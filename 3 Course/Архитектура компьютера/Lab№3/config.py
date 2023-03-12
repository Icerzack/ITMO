"""Конфигурация работы CU и DataPath"""

from enum import Enum

from isa import InstructionCode, Register


class ReservedVariable(int, Enum):
    """Адреса зарезервированных переменных"""
    STDIN = 0
    STDOUT = 1


# Длина машинного слова
WORD_LENGTH: int = 64

# Количество ячеек памяти
AMOUNT_OF_MEMORY = 1024


class AluOperations(Enum):
    """Список операций АЛУ"""
    ADD = 0
    SUB = 1
    MUL = 2
    DIV = 3
    MOD = 4


# Соотношение инструкций команд и операций АЛУ, связанного с регистровым файлом
instruction_operation = {
    InstructionCode.ADD: AluOperations.ADD,
    InstructionCode.SUB: AluOperations.SUB,
    InstructionCode.MUL: AluOperations.MUL,
    InstructionCode.DIV: AluOperations.DIV,
    InstructionCode.MOD: AluOperations.MOD,
    InstructionCode.CMP: AluOperations.SUB,
    InstructionCode.INC: AluOperations.ADD,
    InstructionCode.DEC: AluOperations.ADD
}


class RegisterLatchSignals(Enum):
    """Список управляющих сигналов для latch_reg"""
    ALU = 0
    ARG = 1
    MEM = 2
    REG = 3


class Alu:
    """Арифметико-логическое устройство с двумя входами данных и сигналом операции."""

    def __init__(self):
        self.left: int = 0
        self.right: int = 0
        self.operations: dict = {
            AluOperations.DIV: lambda left, right: left / right,
            AluOperations.MOD: lambda left, right: left % right,
            AluOperations.ADD: lambda left, right: left + right,
            AluOperations.SUB: lambda left, right: left - right,
            AluOperations.MUL: lambda left, right: left * right,
        }


class RegisterFile:
    """Класс эмулирующий регистровый файл"""

    def __init__(self):
        self.registers = {
            Register.R1: 0,
            Register.R2: 0,
            Register.R3: 0,
            Register.R4: 0,
            Register.R5: 0
        }
        self.argument_1: Register = Register.R1
        self.argument_2: Register = Register.R1
        self.out: Register = Register.R1


def resolve_overflow(arg: int) -> int:
    """Отвечает за соблюдение машинного слова"""
    while arg > 2 ** WORD_LENGTH:
        arg = -2 ** WORD_LENGTH + (arg - 2 ** WORD_LENGTH)
    while arg < -2 ** WORD_LENGTH:
        arg = 2 ** WORD_LENGTH - (arg + 2 ** WORD_LENGTH)

    return arg
