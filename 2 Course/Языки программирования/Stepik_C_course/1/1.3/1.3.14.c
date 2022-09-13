void fizzbuzz(int n1){
    if(n1<=0){
        printf("no");
    } else if(n1%3==0){
        printf("fizz");
    } else if(n1%5==0){
        printf("buzz");
    } else if((n1%5 == 0) && (n1%3 == 0)){
        printf("fizzbuzz");
    } 
}