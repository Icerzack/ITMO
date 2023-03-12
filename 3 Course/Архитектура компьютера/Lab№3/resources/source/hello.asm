section .data
    H: 'h'
    E: 'e'
    L: 'l'
    O: 'o'
    space: ' '
    W: 'w'
    R: 'r'
    D: 'd'
    !: '!'

section .text
.start:
       LD %r1, (H)
       ST #STDOUT, %r1
       LD %r1, (E)
       ST #STDOUT, %r1
       LD %r1, (L)
       ST #STDOUT, %r1
       LD %r1, (L)
       ST #STDOUT, %r1
       LD %r1, (O)
       ST #STDOUT, %r1
       LD %r1, (space)
       ST #STDOUT, %r1
       LD %r1, (W)
       ST #STDOUT, %r1
       LD %r1, (O)
       ST #STDOUT, %r1
       LD %r1, (R)
       ST #STDOUT, %r1
       LD %r1, (L)
       ST #STDOUT, %r1
       LD %r1, (D)
       ST #STDOUT, %r1
       LD %r1, (!)
       ST #STDOUT, %r1
       HLT