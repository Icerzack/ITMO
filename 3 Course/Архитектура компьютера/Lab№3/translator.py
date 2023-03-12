"""Модуль транслятора"""
import re
import sys

from config import Register, ReservedVariable
from isa import InstructionCode, Instruction, Argument, AddressMode, \
    write_code, instruction_restriction_info, InstructionType, instruction_restriction_exceptions
from preprocessor import preprocessing


def decode_register(operation: Instruction, arg: str) -> Instruction:
    """Постановка регистра в качестве аргумента
    Проверяет, что данная инструкция регистровая и возвращает инструкцию
    с добавленным типом адресации REG"""
    assert Register(arg.lower()) is not None, 'Такой регистр не найден.'
    assert operation.supports_type_of(InstructionType.REGISTER), 'Вы не можете использовать регистры в нерегистровых командах.'
    operation.add_argument(Argument(AddressMode.REG, Register(arg.lower())))
    return operation


def decode_label(operation: Instruction, arg: str, labels: dict[str, int]) -> tuple[Instruction, str | int]:
    """Постановка лейбла в качестве аргумента
    Проверяет, что лейбл используется в команде перехода"""
    assert operation.supports_type_of(InstructionType.BRANCH), 'Вы не можете использовать лейблы в командах, которые не являются переходами.'

    if arg in labels:
        addr: int | str = labels[arg] - operation.position
    else:
        addr = arg
    operation.add_argument(Argument(AddressMode.REL, addr))

    return operation, addr


def decode_absolute(operation: Instruction, arg: str) -> Instruction:
    """Постановка ячейки памяти с абсолютной адресацией в качестве аргумента
    Проверяет, что инструкция является MEM типом"""
    assert operation.supports_type_of(InstructionType.MEM), 'Доступ к памяти возможне только в командах LD и ST.'
    address: int

    if ReservedVariable[arg] is not None:
        address = ReservedVariable[arg].value
    else:
        try:
            address = int(arg)
        except ValueError as address_error:
            raise TypeError("int используется с абсолютной адресацией.") from address_error

    operation.add_argument(Argument(AddressMode.ABS, address))
    return operation


def decode_relative(operation: Instruction, arg: str) -> Instruction:
    """Постановка ячейки памяти с относительной адресацией в качестве аргумента
    Проверяет, что инструкция явяляется MEM типом"""
    assert operation.supports_type_of(InstructionType.MEM), 'Доступ к памяти возможен только в командах LD и ST.'
    assert arg.find(')') == len(arg) - 1, 'Переменные передаются в скобках ( и ).'
    operation.add_argument(Argument(AddressMode.REL, arg[:-1]))

    return operation


def decode_char(operation: Instruction, arg: str) -> Instruction:
    """Постановка символа в качестве аргумента операции
    Проверяет, что инструкция является регистровой REG"""
    assert arg.split("'") != 2, "Буквы пишутся в одинарных кавычках."
    assert operation.supports_type_of(
        InstructionType.REGISTER) or operation.instruction_code == InstructionCode.DATA, 'Для использования букв это должна быть регистровая операция.'

    try:
        operation.add_argument(Argument(AddressMode.DATA, ord(arg[:-1])))
        return operation
    except TypeError as char_error:
        raise TypeError("Поддержки строк нет, только буквы.") from char_error


def decode_int(operation: Instruction, arg: str) -> Instruction:
    """Постановка числа в качестве аргумента операции
    Проверяет, что инструкция является регистровой REG"""
    assert operation.supports_type_of(
        InstructionType.REGISTER) or operation.instruction_code == InstructionCode.DATA, 'Числа используются только в регистровых операциях.'

    try:
        value = int(arg)
        operation.add_argument(Argument(AddressMode.DATA, value))
        return operation
    except ValueError as char_error:
        raise TypeError("Элементы пишутся в одинарных кавычках.") from char_error


