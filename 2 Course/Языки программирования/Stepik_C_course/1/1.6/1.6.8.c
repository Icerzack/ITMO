int is_prime(int num){
    if(num <= 1){
        return 0;
    }
    for(int i = 2; i <= num; i++){
        if((i!=num) && (num%i==0)){
            return 0;
        }
    }
    return 1;
}