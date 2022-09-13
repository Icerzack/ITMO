void list_destroy( struct list* list ) {
    struct list *l = list;
    while (list) {
        list = list->next;
        free(l);
        l = list;
    }
}