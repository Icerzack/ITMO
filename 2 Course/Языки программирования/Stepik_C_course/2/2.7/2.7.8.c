/*
struct list {
    int64_t value;
    struct list* next;
};
*/
void print_int64(int64_t i);

struct list* node_create( int64_t value );
void list_destroy( struct list* list );


/*  Сгенерировать список длины sz с помощью значения init и функции f
 Результат: init, f(init), f(f(init)), ... */
struct list* list_iterate( int64_t init, size_t sz, int64_t(f)(int64_t)) {
   if(sz==0){
       return NULL;
   } else {
       struct list* l = node_create(init);
       size_t counter = 1;
       while(counter < sz){
           list_add_back(&l, f(list_last(l)->value));
           counter++;
       }
       return l;
   }
}