def check_operation_type(instruction: Instruction):
    """Проверяет, корректное ли количество операндов в операции использовано"""
    if instruction.instruction_code in instruction_restriction_info:
        restriction_types: list[InstructionType] = instruction_restriction_info[instruction.instruction_code].types.copy()
        restriction_amount: int = instruction_restriction_info[instruction.instruction_code].amount.value
        argument_types: list[AddressMode] = []

        for arg in instruction.arguments:
            argument_types.append(arg.address_mode)

        assert restriction_amount == len(
            instruction.arguments), 'Неправильное количество аргументов использовано у операции.'

        # Применение исключений на операцию
        if instruction.instruction_code in instruction_restriction_exceptions:
            for exception in instruction_restriction_exceptions[instruction.instruction_code]:
                if exception in restriction_types:
                    restriction_types.remove(exception)

        if InstructionType.REGISTER in restriction_types:
            for arg_num in range(0, len(instruction.arguments) - 1, 1):
                assert instruction.arguments[arg_num].address_mode == AddressMode.REG, 'Регистр должен быть на первых n-1 местах (или на 1ом, если один операнд).'

        if InstructionType.MEM in restriction_types:
            assert AddressMode.REG in argument_types, 'Должен использоваться хотя бы один регистр в MEM операциях.'

        if InstructionType.BRANCH in restriction_types:
            assert len(
                argument_types) == 1 and AddressMode.REL in argument_types, 'В операциях перехода должен использовать один лейбл и один операнд.'


def check_mem_operand_constraints(operation: Instruction, arg: str, arg_num: int):
    """Проверяет ограничения, наложенные на отдельные команды LD и ST"""
    if operation.instruction_code == InstructionCode.LD:
        if arg_num == 0:
            assert Register(arg[1:].lower()) is not None, \
                "В LD регистр стоит на 1ом месте"
        else:
            assert arg[0] in ('#', '('), "В LD адрес или переменная стоят на 2ом месте"

    if operation.instruction_code == InstructionCode.ST:
        if arg_num == 1:
            assert Register(arg[1:].lower()) is not None, \
                "В ST регист стоит на 2ом месте"
        else:
            assert arg[0] in ('#', '('), "В ST адрес или переменная стоят на 1ом месте"


def set_undefined_labels(instructions: list[Instruction], labels: dict[str, int], undefined_labels: dict[str, list[tuple[int, int]]]) -> list[Instruction]:
    """Постановка адресов необнаруженных при проходе лейблов"""
    for label_name, params in undefined_labels.items():
        assert label_name in labels, 'Вы используете неопределенную метку.'

        for instruction_position, argument_number in params:
            instruction = instructions[instruction_position]
            instruction.arguments[argument_number].data = labels[label_name] - instruction.position

    return instructions


def set_start_address(instructions: list[Instruction], start_address: int):
    """Переход на .start в начале программы"""
    for instruction in instructions:
        instruction.position += 1

    jmp_start_address_instruction = Instruction(InstructionCode.JMP, 0)
    jmp_start_address_instruction.add_argument(Argument(AddressMode.REL, start_address + 1))

    instructions.insert(0, jmp_start_address_instruction)
    return instructions


def set_undefined_variables(instruction: Instruction, variables: dict[str, int]) -> Instruction:
    """Постановка значений переменных в инструкции"""
    if instruction.supports_type_of(InstructionType.MEM):
        for arg in instruction.arguments:
            if arg.address_mode is AddressMode.REL and isinstance(arg.data, str):
                if arg.data in variables:
                    arg.data = variables[arg.data] - instruction.position
                assert not isinstance(arg.data, str), 'Вы используете несуществующие переменные.'

    return instruction


def unite_text_and_data_sections(text_section: list[Instruction], data_section: list[Instruction], is_text_first: bool, variables: dict[str, int]) -> \
        tuple[list[Instruction], int]:
    """Соединение секций text и data"""
    offset: int = 0

    if is_text_first:
        for command in data_section:
            command.position += len(text_section)

        for name in variables.keys():
            variables[name] += len(text_section)

        result = text_section + data_section
    else:
        for command in text_section:
            command.position += len(data_section)
        result = data_section + text_section
        offset = len(data_section)

    for command in result:
        set_undefined_variables(command, variables)

    return result, offset


