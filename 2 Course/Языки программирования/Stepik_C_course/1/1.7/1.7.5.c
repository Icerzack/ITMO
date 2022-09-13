void normalize( int* n1 ) {
   while(*n1 % 2 == 0){
       if(*n1 == 0){
           break;
       }
       *n1 = *n1 / 2;
   }
}