from tools import *



def encode(keyword, origin_phrase, should_extend=False, print_steps=False, print_table=False) -> str:
    """
    @:param keyword -- ключевое слово, по которому будет строиться матрица и шифроваться слово

    @:param origin_phrase -- оригинальное слово, которое будет зашифровано

    @:param should_extend -- False по-умолчанию, флаг, который отвечает за разделение биграммы из двух одинаковых букв

    @:param print_steps -- False по-умолчанию, флаг, который отвечает за показ пошагового конвертирования биграмм

    @:param print_table -- False по-умолчанию, флаг, который отвечает за отображение матрицы по ключевому слову
    """
    origin_phrase = ''.join(re.findall('[А-ЯЁ .,]', origin_phrase))
    if should_extend:
        if len(origin_phrase) > 1:
            for i in range(0, len(origin_phrase), 2):
                if origin_phrase[i] == origin_phrase[i + 1]:
                    origin_phrase = origin_phrase[:i + 1] + 'Я' + origin_phrase[i + 1:]

    if len(origin_phrase) % 2 == 1:
        origin_phrase = origin_phrase + " "

    print(f'Итоговая фраза: "{origin_phrase}"')

    enc_phrase = ""
    matrix = build_table(keyword, print_table)
    for i in range(0, len(origin_phrase), 2):
        first_y, first_x = find_position(matrix, origin_phrase[i])
        second_y, second_x = find_position(matrix, origin_phrase[i + 1])

        if first_x == second_x:
            enc_phrase = enc_phrase + matrix[0 if first_y == 5 else first_y + 1][first_x]
            enc_phrase = enc_phrase + matrix[0 if second_y == 5 else second_y + 1][second_x]

        elif first_y == second_y:
            enc_phrase = enc_phrase + matrix[first_y][0 if first_x == 5 else first_x + 1]
            enc_phrase = enc_phrase + matrix[second_y][0 if second_x == 5 else second_x + 1]

        else:
            enc_phrase = enc_phrase + matrix[first_y][second_x]
            enc_phrase = enc_phrase + matrix[second_y][first_x]
        if print_steps:
            print(f'{origin_phrase[i]}{origin_phrase[i + 1]}-->{enc_phrase[i]}{enc_phrase[i + 1]}')

    print(f'Зашифрованная итоговая фраза: {enc_phrase}')
    return enc_phrase
