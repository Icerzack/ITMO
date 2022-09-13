// tests
#include "mem.h"
#include "mem_internals.h"
#include "util.h"
#include <stdlib.h>

#define DEFAULT_SIZE 1000

void* target_memory_heap;
struct block_header *memory_block;

static size_t get_number_of_blocks(struct block_header* block) {
    size_t count = 0;
    while (block->next){
        count++;
        block = block->next;
    }
    return count;
}

void test1() {
    printf("\nTest #1: Обычное успешное выделение памяти\n");
    size_t test_size = 100;
    void* malloc = _malloc(test_size);
    debug_heap(stdout, memory_block);
    size_t capacity = memory_block->capacity.bytes;
    if (capacity == test_size) {
        printf("\nSuccess: Обычное успешное выделение памяти\n");
    } else {
        printf("\nFailed: Обычное успешное выделение памяти\n");
        exit(1);
    }
    _free(malloc);
}

void test2() {
    printf("\nTest #2: Освобождение одного блока из нескольких выделенных\n");
    size_t test_size1 = 100;
    size_t test_size2 = 200;
    void* malloc1 = _malloc(test_size1);
    void* malloc2 = _malloc(test_size2);
    debug_heap(stdout, memory_block);
    size_t block_number1 = get_number_of_blocks(memory_block);
    _free(malloc2);
    debug_heap(stdout, memory_block);
    size_t block_number2 = get_number_of_blocks(memory_block);
    _free(malloc1);
    if(block_number1 == 2 && block_number2 == 1) {
        printf("\nSuccess: Освобождение одного блока из нескольких выделенных\n");
    } else {
        printf("\nFailed: Освобождение одного блока из нескольких выделенных\n");
        exit(1);
    }
}

void test3() {
    printf("\nTest #3: Освобождение двух блоков из нескольких выделенных\n");
    size_t test_size1 = 100;
    size_t test_size2 = 10;
    size_t test_size3 = 20;
    void* malloc1 = _malloc(test_size1);
    void* malloc2 = _malloc(test_size2);
    void* malloc3 = _malloc(test_size3);
    debug_heap(stdout, memory_block);
    _free(malloc3);
    _free(malloc2);
    size_t block_number = get_number_of_blocks(memory_block);
    debug_heap(stdout, memory_block);
    _free(malloc1);
    if (block_number == 1) {
        printf("\nSuccess: Освобождение двух блоков из нескольких выделенных\n");
    } else {
        printf("\nFailed: Освобождение двух блоков из нескольких выделенных\n");
        exit(1);
    }
}

void test4() {
    printf("\nTest #4: Память закончилась, новый регион памяти расширяет старый\n");
    const size_t test_size1 = 990;
    const size_t test_size2 = 110;
    void* malloc1 = _malloc(test_size1);
    void* malloc2 = _malloc(test_size2);
    debug_heap(stdout, memory_block);
    size_t block_number = get_number_of_blocks(memory_block);
    _free(malloc1);
    _free(malloc2);
    if (block_number == 2) {
        printf("\nSuccess: Память закончилась, новый регион памяти расширяет старый\n");
    } else {
        printf("\nFailed: Память закончилась, новый регион памяти расширяет старый\n");
        exit(1);
    }
}

void test5() {
    printf("\nTest #5: Память закончилась, старый регион памяти не расширить из-за другого выделенного диапазона адресов, новый регион выделяется в другом месте\n");
    debug_heap(stdout, memory_block);
    const size_t test_size1 = 990;
    const size_t test_size2 = 110;
    void* malloc1 = _malloc(test_size1);
    debug_heap(stdout, memory_block);
    map_pages(memory_block, 900, MAP_SHARED);
    debug_heap(stdout, memory_block);
    void* malloc2 = _malloc(test_size2);
    debug_heap(stdout, memory_block);
    _free(malloc1);
    _free(malloc2);
    printf("\nSuccess: Память закончилась, старый регион памяти не расширить из-за другого выделенного диапазона адресов, новый регион выделяется в другом месте\n");
}

// test setup
void start_tests() {
    target_memory_heap = heap_init(DEFAULT_SIZE);
    memory_block = (struct block_header *) target_memory_heap;
    const bool init_error_occured = target_memory_heap == NULL || memory_block == NULL;
    if (init_error_occured){
        printf("Tests failed heap_init \n");
        exit(1);
    }
    printf("\nSTARTING TESTS\n");
    printf("\n--------------------------------------------------\n\n");
    test1();
    test2();
    test3();
    test4();
    test5();
    printf("\n--------------------------------------------------\n");
    printf("\nAll tests are passed. Congratulations!\n");
}
