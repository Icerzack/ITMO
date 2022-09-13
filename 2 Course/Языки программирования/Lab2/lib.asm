;!!! Все возможные спорные и непонятные моменты я постарался расписать в виде комментариев.



section .data

newline_char: db 10
numbers: db '0123456789', 0

%define stderr 2
%define stdout 1

section .text

global string_length
global print_newline
global print_string
global read_word
global string_equals
global print_error
global exit

; Принимает код возврата и завершает текущий процесс
exit:
    mov     rax, 60
    syscall

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    mov rsi, stdout
    jmp print_in_stream


; Выводит данные в поток ошибок
print_error:
    mov rsi, stderr

; Принимает указатель на строку и поток, в который выводить строку
print_in_stream:
    call string_length
    push rdi
    push rsi
    pop rdi
    pop rsi
    mov rdx, rax
    mov rax, 1
    syscall
    ret


; Выводит беззнаковое 8-байтовое число в десятичном формате
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
print_uint:
    mov rax, rdi
    mov r8, 0 ;просто счетчик цифр
    mov r9, 10;делитель (на 10, чтобы разбирать каждую цифру)

    .parsingNumber:
        mov rdx, 0
        div r9
        push rdx ;пушим остаток
        inc r8
        cmp rax, 0
        jne .parsingNumber

    .printingNumber:
        pop rax ;снимаем остатки
        lea rsi, [numbers+rax] ;выводим цифру по порядковому номеру из строки с числами
        mov rax, 1
        mov rdi, 1
        mov rdx, 1
        syscall
        dec r8
        cmp r8, 0
        jne .printingNumber
        ret

; Выводит знаковое 8-байтовое число в десятичном формате
print_int:
    cmp rdi, 0
    jge print_uint
    neg rdi
    push rdi
    mov rdi, '-'
    call print_char
    pop rdi
    call print_uint
    ret

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
    mov r8, 0 ;здесь будет храниться само считываемое число
    mov r9, 0 ;а здесь его считаемая длина
    mov r10, 10 ;ну и делитель (равный 10, чтобы разбирать число по цифре)

    .parseFirstElement:
        mov r8b, [rdi]
        inc rdi
        cmp r8b, '0'
        jl .endOrFail
        cmp r8b, '9'
        jg .endOrFail
        mov rax, 0
        sub r8b, '0'
        mov al, r8b
        inc r9

    .parseLoop:
        mov r8b, [rdi]
        inc rdi
        cmp r8b, '0'
        jl .endOrFail
        cmp r8b, '9'
        jg .endOrFail
        inc r9
        mul r10
        sub r8b, '0'
        add rax, r8
        jmp .parseLoop

    .endOrFail:
        mov rdx, r9
        ret

; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был)
; rdx = 0 если число прочитать не удалось
parse_int:
    cmp byte[rdi], '-' ;сравниваем первый элемент со знаком
    je .ifNegative
    call parse_uint
    ret

    .ifNegative:
        inc rdi
        call parse_uint
        cmp rdx, 0
        je .end
        inc rdx
        neg rax
        ret

    .end:
        mov rax, 0
        ret

; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    mov rax, 0

    .loop:
        cmp byte [rdi+rax], 0
        je .end
        inc rax
        jmp .loop

    .end:
        ret

; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
string_equals:
    mov r8, 0 ;здесь храним первую строчку
    mov r9, 0 ;а здесь вторую

    .loop:
        mov r8b, byte [rdi]
        mov r9b, byte [rsi]
        cmp r8, r9
        jne .end
        inc rdi
        inc rsi
        cmp r8, 0
        jne .loop
        mov rax, 1
        ret

    .end:
        mov rax, 0
        ret

; Принимает указатель на строку, указатель на буфер и длину буфера
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
string_copy:
	call string_length ;таким образом сразу проверяем возможность копирования
	cmp rdx, rax
	jl .notEnough

    .loop:
        mov rdx, [rdi] ;так как mov [rsi], [rdi] не возможен, то используем временный (уже не нужный) регистр rdx
        mov [rsi], rdx
        cmp byte [rdi], 0
        je .endOfString
        inc rdi
        inc rsi
        jmp .loop

    .notEnough:
        mov rax, 0

    .endOfString:
        ret

; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    mov rax, 0
    mov rdi, 0
    push 0         ;аллоцируем место в стеке для элемента
    mov rsi, rsp
    mov rdx, 1
    syscall
    pop rax
    cmp rax, 0x0
    je .ifEndOfStream
    ret

    .ifEndOfStream:
        mov rax, 0
        ret

; Принимает: адрес начала буфера, размер буфера
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор

read_word:
    mov r8, rdi	; указатель на текущий, записываемый элемент.
    mov r9, rsi  ; переданный ИЗНАЧАЛЬНЫЙ размер.
    mov r10, rdi ; конечный адрес расположения буфера.
    mov r11, 0	; посчитанный ИТОГОВЫЙ размер.

   .readLoop:
	    cmp r11, r9
	    jae .notEnough
	    push r11
	    call read_char
	    pop r11
	    cmp al, 0x0
	    je .endOfString
	    cmp al, 0x9
	    je .check
	    cmp al, 0xA
	    je .check
	    cmp al, 0x20
	    je .check
        mov byte[r8], al     ; записываем очередной символ в буфер
        inc r8
        inc r11         ; увеличиваем счетчик
        jmp .readLoop

    .notEnough:
        mov rax, 0
        ret

    .check:
        cmp r11, 0
        je .readLoop

    .endOfString:
        mov byte[r8], 0
        mov rax, r10
        mov rdx, r11
        ret

; Принимает код символа и выводит его в stdout
print_char:
    push rdi
    mov rdx, 1
    mov rax, 1
    mov rsi, rsp
    mov rdi, 1
    syscall
    pop rdi ;чтобы не было ошибки, снимем уже ненужный элемент со стека
    ret

; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    mov rax, 1
    mov rdi, 1
    mov rsi, newline_char
    mov rdx, 1
    syscall
    ret
