// Мы хотим, чтобы в структуре user хранились ссылки только на строчки из кучи.
typedef struct { char* addr; } string_heap ;

/*  Тип для идентификаторов пользователей
    и его спецификаторы ввода и вывода для printf */
typedef uint64_t uid;
#define PRI_uid PRIu64
#define SCN_uid SCNu64

enum city {C_SARATOV, C_MOSCOW, C_PARIS, C_LOS_ANGELES, C_OTHER};

/*  Массив, где элементам перечисления сопоставляются их текстовые представления */
const char* city_string[] = {
  [C_SARATOV] = "Saratov",
  [C_MOSCOW] = "Moscow",
  [C_PARIS] = "Paris",
  [C_LOS_ANGELES] = "Los Angeles",
  [C_OTHER] = "Other"
};


struct user {
  const uid id;
  const string_heap name;
  enum city city;
};

//---------------------------------------------------------------

int id_comparer(const struct user* x, const struct user* y) {
  if (x->id < y->id) return -1;
  if (x->id > y->id) return 1;
  return 0;
}

int id_void_comparer(const void* _x, const void* _y ) {
  return id_comparer(_x, _y );
}

//--------------------------------------------------------------

int name_comparer(const struct user* x, const struct user* y) {
  return strcmp(x->name.addr, y->name.addr);
}

int name_void_comparer(const void* _x, const void* _y ) {
  return name_comparer(_x, _y );
}

//--------------------------------------------------------------

int city_comparer(const struct user* x, const struct user* y) {
  return strcmp(city_string[x->city], city_string[y->city]);
}

int city_void_comparer(const void* _x, const void* _y) {
  return city_comparer(_x, _y );
}

//-------------------------------------------------------------

void users_sort_uid(struct user users[], size_t sz) {
    qsort(users, sz, sizeof(struct user), id_void_comparer);
}


void users_sort_name(struct user users[], size_t sz) {
    qsort(users, sz, sizeof(struct user), name_void_comparer);
}


void users_sort_city(struct user users[], size_t sz) {
    qsort(users, sz, sizeof(struct user), city_void_comparer);
}