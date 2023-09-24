from encode import *
from decode import *

while 1:
    print("Введите ключевое слово (допуст. символы: [А-Я ,.]):")
    keyword = input().upper()

    print("Введите 1 -- для кодирования, 2 -- для декодирования:")
    mode = input()
    if mode == '1':
        print("Фраза для шифрования:")
        phrase = input().upper()
        encode(keyword, phrase, print_steps=True)
    elif mode == '2':
        print("Фраза для дешифрования:")
        phrase = input().upper()
        decode(keyword, phrase)
    else:
        print("Неверный ввод. Отмена операции.")
