
# CC=gcc
LD=$(CC)
CFLAGS=-ggdb -O0 -Isrc --std=c17 -Wall -pedantic -Wextra -Werror -DDEBUG
LDFLAGS=

TARGETDIR=target
BUILDDIR=build
TESTSDIR=tests
COMMON_SRCDIR=src
SOLUTION_SRCDIR=../src
SOLUTION_INCDIR=../src # change it if you has another include directory

CFLAGS += -I$(SOLUTION_INCDIR)

SOLUTION_SOURCES=$(shell find $(SOLUTION_SRCDIR) -type f -name '*.c' 2>/dev/null)
SOLUTION_HEADERS=$(shell find $(SOLUTION_INCDIR) -type f -name '*.h' 2>/dev/null)
SOLUTION=$(SOLUTION_SOURCES) $(SOLUTION_HEADERS)

COMMON_SOURCES=$(shell find $(COMMON_SRCDIR) -type f -name '*.c' 2>/dev/null)
COMMON_HEADERS=$(shell find $(COMMON_SRCDIR) -type f -name '*.h' 2>/dev/null)
TEST_SOURCES=$(shell find $(TESTSDIR) -type f -name '*.c' 2>/dev/null)
TEST_HEADERS=$(shell find $(TESTSDIR) -type f -name '*.h' 2>/dev/null) $(COMMON_HEADERS)

COMMON_OBJECTS=$(patsubst %.c,$(BUILDDIR)/%.o,$(COMMON_SOURCES))
TEST_OBJECTS=$(patsubst %.c,$(BUILDDIR)/%.o,$(TEST_SOURCES))
TARGETS=$(patsubst $(BUILDDIR)/%.o,$(TARGETDIR)/%,$(TEST_OBJECTS))
TESTS=$(patsubst $(TARGETDIR)/$(TESTSDIR)/%,test_%,$(TARGETS))

.PHONY: all test $(TESTS)
.SILENT: test list $(TESTS)

# suppress default targets
.SUFFIXES:

all: test

list:
	arr=($(TESTS)) ; for test in "$${arr[@]}"; do echo "$$test"; done

test: $(TESTS)
	echo 'All tests passed successful!'

$(TESTS): test_% : $(TARGETDIR)/$(TESTSDIR)/%
	echo "Running test set $(patsubst test_%,\"%\",$@)..."
	./$< || (echo 'Test set failed' && false)
	echo 'Test set passed successful!'

$(TARGETS): $(TARGETDIR)/% : $(BUILDDIR)/%.o $(COMMON_OBJECTS)
	@mkdir -p $(@D)
	$(LD) -o $@ $^ $(LDFLAGS)

$(BUILDDIR)/%.o: %.c $(TEST_HEADERS) $(SOLUTION)
	@mkdir -p $(@D)
	$(CC) -c $< -o $@ $(CFLAGS)

clean:
	rm -rf $(TARGETDIR) $(BUILDDIR)
