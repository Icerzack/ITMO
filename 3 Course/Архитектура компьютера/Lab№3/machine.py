"""Модуль процессора"""

import logging
import sys

from config import ReservedVariable, Register, resolve_overflow, AMOUNT_OF_MEMORY, RegisterFile, Alu, \
    AluOperations, RegisterLatchSignals, instruction_operation
from isa import read_code, InstructionCode, Argument, AddressMode, Instruction


class DataPath:
    """Класс, эмулирующий тракт данных, предоставляющий интерфейс для CU"""

    def __init__(self):
        self.memory: list[Instruction] = []
        self.reg_file: RegisterFile = RegisterFile()
        self.pc_counter: int = 0
        self.addr_bus: int = 0

        self.data_alu: Alu = Alu()
        self.data_alu_bus: int = 0

        self.addr_alu: Alu = Alu()
        self.addr_alu_bus: int = 0

        self.mem_bus: int = 0

        self._zero_flag: bool = False
        self._positive_flag: bool = False

        self.input_buffer: list[int] = []
        self.output_buffer: list[int] = []

    def update_memory(self, memory: list[Instruction], input_buffer: list[int]):
        """Обновление состояния памяти (в т.ч буферов ввода и вывода)"""
        self.memory = memory
        self.input_buffer = input_buffer
        self.output_buffer = []

    def set_registers_arguments(self, sel_out: Register = Register.R1, sel_arg_1: Register = Register.R1, sel_arg_2: Register = Register.R1):
        """Метод для выбора набора регистров из регистрового файла для инструкции (3 параметра)"""
        assert sel_arg_1 in self.reg_file.registers and \
               sel_arg_2 in self.reg_file.registers and \
               sel_out in self.reg_file.registers, 'Такого регистра нет'

        self.reg_file.argument_1 = sel_arg_1
        self.reg_file.argument_2 = sel_arg_2
        self.reg_file.out = sel_out

    def set_data_alu_arguments(self, argument: int | None = None):
        """Метод для эмуляции ввода данных в АЛУ, связанного с регистровым файлом"""
        self.data_alu.left = self.reg_file.registers[self.reg_file.argument_1]
        if argument is not None:
            self.data_alu.right = argument
        else:
            self.data_alu.right = self.reg_file.registers[self.reg_file.argument_2]

    def set_addr_alu_arguments(self, argument: int):
        """Метод для эмуляции ввода данных в АЛУ, связанного счетчиком команд"""
        self.addr_alu.left = self.pc_counter
        self.addr_alu.right = argument

    def execute_data_alu(self, instruction: AluOperations):
        """Эмуляция исполнения команд в АЛУ, связанного с регистровым файлом"""
        res = resolve_overflow(self.data_alu.operations[instruction](self.data_alu.left, self.data_alu.right))

        self.data_alu_bus = res
        self._zero_flag = (res == 0)
        self._positive_flag = (res > 0)

    def execute_addr_alu(self, instruction: AluOperations):
        """Эмуляция исполнения команд в АЛУ, связанного с счетчиком команд"""
        res = resolve_overflow(self.addr_alu.operations[instruction](self.addr_alu.left, self.addr_alu.right))
        self.addr_alu_bus = res

    def latch_pc(self, operand: int | None = None):
        """Установка значений PC"""
        self.pc_counter = self.get_addr_argument(operand)

    def latch_addr_bus(self, operand: int | None = None):
        """Установка значений адресного регистра"""
        self.addr_bus = self.get_addr_argument(operand)

    def latch_register(self, sel_reg: RegisterLatchSignals, argument: int | None = None):
        """Установка значения в выбранный регистр"""
        if sel_reg is RegisterLatchSignals.ARG:
            assert argument is not None, 'Нужно передать аргумент'
            source: int = argument
        elif sel_reg is RegisterLatchSignals.REG:
            source = self.reg_file.registers[self.reg_file.argument_1]
        elif sel_reg is RegisterLatchSignals.ALU:
            source = self.data_alu_bus
        else:
            source = self.mem_bus

        self.reg_file.registers[self.reg_file.out] = source

    def read(self):
        """Чтение из памяти (вкл. ввод)"""
        if self.addr_bus == ReservedVariable.STDIN.value:
            self.mem_bus = self.input_buffer.pop()
        else:
            self.rw_restrictions()
            self.mem_bus = int(self.memory[self.addr_bus].arguments[0].data)

    def write(self):
        """Запись в память (вкл. вывод)"""
        write_data = self.reg_file.registers[self.reg_file.argument_2]

        if self.addr_bus == ReservedVariable.STDOUT.value:
            self.output_buffer.append(write_data)
        else:
            self.rw_restrictions()
            self.memory[self.addr_bus].arguments[0].data = write_data

    def get_zero_flag(self) -> bool:
        """Метод для получения Zero флага"""
        return self._zero_flag

    def get_positive_flag(self) -> bool:
        """Метод получения Positive флага"""
        return self._positive_flag

    def rw_restrictions(self):
        """Ограничения на записываемые / читаемые значения - вспомогательная функция"""
        assert self.memory[self.addr_bus].instruction_code == InstructionCode.DATA, "Попытка доступа к DATA во время write/read операции"
        assert len(self.memory[self.addr_bus].arguments) and self.memory[self.addr_bus].arguments[
            0].address_mode == AddressMode.DATA, "Один аргумент на одну ячейку"

    def get_addr_argument(self, operand: int | None) -> int:
        """Получение корректного аргумента адреса - вспомогательная функция"""
        if operand is not None:
            result = resolve_overflow(operand)
        else:
            result = self.addr_alu_bus

        assert result < AMOUNT_OF_MEMORY, 'Вышел за пределы памяти'
        return result


