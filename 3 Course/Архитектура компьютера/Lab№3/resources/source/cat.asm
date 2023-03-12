section .text
.start:
    .read_char:
        LD %r2, #STDIN
        ST #STDOUT, %r2
        JMP .read_char