dic: 	main.o dict.o lib.o
		ld -o dic main.o dict.o lib.o

main.o: main.asm colon.inc words.inc
		nasm -felf64 main.asm

dict.o: dict.asm 
		nasm -felf64 dict.asm

lib.o: 	lib.asm 
		nasm -felf64 lib.asm

.PHONY: clean

clean:
		rm -f *.o dic
