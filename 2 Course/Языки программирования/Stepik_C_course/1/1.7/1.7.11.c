void array_reverse(int* array, int size) {
    int temp;
    int start = 0;
    while (start < size)
    {
        temp = array[start];  
        array[start] = array[size-1];
        array[size-1] = temp;
        start++;
        size--;
    }
        
}

void array_reverse_ptr(int* array, int* limit) {
    array_reverse(array, *array + *limit);
}