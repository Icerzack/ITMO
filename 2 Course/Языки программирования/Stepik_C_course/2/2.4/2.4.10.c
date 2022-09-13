struct maybe_int64 list_at(const struct list* list, size_t idx ) {
    const struct list* current = list;
    size_t count = 0;
    while (current != NULL) {
        if (count == idx){
            return (struct maybe_int64) { .value = current->value, .valid = true };
        }
        count++;
        current = current->next;
    }
    return none_int64;
}