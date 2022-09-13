void list_add_front( struct list** old, int64_t value );

// создать перевернутую копию списка
struct list* list_reverse( const struct list * list ) {
    const struct list* l = list;
    struct list* reversed = NULL;
    while(l){
        list_add_front(&reversed, l->value);
        l = l->next;
    }
    return reversed;
}