/*
struct list {
    int64_t value;
    struct list* next;
};
*/
/* Вы можете пользоваться следующими функциями */
void print_int64(int64_t i);
struct list* node_create( int64_t value );

/*  Создать новый список, в котором каждый элемент получен из соответствующего
    элемента списка l путём применения функции f */
struct list* list_map(const struct list* l, int64_t (f) (int64_t))  {
    struct list* temp = NULL;
    while(l!=NULL){
        list_add_back(&temp, f(l->value));
        l=l->next;
    }
    return temp;
}

int64_t just_nothing(int64_t jn){
    return jn;
}

int64_t abs_value(int64_t num){
    if(num < 0) return num*(-1);
    else return num;
}

struct list* list_copy(const struct list* l ) {
    return list_map(l, just_nothing);
}

struct list* list_abs(const struct list* l ) {
    return list_map(l, abs_value);
}