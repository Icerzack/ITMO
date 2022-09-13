static struct stack_interface {
  struct stack_int_interface {
    struct stack_int *(*create)();
    bool (*empty)(struct stack_int const *s);
    bool (*full)(struct stack_int const *s);
    void (*destroy)(struct stack_int *s);
    bool (*push)(struct stack_int *s, item i);
    struct maybe_item (*pop)(struct stack_int *s);
  } int64;
} const stack = {{stack_int_create, stack_int_empty, stack_int_full,
                  stack_int_destroy, stack_int_push, stack_int_pop}};




