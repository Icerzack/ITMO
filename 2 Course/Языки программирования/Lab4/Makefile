CFLAGS=--std=c17 -Wall -pedantic -Isrc/ -ggdb -Wextra -Werror  -DDEBUG
BUILDDIR=build
SRCDIR=src
CC=gcc


all: $(BUILDDIR)/mem.o $(BUILDDIR)/util.o $(BUILDDIR)/mem_debug.o $(BUILDDIR)/test.o $(BUILDDIR)/main.o
	$(CC) -o $(BUILDDIR)/lab4 $^

build:
	mkdir -p $(BUILDDIR)

exec: all
	./$(BUILDDIR)/lab4
	test

$(BUILDDIR)/test.o: $(SRCDIR)/test.c build
	$(CC) -c $(CFLAGS) $< -o $@

$(BUILDDIR)/main.o: $(SRCDIR)/main.c build
	$(CC) -c $(CFLAGS) $< -o $@

$(BUILDDIR)/mem.o: $(SRCDIR)/mem.c build
	$(CC) -c $(CFLAGS) $< -o $@

$(BUILDDIR)/mem_debug.o: $(SRCDIR)/mem_debug.c build
	$(CC) -c $(CFLAGS) $< -o $@

$(BUILDDIR)/util.o: $(SRCDIR)/util.c build
	$(CC) -c $(CFLAGS) $< -o $@

test:
	@+cd tester; make CC=$(CC)

clean:
	rm -rf $(BUILDDIR)
