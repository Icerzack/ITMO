// вы можете реализовать эти функции для более удобного считывания чисел
int64_t read_int64();
size_t read_size();

// заполнить уже выделенный массив array размера size числами
void array_int_fill( int64_t* array, size_t size ) {
    for (size_t i=0; i<size; i++) {
        scanf("%" SCNd64, &array[i]);
    }
}

// Считать размер массива в *size, выделить память под массив и заполнить его числами.
int64_t* array_int_read( size_t* size ) {
    scanf("%zu", size);
    int64_t* array = malloc(*size * sizeof(int64_t));
    array_int_fill(array, *size);
    return array;
}