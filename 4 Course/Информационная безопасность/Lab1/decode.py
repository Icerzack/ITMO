from tools import *


def decode(keyword, origin_phrase, print_steps=False, print_table=False) -> str:
    """
    @:param keyword -- ключевое слово, по которому будет строиться матрица и дешифроваться слово

    @:param origin_phrase -- оригинальное слово, которое будет дешифровано

    @:param print_steps -- False по-умолчанию, флаг, который отвечает за показ пошагового конвертирования биграмм

    @:param print_table -- False по-умолчанию, флаг, который отвечает за отображение матрицы по ключевому слову
    """
    temp = len(origin_phrase)

    origin_phrase = ''.join(re.findall('[А-ЯЁ ,.]', origin_phrase))
    if temp != len(origin_phrase) or temp % 2 == 1:
        raise Exception(f'ОШИБКА: нечетное количество символом, либо недопустимые элементы')

    print(f'Итоговая фраза: "{origin_phrase}"')

    dec_phrase = ""
    matrix = build_table(keyword, print_table)
    for i in range(0, len(origin_phrase), 2):
        first_y, first_x = find_position(matrix, origin_phrase[i])
        second_y, second_x = find_position(matrix, origin_phrase[i + 1])

        if first_x == second_x:
            dec_phrase = dec_phrase + matrix[5 if first_y == 0 else first_y - 1][first_x]
            dec_phrase = dec_phrase + matrix[5 if second_y == 0 else second_y - 1][second_x]

        elif first_y == second_y:
            dec_phrase = dec_phrase + matrix[first_y][5 if first_x == 0 else first_x - 1]
            dec_phrase = dec_phrase + matrix[second_y][5 if second_x == 0 else second_x - 1]

        else:
            dec_phrase = dec_phrase + matrix[first_y][second_x]
            dec_phrase = dec_phrase + matrix[second_y][first_x]
        if print_steps:
            print(f'{origin_phrase[i]}{origin_phrase[i + 1]}-->{dec_phrase[i]}{dec_phrase[i + 1]}')

    print(f'Дешифрованная итоговая фраза: {dec_phrase}')
    return dec_phrase
