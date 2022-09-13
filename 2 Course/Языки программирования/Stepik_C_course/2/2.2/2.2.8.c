int64_t* array_int_min( int64_t* array, size_t size ) {
    size_t temp = 0;
    if(size == 0){
        return NULL;
    }
    else {  
        for (size_t i = 1; i < size; i++){
            if (array[i] < array[temp]){
                temp = i;
            }
        }
    }
    return &array[temp];
}