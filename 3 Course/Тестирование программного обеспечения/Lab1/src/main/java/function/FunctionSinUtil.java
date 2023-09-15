package function;

import static java.lang.Float.NaN;

public final class FunctionSinUtil {

    public final static double PRECISION = 0.01;

    private static double sinTailorStep(double val, int k) throws StackOverflowError {
        int sign = (k % 2 == 0) ? 1 : -1;

        return sign * Math.pow(val, 2 * k + 1) / factorial(2 * k + 1);
    }

    public static double sin(double val) {
        if (val > Math.PI || val < -Math.PI) {
            return NaN;
        }
        double result = 0;
        double prev = 0;
        double current = 10;
        int n = 0;
        while (Math.abs(prev - current) >= PRECISION) {
            prev = current;
            current = sinTailorStep(val, n);
            result += current;
            n++;
        }
        return result;
    }

    private static long factorial(int val) {
        if (val <= 1)
            return 1;
        return val * (factorial(val - 1));
    }
}
