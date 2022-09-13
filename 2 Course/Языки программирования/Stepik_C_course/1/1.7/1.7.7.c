void factorize( int n, int* a, int* b )
{
    *a = 1;
    *b = n;
    for(int i = 2; i <= n/2; i++){
        if(n % i == 0){
            *a = i;
            for(int j = *a; j <= n/2; j++){
                if((*a) * j == n){
                    *b = j;
                    break;
                }
            }
            break;
        }
    }
}