class ControlUnit:
    """Блок управления процессора. Выполняет декодирование инструкций и
       управляет состоянием процессора, включая обработку данных (DataPath).
       """

    def __init__(self):
        self.data_path: DataPath = DataPath()
        self.current_instruction: Instruction | None = None
        self.program: list[Instruction] = []
        self.step_counter: int = 0
        self._tick: int = 0

    def set_program(self, program: list[Instruction], input_buffer: list[int]):
        """Ввод программы в процессор"""
        cell_counter: int = len(program)
        prog_addr: int = 0

        # Добавляем специальные переменные (если они должны находиться раньше кода)
        max_reserved_address: int = -1

        for var in ReservedVariable:
            if cell_counter > var.value > max_reserved_address:
                max_reserved_address = var.value

        if max_reserved_address != -1:
            program = add_reserved_variables(program, max_reserved_address)
            cell_counter += max_reserved_address + 1
            prog_addr += max_reserved_address + 1

        # "Доаллоцируем" память - 1024 ячейки (включают зарезервированные переменные, находящиеся после кода)
        assert cell_counter < AMOUNT_OF_MEMORY, "Недостаточно памяти!"
        while cell_counter < AMOUNT_OF_MEMORY:
            instruction: Instruction = Instruction(InstructionCode.DATA, cell_counter)
            instruction.add_argument(Argument(AddressMode.DATA, 0))
            program.append(instruction)
            cell_counter += 1

        self.data_path.update_memory(program, input_buffer)
        self.data_path.latch_pc(prog_addr)
        self.latch_step_counter(sel_next=False)

    def tick(self):
        """Счётчик тактов процессора. Вызывается при переходе на следующий такт."""
        self._tick += 1

    def current_tick(self):
        """Получить номер текущего такта"""
        return self._tick

    def latch_step_counter(self, sel_next: bool):
        """Защелкнуть счетчик шагов"""
        if sel_next:
            self.step_counter += 1
        else:
            self.step_counter = 0

    def latch_inc_program_counter(self):
        """Увеличивает счетчик команд на 1 - переходит к следующей инструкции"""
        self.data_path.latch_pc(self.data_path.pc_counter + 1)

    def decode_and_execute_instruction(self, stop_on_tick=0):
        """Прочитать и исполнить инструкцию (в потактовом режиме)"""
        self.tick()
        try:
            if self.current_instruction != self.data_path.memory[self.data_path.pc_counter]:
                self.latch_step_counter(sel_next=False)
                self.current_instruction = self.data_path.memory[self.data_path.pc_counter]
            else:
                self.latch_step_counter(sel_next=True)
                opcode = self.current_instruction.instruction_code
                assert opcode != InstructionCode.DATA, "Попытка исполнения секции DATA"

                if (opcode is InstructionCode.HLT) or (self._tick == stop_on_tick):
                    raise StopIteration

                if opcode in (InstructionCode.JMP, InstructionCode.JE, InstructionCode.JNE, InstructionCode.JG):
                    arg = self.current_instruction.arguments[0]

                    if self.step_counter == 1:
                        self.calc_relative_addr(int(arg.data))
                    else:
                        zero: bool = self.data_path.get_zero_flag()
                        positive: bool = self.data_path.get_positive_flag()

                        if opcode == InstructionCode.JMP \
                                or opcode == InstructionCode.JE and zero \
                                or opcode == InstructionCode.JNE and not zero \
                                or opcode == InstructionCode.JG and positive:
                            self.data_path.latch_pc()
                        else:
                            self.latch_inc_program_counter()

                if opcode in (InstructionCode.INC, InstructionCode.DEC):
                    if self.step_counter == 1:
                        reg: Register = Register(self.current_instruction.arguments[0].data)
                        self.data_path.set_registers_arguments(sel_out=reg, sel_arg_1=reg)

                        if opcode == InstructionCode.INC:
                            self.data_path.set_data_alu_arguments(1)
                        else:
                            self.data_path.set_data_alu_arguments(-1)

                        self.data_path.execute_data_alu(instruction_operation[opcode])
                    else:
                        self.data_path.latch_register(RegisterLatchSignals.ALU)
                        self.latch_inc_program_counter()

                if opcode in (InstructionCode.ADD, InstructionCode.SUB, InstructionCode.DIV, InstructionCode.MOD, InstructionCode.MUL):
                    if self.step_counter == 1:
                        first_arg, second_arg, third_arg = self.current_instruction.arguments

                        if third_arg.address_mode == AddressMode.REG:
                            self.data_path.set_registers_arguments(sel_out=Register(first_arg.data), sel_arg_1=Register(second_arg.data), sel_arg_2=Register(third_arg.data))
                            self.data_path.set_data_alu_arguments()
                        else:
                            self.data_path.set_registers_arguments(sel_out=Register(first_arg.data), sel_arg_1=Register(second_arg.data))
                            self.data_path.set_data_alu_arguments(int(third_arg.data))

                        self.data_path.execute_data_alu(instruction_operation[opcode])
                    else:
                        self.data_path.latch_register(RegisterLatchSignals.ALU)
                        self.latch_inc_program_counter()

                if opcode is InstructionCode.CMP:
                    first_arg, second_arg = self.current_instruction.arguments
                    if second_arg.address_mode == AddressMode.REG:
                        self.data_path.set_registers_arguments(sel_arg_1=Register(first_arg.data), sel_arg_2=Register(second_arg.data))
                        self.data_path.set_data_alu_arguments()
                    else:
                        self.data_path.set_registers_arguments(sel_arg_1=Register(first_arg.data))
                        self.data_path.set_data_alu_arguments(int(second_arg.data))

                    self.data_path.execute_data_alu(instruction_operation[opcode])
                    self.latch_inc_program_counter()

                if opcode is InstructionCode.MOV:
                    first_arg, second_arg = self.current_instruction.arguments
                    if second_arg.address_mode == AddressMode.REG:
                        if self.step_counter == 1:
                            self.data_path.set_registers_arguments(sel_out=Register(first_arg.data), sel_arg_1=Register(second_arg.data))
                        else:
                            self.data_path.latch_register(RegisterLatchSignals.REG)
                            self.latch_inc_program_counter()
                    else:
                        if self.step_counter == 1:
                            self.data_path.set_registers_arguments(sel_out=Register(first_arg.data))
                        else:
                            self.data_path.latch_register(RegisterLatchSignals.ARG, int(second_arg.data))
                            self.latch_inc_program_counter()

                if opcode is InstructionCode.LD:
                    first_arg, second_arg = self.current_instruction.arguments

                    if second_arg.address_mode == AddressMode.ABS:
                        self.load_common_part(Register(first_arg.data), int(second_arg.data), 0)
                        if self.step_counter >= 2:
                            self.latch_inc_program_counter()
                    else:
                        if self.step_counter == 1:
                            self.calc_relative_addr(int(second_arg.data))
                        else:
                            self.load_common_part(Register(first_arg.data), None, 1)
                            if self.step_counter >= 3:
                                self.latch_inc_program_counter()

                if opcode is InstructionCode.ST:
                    first_arg, second_arg = self.current_instruction.arguments
                    if first_arg.address_mode == AddressMode.ABS:
                        self.save_common_part(int(first_arg.data), Register(second_arg.data))
                        self.latch_inc_program_counter()
                    else:
                        if self.step_counter == 1:
                            self.calc_relative_addr(int(first_arg.data))
                            self.latch_step_counter(sel_next=True)
                        else:
                            self.save_common_part(None, Register(second_arg.data))
                            self.latch_inc_program_counter()
        except ValueError as error:
            raise ValueError(f'You use incorrect argument in command {self.current_instruction}') from error

    def __repr__(self):
        state = f"TICK: {self._tick}, PC: {self.data_path.pc_counter}, ADDR_BUS: {self.data_path.addr_bus}, " \
                f"R1: {self.data_path.reg_file.registers[Register.R1]}, R2: {self.data_path.reg_file.registers[Register.R2]}, " \
                f"R3: {self.data_path.reg_file.registers[Register.R3]}, R4: {self.data_path.reg_file.registers[Register.R4]}, " \
                f"R5: {self.data_path.reg_file.registers[Register.R5]}, D_ALU_BUD: {self.data_path.data_alu_bus}, " \
                f"A_ALU_BUD: {self.data_path.addr_alu_bus}, MEM_BUS: {self.data_path.mem_bus}," \
                f" Z: {self.data_path.get_zero_flag()}, P: {self.data_path.get_positive_flag()}, STEP_COUNTER: {self.step_counter}"

        if self.current_instruction is not None:
            instruction = self.current_instruction
            opcode: InstructionCode = instruction.instruction_code
            cell_num: int = instruction.position
            arguments: list[tuple[AddressMode, int | Register | str]] = []

            for arg in instruction.arguments:
                arguments.append((arg.address_mode, arg.data))

            action = f"\nCELL_NUMBER: {cell_num}, OPCODE: {opcode.upper()}, ARGS: {arguments}"
        else:
            action = "-"

        return f"{state} {action}"

    def calc_relative_addr(self, offset: int):
        """Вычисление относительного адреса - вспомогательная функция"""
        self.data_path.set_addr_alu_arguments(offset)
        self.data_path.execute_addr_alu(AluOperations.ADD)

    def save_common_part(self, goal: int | None, arg: Register):
        """Общая часть операции сохранения - вспомогательная функция"""
        self.data_path.latch_addr_bus(goal)
        self.data_path.set_registers_arguments(sel_arg_2=arg)
        self.data_path.write()

    def load_common_part(self, goal: Register, arg: int | None, offset: int = 0):
        """Общая часть операции загрузки - вспомогательная функция"""
        if self.step_counter == 1 + offset:
            self.data_path.latch_addr_bus(arg)
            self.data_path.read()
        elif self.step_counter == 2 + offset:
            self.data_path.set_registers_arguments(sel_out=goal)
            self.data_path.latch_register(RegisterLatchSignals.MEM)


