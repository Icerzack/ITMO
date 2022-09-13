int64_t list_sum( const struct list* list ) {
    const struct list *l = list;
    int64_t count = 0;
    while(l)  {
        count = count + l->value;
        l = l->next;
    }
    return count;
}