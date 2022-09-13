size_t count_zeroes( void const* data, size_t sz ) {
    size_t nulls = 0;
    int8_t const* bytes = data;
    for (size_t i = 0; i < sz; i++) {
        if(bytes[i] == 0){
            nulls+=1;
        }
    }
    return nulls;
}