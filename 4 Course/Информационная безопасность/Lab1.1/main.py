from encode import *
from decode import *

while 1:
    print("Введите режим шифрования: 1 -- файл, 2 -- консоль.")
    type = input()
    phrase = ""
    if type == '1':
        with open('input.txt', 'r') as f:
            phrase = f.read().upper()
    elif type == '2':
        print("Фраза:")
        phrase = input().upper()
    else:
        print("Неверный ввод. Отмена операции.")
        continue

    print("Введите ключевое слово (допуст. символы: [А-Я ,.]):")
    keyword = input().upper()

    print("Введите 1 -- для кодирования, 2 -- для декодирования:")
    mode = input()
    if mode == '1':
        output = encode(keyword, phrase, print_steps=True, print_table=True)
    elif mode == '2':
        output = decode(keyword, phrase, print_steps=True, print_table=True)
    else:
        print("Неверный ввод. Отмена операции.")
        continue
    if type == '1':
        with open('output.txt', 'w') as f:
            f.write(output)