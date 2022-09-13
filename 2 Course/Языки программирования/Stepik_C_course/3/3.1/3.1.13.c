/* Вам доступны:


struct maybe_int64 {
    int64_t value; 
    bool valid; 
};

struct maybe_int64 some_int64(int64_t i);

extern const struct maybe_int64 none_int64;

void maybe_int64_print( struct maybe_int64 i );
struct maybe_int64 maybe_read_int64();
void print_int64(int64_t i)
*/

void interpret_push(struct vm_state* state) {
  stack_push(&state->data_stack, state->ip->as_arg64.arg);
}

void interpret_iread(struct vm_state* state ) {
  stack_push(&state->data_stack, maybe_read_int64().value);
}
void interpret_iadd(struct vm_state* state ) {
  stack_push(&state->data_stack, (stack_pop(&state->data_stack).value + stack_pop(&state->data_stack).value));

}
void interpret_iprint(struct vm_state* state ) {
  print_int64(stack_pop(&state->data_stack).value);
}

/* Подсказка: можно выполнять программу пока ip != NULL,
    тогда чтобы её остановить достаточно обнулить ip */
void interpret_stop(struct vm_state* state ) {
    //ненужно получается
}

typedef void (*interp)(struct vm_state *);
interp interpreters[] = {
    [BC_PUSH] = interpret_push,
    [BC_IADD] = interpret_iadd,
    [BC_IREAD] = interpret_iread,
    [BC_STOP] = interpret_stop,
    [BC_IPRINT] = interpret_iprint
};

void interpret(struct vm_state* state) {
    while ((state->ip)->opcode != BC_STOP){
        interpreters[(state->ip)->opcode](state);
        state->ip = state->ip+1;
    }
    (state->ip)=0;
}