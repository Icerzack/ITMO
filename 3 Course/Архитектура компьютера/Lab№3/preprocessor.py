"""Модуль препроцессорной обработки"""

import re


def remove_extra_spaces(line: str) -> str:
    """Удаление лишних пробелов"""
    line = line.replace("' '", str(ord(' ')))
    return re.sub(r'\s+', ' ', line)


def remove_commas(line: str) -> str:
    """Удаление запятых"""
    line = line.replace("','", str(ord(',')))
    return line.replace(',', ' ')


def preprocessing(asm_text: str) -> str:
    """Предобработка кода"""
    lines: list[str] = asm_text.splitlines()

    strip_lines = map(str.strip, lines)
    remove_empty_lines = filter(bool, strip_lines)
    removed_commas = map(remove_commas, remove_empty_lines)
    remove_spaces = map(remove_extra_spaces, removed_commas)

    minified_text: str = '\n'.join(remove_spaces)

    return minified_text