def simulation(code: list[Instruction], limit: int, input_buffer: list[int]) -> tuple[str, int, int]:
    """Симуляция программы"""
    control_unit: ControlUnit = ControlUnit()
    control_unit.set_program(code, input_buffer)
    instr_counter = 0

    logging.debug('%s', control_unit)
    try:
        while True:
            assert limit > instr_counter, "Слишком долгое исполнение!"
            control_unit.decode_and_execute_instruction()
            if control_unit.step_counter == 0:
                instr_counter += 1
            logging.debug('%s', control_unit)
    except StopIteration:
        pass
    except IndexError:
        pass

    output = ''
    for char in control_unit.data_path.output_buffer:
        output += str(char)
        if char in range(0x110000):
            output += '(' + chr(char) + ')' + ' '
    return output, instr_counter, control_unit.current_tick()


def add_reserved_variables(instructions: list[Instruction], max_reserved_address: int) -> list[Instruction]:
    """Добавление зарезервированных ячеек до кода"""
    for instruction in instructions:
        instruction.position += max_reserved_address + 1

    for pos in range(max_reserved_address + 1):
        instruction = Instruction(InstructionCode.DATA, pos)
        instruction.add_argument(Argument(AddressMode.DATA, 0))

        instructions.insert(pos, instruction)

    return instructions


def main(args):
    """Главная функция модуля"""
    assert len(args) == 2, "Ожидаемый ввод: machine.py <code_file> <input_file>"
    code_file, input_file = args

    input_token = []
    memory: list[Instruction] = read_code(code_file)
    with open(input_file, encoding="utf-8") as file:
        input_text = file.read()
        for char in input_text:
            input_token.append(ord(char))

    input_token.reverse()

    output, instr_counter, ticks = simulation(memory, 100000, input_token)

    print("Output:")
    print(output)
    print("instr_counter:", instr_counter, "ticks:", ticks, end='')
    return output


if __name__ == '__main__':
    logging.getLogger().setLevel(logging.DEBUG)
    main(sys.argv[1:])
