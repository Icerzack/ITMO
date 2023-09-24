import re


def find_position(square, letter) -> (int, int):
    for y in range(0, 6):
        for x in range(0, 6):
            if square[y][x] == letter:
                return y, x


def build_table(keyword, print_table=False) -> [[], [], [], [], [], []]:
    keyword = ''.join(re.findall('[А-ЯЁ ,.]', keyword))
    print(f'Итоговое ключевое слово: "{keyword}"')

    encryption_square = [[], [], [], [], [], []]

    keyword = ''.join(sorted(set(keyword), key=keyword.index))

    total = keyword + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ .,"

    encryption_word = ''.join(sorted(set(total), key=total.index))
    if print_table:
        print("\nМатрица 6х6:")
    for i in range(0, 6):
        for j in range(0, 6):
            encryption_square[i].append(encryption_word[j + i * 6])
        if print_table:
            print(encryption_square[i])

    return encryption_square
