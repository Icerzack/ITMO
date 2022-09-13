// Вам доступны эти функции из прошлых заданий
size_t read_size();
void array_int_fill( int64_t* array, size_t size );
int64_t* array_int_read( size_t* size );

int64_t** marray_read( size_t* rows, size_t** sizes ) {
    *rows = read_size();
    *sizes = malloc(*rows * sizeof(size_t));
    int64_t** marray = (int64_t**) malloc(*rows * sizeof(int64_t));
    for(size_t i = 0; i < *rows; i++){
        (*sizes)[i] = read_size();
        (marray)[i] = malloc((*sizes)[i] * sizeof(int64_t));
        for (size_t j = 0; j < (*sizes)[i]; j++){
            scanf("%" SCNd64, &marray[i][j]);
        }
    }
    return marray;
}

void marray_print(int64_t** marray, size_t* sizes, size_t rows) {
    for( size_t i = 0; i < rows; i = i + 1 ) {
        array_int_print( marray[i], sizes[i] );
        print_newline();
    }
}