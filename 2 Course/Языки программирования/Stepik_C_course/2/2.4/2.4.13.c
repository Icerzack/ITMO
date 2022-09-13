struct list* list_read() {
    struct maybe_int64 maybe = (maybe_read_int64());
    struct list *head = NULL;
    struct list *l = NULL;
    if(maybe.valid != 0){
        l = node_create(maybe.value);
        l->next = NULL;
        head = l;
        maybe = maybe_read_int64();
    } else {
        return l;
    }
    while(maybe.valid != 0) {
        struct list *tmp = node_create(maybe.value);
        tmp->next = NULL;
        l->next = tmp;
        l = tmp;
        maybe = maybe_read_int64();
    }
    return head;
}