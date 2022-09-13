int is_whitespace(char c) { return c == ' ' || c == '\t' || c == '\n'; }

int string_count(char* str) { 
   int c = 0;
   while (str[c] != '\0')
      c++;

   return c;
}

int string_words(char* str)  {
    int OUT = 0;
    int IN = 1;
    int state = OUT;
    int wc = 0;
 
    while (*str)
    {
        if (*str == ' ' || *str == '\n' || *str == '\t')
            state = OUT;
        else if (state == OUT)
        {
            state = IN;
            ++wc;
        }
        ++str;
    }
 
    return wc;
}