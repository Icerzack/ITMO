// position -- адрес указателя на первый элемент E в массиве,
// для которого predicate(E) выполняется.
// Функция возвращает 0 если элемент не найден.
int array_contains(int* array, int* limit, int** position) {
   if(array >= limit){
       return 0;
   }
   *position = 0;
   for(int* cur = array; cur < limit; cur ++){
       if(predicate(*cur)){
           *position = cur;
           break;
       }
   }
   if(*position == 0){
       return 0;
   }
    return 1;
}