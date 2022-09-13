void list_add_back( struct list** old, int64_t value ) {
    if(*old == NULL){
        list_add_front(old, value);
    } else {
    struct list *l = list_last(*old);
    struct list *tmp = malloc(sizeof(struct list));
    tmp->value = value;
    tmp->next = NULL;
    l->next = tmp;
    }
}