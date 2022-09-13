;!!! Все возможные спорные и непонятные моменты я постарался расписать в виде комментариев.
section .rodata
newline_char: equ 10
space_char: equ 32
tab_char: equ 9
null_char: equ 0


section .data

section .text

; Принимает код возврата и завершает текущий процесс
exit:
    mov     rax, 60
    syscall

; Выводит беззнаковое 8-байтовое число в десятичном формате
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.


; Выводит знаковое 8-байтовое число в десятичном формате
print_int:
    cmp rdi, 0
    jge print_uint
    neg rdi
    push rdi
    mov rdi, '-'
    call print_char
    pop rdi

print_uint:
    mov rax, rdi                        
    mov r8, 10                          ; Делитель, при делении на 10 будем получать цифру числа
    push 0                              ; Потом, когда достанем со стека, будет обозначать конец числа

.loop:
    xor rdx, rdx 
    div r8                              ; В rax записывается частное, а в rdx остаток
    add rdx, '0'                      ; Чтобы получить ASCII, нужно прибавить к числу 0x30
    push rdx
    cmp rax, 0                          
    ja .loop                            ; Если частное больше нуля, то значит остались цифры и продолжаем делить

.print:
    pop rdi
    cmp rdi, 0
    jz .end                             ; Если достали со стека 0, значит мы прошлись по всем символам
    call print_char                     ; Иначе это символ, и мы его печатаем
    jmp .print

.end:
    ret

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
    xor rax, rax
    xor r8, r8
    xor r9, r9
    mov r10, 10
.read_digit:
    cmp byte[rdi], '0'     ; Сравниваем ASCII код цифры с ASCII кодом 0
    jb .end                 ; Если код цифры меньше, то это не цифра
    cmp byte[rdi], '9'     ; Сравниваем ASCII код цифры с ASCII кодом 9
    ja .end                 ; Если код цифры больше, то это не цифра
    mul r10                 ; Умножаем разряд на 10
    mov r8b, byte[rdi]      ; Записываем цифру в r8
    sub r8b, '0'           ; Чтобы перевести ASCII код в цифру отнимаем 0x30
    add rax, r8             ; Добавляем цифру к числу
    inc rdi                 ; Смещаем указатель на следующий символ
    inc r9                  ; Увеличиваем длину числа на 1
    jmp .read_digit         ; Читаем следующий символ

.end:
    mov rdx, r9             ; Записываем длину числа в rdx
    ret

; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был)
; rdx = 0 если число прочитать не удалось
parse_int:
    cmp byte[rdi], '-' ;сравниваем первый элемент со знаком
    je .ifNegative
    jmp parse_uint

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
	jle .notEnough

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
	    cmp al, null_char
	    je .endOfString
	    cmp al, tab_char
	    je .check
	    cmp al, newline_char
	    je .check
	    cmp al, space_char
	    je .check
        mov [r8], al     ; записываем очередной символ в буфер
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
        mov byte [r8], 0x0
        mov rax, r10
        mov rdx, r11
        ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    call string_length
    mov rsi, rdi
    mov rdx, rax
    mov rax, 1
    mov rdi, 1
    syscall
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
