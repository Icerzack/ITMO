static bool divides(int64_t x, int64_t y) { return x % y == 0; }
static void print_int_space(int64_t x) {printf("%" PRId64 " ", x); }
static int64_t read_int() { int64_t x; scanf("%" PRId64, &x); return x; }

void print_divisors(int64_t n) {
  if (n > 0) {
    for (int64_t i = 1; i <= n; i = i + 1){
      if (divides(n, i))
        print_int_space(i);} 
        printf("$");
  } else printf("No");
}