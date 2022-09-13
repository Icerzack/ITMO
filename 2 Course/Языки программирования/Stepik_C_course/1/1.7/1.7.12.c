void array_fib(int* array, int* limit) {
  if(limit == array){
      return;
  } else if(limit == array + 1){
      *array = 1;
      return;
  }
  *array = 1;
  *(array+1) = 1;
  if(limit-array > 1){
      for(int* current = array; current < limit -2; current++){
          *(current+2) = *current + *(current+1);
      }
  }
}