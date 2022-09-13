size_t list_length(const struct list* list) {
    const struct list *l = list;
    size_t count = 0;
    while(l)  {
        count++;
        l = l->next;
    }
    return count;
}