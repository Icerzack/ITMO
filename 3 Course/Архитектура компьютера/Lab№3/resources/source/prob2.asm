section .text

.start:
    MOV %r5, 2
    MOV %r1, 1
    MOV %r2, 2

.fib_loop:
    ADD %r1, %r1, %r2
    CMP %r1, 4000000
    JG .end_loop
    MOD %r4, %r1, 2
    JE .add_sum
    MOV %r3, %r1
    MOV %r1, %r2
    MOV %r2, %r3
    JMP .fib_loop

.add_sum:
    ADD %r5, %r5, %r1
    MOV %r3, %r1
    MOV %r1, %r2
    MOV %r2, %r3
    JMP .fib_loop

.end_loop:
    ST #STDOUT, %r5
    HLT
