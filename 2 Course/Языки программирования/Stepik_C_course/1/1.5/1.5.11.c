int discriminant(int a, int b, int c) {
    return b*b - 4*a*c ;
}

int root_count( int disc ) {
   int D = disc ;
   if (D > 0) {
       return 2;
   } else if(D == 0) {
       return 1;
   } else {
       return 0;
   }
    
}

int main() {
    int a = read_int();
    int b = read_int();
    int c = read_int();
    printf("%d",root_count(discriminant(a,b,c)));

    return 0;
}