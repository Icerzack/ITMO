/*

struct array_int {
  int64_t* data;
  size_t size;
};

struct stack {
  size_t count;
  struct array_int data;
};

*/

// Стек полный
bool stack_is_full( const struct stack * s, size_t size){
    return s->count + size > s->data.size;
}
// Стек пустой
bool stack_is_empty( const struct stack * s, size_t size){
    return s->count < size;
}




void interpret(struct vm_state* state, ins_interpreter * const  (actions)[]) {
    for (; state->ip ;) {
        const union ins* ins = state->ip;

        const struct ins_descr* ins_descr = instructions + ins->opcode;

        if (stack_is_empty(&state->data_stack, ins_descr->stack_min)){
            printf("Stack underflow\n");
            break;
        }
        else if (stack_is_full(&state->data_stack, (size_t) ins_descr->stack_delta)) {
            printf("Stack overflow\n");
            break;
        }
        else {
            actions[ins->opcode](state);
            if (!ins_descr->affects_ip) { state->ip = state->ip + 1; }
        }
    }
}