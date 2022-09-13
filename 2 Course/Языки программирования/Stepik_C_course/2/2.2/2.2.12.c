// Вам доступны следующие функции:
size_t read_size();
int64_t* array_int_min( int64_t* array, size_t size );
int64_t** marray_read( size_t* rows, size_t* sizes[] );
void marray_free( int64_t** marray, size_t rows );
void marray_print(int64_t** marray, size_t* sizes, size_t rows);


// Указатель на минимальное из двух чисел.
// если хотя бы одно число NULL, то возвращать второе
// если оба числа NULL результат NULL
int64_t* int64_ptr_min(int64_t* x, int64_t* y) {
    if (x == NULL && y == NULL) {
        return NULL;
    } else if (x != NULL && y == NULL) {
        return x;
    } else if (x == NULL && y != NULL) {
        return y;
    } else if (*x < *y) {
        return x;
    } else {
        return y;
    }
}

// Вернуть адрес минимального элемента массива массивов
int64_t* marray_int_min(int64_t** marray, size_t* sizes, size_t rows) {
    
    int64_t* min = NULL;

    for (size_t i = 0; i < rows; i++) {
        int64_t* minString = array_int_min(marray[i], sizes[i]);
        min = int64_ptr_min(min, minString);    
    }

    return min;
}

// Вычесть m изо всех элементов массива
void marray_normalize( int64_t** marray, size_t sizes[], size_t rows, int64_t m ) {
    
    for (size_t i = 0; i < rows; i++) {
        for (size_t j = 0; j < sizes[i]; j++) {
            (marray[i])[j] = (marray[i])[j] - m;  
        }
    }

}

// Прочитать, найти минимум и нормализовать массив, вывести результат
void perform() {

    size_t rows;
    size_t* sizes;
    int64_t** marray = marray_read(&rows, &sizes);

    int64_t* min;
    min = marray_int_min(marray, sizes, rows);

    if (min != NULL) {
        marray_normalize(marray, sizes, rows, *min);
    }
    
    marray_print(marray, sizes, rows);
     
    marray_free(marray, rows);
    
    free(sizes);
}