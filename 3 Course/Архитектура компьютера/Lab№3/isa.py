"""Модуль описания системы команд (содержит функции кодирования и декодирования команд"""
import json

from enum import Enum


class Register(str, Enum):
    """Список поддерживаемых регистров"""
    R1 = 'r1'
    R2 = 'r2'
    R3 = 'r3'
    R4 = 'r4'
    R5 = 'r5'


class InstructionCode(str, Enum):
    """Коды операций"""

    DATA = 'data'
    HLT = 'hlt'

    LD = 'ld'
    ST = 'st'

    ADD = 'add'
    SUB = 'sub'
    MUL = 'mul'
    MOD = 'mod'
    DIV = 'div'
    CMP = 'cmp'
    MOV = 'mov'

    INC = 'inc'
    DEC = 'dec'

    JMP = 'jmp'
    JE = 'je'
    JNE = 'jne'
    JG = 'jg'


class OperandNumber(int, Enum):
    """Количество операндов, которые должны быть у команды"""
    THREE = 3
    TWO = 2
    ONE = 1
    NONE = 0


class InstructionType(str, Enum):
    """Вид операции"""
    MEM = 'mem'
    BRANCH = 'branch'
    REGISTER = 'register'


class InstructionRestriction:
    """Все ограничения конкретной операции"""

    def __init__(self, amount: OperandNumber):
        self.amount = amount
        self.types: list[InstructionType] = []

    def add_operation_type(self, *operation_types: InstructionType):
        """Добавляет тип операции к ограничениям"""
        for current_type in operation_types:
            self.types.append(current_type)


# Конфигурация ограничений операций
nop_restriction = InstructionRestriction(OperandNumber.NONE)

branch_restriction = InstructionRestriction(OperandNumber.ONE)
branch_restriction.add_operation_type(InstructionType.BRANCH)

incdec_restriction = InstructionRestriction(OperandNumber.ONE)
incdec_restriction.add_operation_type(InstructionType.REGISTER)

data_restriction = InstructionRestriction(OperandNumber.TWO)
data_restriction.add_operation_type(InstructionType.REGISTER, InstructionType.MEM)

three_restriction = InstructionRestriction(OperandNumber.THREE)
three_restriction.add_operation_type(InstructionType.REGISTER)

two_restriction = InstructionRestriction(OperandNumber.TWO)
two_restriction.add_operation_type(InstructionType.REGISTER)

instruction_restriction_info: dict[InstructionCode, InstructionRestriction] = {
    InstructionCode.DATA: nop_restriction,
    InstructionCode.HLT: nop_restriction,

    InstructionCode.JMP: branch_restriction,
    InstructionCode.JE: branch_restriction,
    InstructionCode.JNE: branch_restriction,
    InstructionCode.JG: branch_restriction,

    InstructionCode.INC: incdec_restriction,
    InstructionCode.DEC: incdec_restriction,

    InstructionCode.LD: data_restriction,
    InstructionCode.ST: data_restriction,

    InstructionCode.ADD: three_restriction,
    InstructionCode.SUB: three_restriction,
    InstructionCode.MOV: two_restriction,
    InstructionCode.MOD: three_restriction,
    InstructionCode.DIV: three_restriction,
    InstructionCode.MUL: three_restriction,
    InstructionCode.CMP: two_restriction,
}

# Сбор ограничений для каждой операции

# Исключения в общих ограничениях
instruction_restriction_exceptions: dict[InstructionCode, list[InstructionType]] = {
    InstructionCode.ST: [InstructionType.REGISTER]
}


class AddressMode(str, Enum):
    """Режимы адресации"""
    ABS = 'absolute'
    REL = 'relative'
    DATA = 'data'
    REG = 'register'


class Argument:
    """Аргуемент инструкции"""

    def __init__(self, address_mode: AddressMode, data: int | Register | str):
        self.data = data
        self.address_mode = address_mode


class Instruction:
    """Полное описание инструкции."""

    def __init__(self, instruction_code: InstructionCode, position: int):
        self.instruction_code = instruction_code
        self.position = position
        self.arguments: list[Argument] = []

    def add_argument(self, arg: Argument):
        """Функция добавления аргументов к команде (инструкции)"""
        self.arguments.append(arg)

    def supports_type_of(self, instruction_type: InstructionType) -> bool:
        """Проверяет поддерживает ли операция тот или иной тип"""
        return instruction_type in instruction_restriction_info[self.instruction_code].types


class Encoder(json.JSONEncoder):
    """Класс-энкодер для записи и чтения из JSON"""

    def default(self, o):
        if isinstance(o, (Argument, Instruction)):
            return o.__dict__
        return json.JSONEncoder.default(self, o)


def write_code(filename: str, code: list[Instruction]) -> None:
    """Функция кодирования и записи команд"""
    with open(filename, "w", encoding="utf-8") as file:
        file.write(json.dumps(code, indent=4, cls=Encoder))


def read_code(filename: str) -> list[Instruction]:
    """Функция декодирования и чтения команд"""

    with open(filename, encoding="utf-8") as file:
        code = json.loads(file.read())  # type: ignore

    result = []

    for instr in code:
        instruction = Instruction(InstructionCode(instr['instruction_code']), instr['position'])
        for arg in instr['arguments']:
            addr_mode = arg['address_mode']
            if addr_mode == AddressMode.REG:
                arg['data'] = Register(arg['data'])
            instruction.add_argument(Argument(addr_mode, arg['data']))
        result.append(instruction)

    return result
