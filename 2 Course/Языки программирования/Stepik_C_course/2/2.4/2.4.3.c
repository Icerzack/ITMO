struct list {
  int64_t value;
  struct list* next;
};

struct list* node_create( int64_t value ) {
    struct list *l = malloc (sizeof (struct list));
    l->value = value;
    return l;
}