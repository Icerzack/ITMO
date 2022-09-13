int is_square(int num){
    int square = 0;
    while(square<=num){
        if(square*square == num){
            return 1;
        }
        square = square +1;
    }
    return 0;
}