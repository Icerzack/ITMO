/*
struct list {
    int64_t value;
    struct list* next;
};
*/
/* Вы можете пользоваться этими функциями */
void print_int64(int64_t i);
struct list* node_create( int64_t value );
void list_destroy( struct list* list );

static int64_t sum( int64_t x, int64_t y) { return x + y; }

typedef int64_t folding(int64_t, int64_t);

/* Свернуть список l с помощью функции f. */
int64_t list_fold( 
        const struct list* l, 
        int64_t init, 
        folding f) {
    int64_t final_result = init;
    while(l){
        final_result = f(final_result, l->value);
        l=l->next;
    }
    return final_result;
}

/* Просуммируйте список с помощью list_fold и sum */
int64_t list_sum( const struct list* l ) {
    return list_fold(l, 0, sum);
}