def transform_data_into_structure(data: str) -> list:
    """Траснфомирует секцию data в структуру"""
    result = []
    variables: dict[str, int] = {}
    address_counter = 0

    for line in data.split('\n'):
        assert len(line.split(' ')) == 2 or line.find("' '") != -1, 'Для одной переменной -- одно значение.'
        current_operation = Instruction(InstructionCode.DATA, address_counter)

        var_description = line.split(':')
        assert len(var_description) == 2, 'Знак : пишется после названия переменной.'

        name, value = var_description[0], re.sub(r'\s+', '', var_description[1])

        assert name[0][-1] != ' ', 'Знак : пишется после названия переменной.'
        assert name not in variables, 'Переинициализация переменной.'

        if value[0] == "'":
            print(value)
            current_operation = decode_char(current_operation, value[1:])
        else:
            current_operation = decode_int(current_operation, value)

        variables[name] = address_counter

        result.append(current_operation)

        address_counter += 1

    return [result, variables]


def transform_text_into_structure(text: str) -> tuple[list[Instruction], int]:
    """Трансофмирует секцию text в структуру"""
    assert (text.find('.start:') != -1), 'Обязательно должна быть метка .start'

    labels: dict[str, int] = {}
    undefined_labels: dict[str, list[tuple[int, int]]] = {}
    start_address: int = -1

    address_counter: int = 0

    result: list[Instruction] = []

    for instr in text.split('\n'):

        decoding = instr.split(' ')

        if decoding[0][0] == '.':  # Новый лейбл
            current_label = decoding[0]

            assert len(decoding) == 1, 'Метки пишутся на отдельных строчках.'
            assert current_label[-1] == ':' and current_label.find(':'), 'Метки должны содержать знак :'

            current_label = decoding[0][1:-1]
            if current_label == 'start':
                start_address = address_counter
            labels[current_label] = address_counter
        else:  # Декодирование операнда
            assert InstructionCode(decoding[0].lower()) is not None, 'Такой команды не существует.'

            current_instruction = Instruction(InstructionCode(decoding[0].lower()), address_counter)
            arg_counter = 0
            for arg in decoding[1:]:
                # Проверка ограничений конкретных команд
                check_mem_operand_constraints(current_instruction, arg, arg_counter)

                if arg[0] == '%':
                    current_instruction = decode_register(current_instruction, arg[1:])
                elif arg[0] == '.':
                    current_instruction, label = decode_label(current_instruction, arg[1:], labels)
                    if isinstance(label, str):
                        if label not in undefined_labels:
                            undefined_labels[label] = []
                        undefined_labels[label].append((address_counter, arg_counter))
                elif arg[0] == '#':
                    current_instruction = decode_absolute(current_instruction, arg[1:])
                elif arg[0] == '(':
                    current_instruction = decode_relative(current_instruction, arg[1:])
                elif arg[0] == "'":
                    current_instruction = decode_char(current_instruction, arg[1:])
                else:
                    current_instruction = decode_int(current_instruction, arg)

                arg_counter += 1

            # Проверка корректности команды и ее сохранение
            check_operation_type(current_instruction)
            result.append(current_instruction)
            address_counter += 1

    result = set_undefined_labels(result, labels, undefined_labels)
    return result, start_address


def perform_translator(source: str) -> list[Instruction]:
    """Функция запуска транслятора."""

    code = preprocessing(source)
    data = []
    variables = {}

    text_index = code.find('section .text')

    if text_index == -1:
        raise Exception('Обязательно наличие section .text')

    text_start, text_stop = text_index + len('section .text') + 1, None

    data_index = code.find('section .data')

    if data_index == -1:
        text_stop = len(code)
    else:
        data_start, data_stop = data_index + len('section .data') + 1, None

        if data_index < text_index:
            data_stop = text_index - 1
            text_stop = len(code)
        else:
            text_stop = data_index - 1
            data_stop = len(code)
        data, variables = transform_data_into_structure(code[data_start:data_stop])

    text, start_address = transform_text_into_structure(code[text_start:text_stop])

    all_programm, data_offset = unite_text_and_data_sections(text, data, data_index > text_index, variables)
    result = set_start_address(all_programm, start_address + data_offset)

    return result


def main(args):
    """Главная функция модуля"""
    assert len(args) == 2, \
        "Запуск вида: translation.py <asm_file> <target>"
    source = args[0]

    with open(source, "rt", encoding="utf-8") as file:
        code = file.read()

    result = perform_translator(code)

    write_code(args[1], result)
    loc = len(code.split("\n"))

    print(f"source LoC: {loc} instr: {len(result)}")


if __name__ == '__main__':
    sys.path.append('')
    main(sys.argv[1:])
