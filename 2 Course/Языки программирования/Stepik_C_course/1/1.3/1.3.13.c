int max3(int a, int b, int c) {

 if((a<=b) && (b<=c)){
     return c;
 } else if((a<=b)&&(b>=c)){
     return b;
 } else if((a>=b) && (a>=c)){
     return a;
 } else {
     return c;
 }

}