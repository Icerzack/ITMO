struct maybe_int64 maybe_read_int64() {
    int64_t value = 0;
    if (scanf("%" SCNd64, &value) != EOF)
        return (struct maybe_int64) { .value = value, .valid = true };
    return none_int64;
}