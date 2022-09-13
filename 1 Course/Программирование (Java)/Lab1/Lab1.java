import java.util.*;
import java.lang.*;
public class Main{
     public static void main(String []args){
        int d[] = new int[10];
        int num = 20;
        for(int i = 0; i < 10; i ++){
            d[i] = num;
            num-=2;
        }
        double x[] = new double[16];
        double min = -9.0;
        double max = 12.0;
        for(int i = 0; i < 16; i ++){
            double ranNum = min + (double) (Math.random() * max);
            x[i] = ranNum;
        }
        double d1[][] = new double[10][16];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 16; j++){
                if(d[i]==18){
                    d1[i][j] = Math.asin(Math.cos(x[j]));
                }
                else if(d[i] == 2 || d[i] == 8 || d[i] == 10 || d[i] == 14 || d[i] == 16){
                    d1[i][j] = Math.cbrt(Math.cbrt(Math.cos(x[j])));
                }
                else{
                    d1[i][j] = Math.pow(0.5 / (Math.sin(Math.sin(2*x[j]))+1), 3);
                }
            }
        }
        for (int i = 0; i <10; i++) {    
            for(int j = 0; j < 16; j ++){
                System.out.print(String.format("%.4f", d1[i][j])+" ");
            }
            System.out.println();
        }
     }
}
