enum move_dir { MD_UP, MD_RIGHT, MD_DOWN, MD_LEFT, MD_NONE };



// Робот и его callback'и
// callback'ов может быть неограниченное количество
struct robot {
  const char* name;
  struct list* callbacks;
};

// Определите тип обработчика событий move_callback с помощью typedef
typedef void (move_callback)(enum move_dir);

struct list {
  move_callback* callback;
  struct list* next;
};

void list_destroy(struct robot* robot) {
    struct list *l = NULL;
    while (robot->callbacks) {
        l = robot->callbacks->next;
        free(robot->callbacks);
        robot->callbacks = l;
    }
}

struct list* node_create(move_callback callback) {
    struct list *l = malloc (sizeof (struct list));
    l->callback = callback;
    l->next = NULL;
    return l;
}

void list_add_front(struct list** old, move_callback callback) {
    struct list *l = node_create(callback);
    l->next = *old;
    *old = l;
}

// Добавить callback к роботу, чтобы он вызывался при движении
// В callback будет передаваться направление движения
void register_callback(struct robot* robot, move_callback new_cb) {
    list_add_front(&(robot->callbacks), new_cb);
}

// Отменить все подписки на события.
// Это нужно чтобы освободить зарезервированные ресурсы
// например, выделенную в куче память
void unregister_all_callbacks(struct robot* robot) {
    list_destroy(robot);
}

// Вызывается когда робот движется
// Эта функция должна вызвать все обработчики событий
void move(struct robot* robot, enum move_dir dir) {
   struct list* next = robot->callbacks;
   while (next) {
       next->callback(dir);
       next = next->next;
   }
}