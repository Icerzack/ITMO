const union ins program[] = {
    {BC_IREAD},
    {.as_arg64 = {BC_PUSH, .arg = 10}},
    {BC_ISUB},
    {.as_arg64 = {BC_PUSH, .arg = 2}},
    {BC_IDIV},
    {BC_IPRINT},
    {BC_STOP}
};