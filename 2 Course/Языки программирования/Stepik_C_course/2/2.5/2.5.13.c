struct heap_string {
  char* addr;
};

// скопировать в кучу
struct heap_string halloc( const char* s ){
    char* copy = malloc((strlen(s)+1)*(sizeof(char)));
    for (size_t i = 0; i < strlen(s)+1; i++){
        copy[i] = s[i];
    }
    return ((struct heap_string) {.addr = copy});
}

// освободить память
void heap_string_free( struct heap_string h ){
    if (h.addr){
        free(h.addr);
    }
}