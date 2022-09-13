%include "words.inc"						;Подключаем words.inc

extern read_word						;Импортируем функцию read_word из lib.asm
extern print_newline						;Импортируем функцию print_newline из lib.asm
extern print_string						;Импортируем функцию print_string из lib.asm
extern string_length						;Импортируем функцию string_length из lib.asm
extern find_word						;Импортируем функцию find_word из dict.asm
extern print_error						;Импортируем функцию print_error из lib.asm
extern exit							;Импортируем функцию exit из lib.asm


%define BUFFER_SIZE 256


global _start							;Указываем глоблальную метку 


section .rodata						;Секция с константными данными

not_found: db "Ничего не найдено по такому ключу =(", 0	;Сообщение об ошибке, что не найдено соответствие
not_enough: db "Введенная строка больше чем 255 символов", 0	;Сообщение об ошибке, что введенная строка слишком большая



section .text							;Начало секции с кодом


_start:							;Метка начала программы
    sub rsp, BUFFER_SIZE					; Выделяем на стеке буффер под строку-ключ
    mov rdi, rsp						; Читаем строку-ключ из stdin
    mov rsi, BUFFER_SIZE
    call read_word						;Считываем введенное слово
    test rax, rax						;Слово оказалось больше буфера, rax == 0?
    je .not_enough						;Если так, то завершаем программу ошибкой "Мало места"
    mov rdi, rsp						;Кладем в rdi сам введенный ключ поиска
    mov rsi, next						;кладем в rsi адрес начала нашего словаря (то есть последнего добавленого элемента)
    call find_word						;Вызываем find_word чтобы найти такой ключ в словаре
    add rsp, BUFFER_SIZE
    test rax, rax						;Ничего не нашлось, rax == 0?
    je .not_found						;Если так, то завершаем программу ошибкой "Ключ не найден"
    add rax, 8							;Иначе же переходим на значение КЛЮЧА(а нам вернулся адрес НАЧАЛА ВХОЖДЕНИЯ в словарь (не значения), dq = 8 байт потому что).
    mov rdi, rax						;Кладем в rdi наш ключ
    push rax							;Сохраняем на стеке данный найденный ключ
    call string_length						;Смотрим на длину нашей строки (то есть ключа)
    pop rdi							;Снимаем в rdi наш сохраненный ранее ключ
    add rdi, rax						;!!! Добавляем к адресу НАЧАЛА КЛЮЧА вычисленную длину самого ключа, чтобы перейти на адрес нуль-терминатора этого ключа, то есть в конец (мы ведь не знаем длину ключа, поэтому "извращаемся" таким образом, чтобы получить значение по ключу)
    inc rdi							;Увеличиваем rdi на 1, чтобы получить адрес ЗНАЧЕНИЯ по нашему ключу
    call print_string						;Выводим данное значение по введенному ключу
    call print_newline
    mov rdi, 0
    call exit

.not_enough:							;Метка для кода с ошибкой "Мало места"
    mov rdi, not_enough					;В rdi кладем строку с ошибкой
    call print_error						;Вызываем из lib.asm функцию print_error чтобы вывести в поток ошибок
    jmp .end							;И завершаем программу

.not_found:							;Метка для кода с выводом ошибки "Ключ не найден"
    mov rdi, not_found						;Кладем rdi в строку с ошибкой
    call print_error						;Вызываем из lib.asm функцию print_error чтобы вывести в поток ошибок

.end:								;Метка завершения программы
    call print_newline						;Делаем перенос строки, чтобы вывод был читабелен
    mov rdi, 1
    call exit							;И завершаем исполнение программы
