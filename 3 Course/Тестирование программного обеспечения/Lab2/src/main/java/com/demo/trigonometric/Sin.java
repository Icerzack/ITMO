package com.demo.trigonometric;

import com.demo.function.LimitedIterations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Sin extends LimitedIterations {

  public Sin() {
    super();
  }

  @Override
  public BigDecimal calculate(final BigDecimal x, final BigDecimal precision) throws ArithmeticException {
    double X = x.doubleValue();
    double PI2 = Math.PI * 2;
    BigDecimal sum = BigDecimal.ZERO;
    BigDecimal prev;

    if (X >= 0) {
      X %= PI2;
    } else {
      while (X < 0) {
        X += PI2;
      }
    }

    int i = 0;
    do {
      prev = sum;
      sum = sum.add(minusOnePow(i).multiply(prod(X, 2 * i + 1)));
      i++;
    } while (BigDecimal.valueOf(0.1).pow(precision.scale()).compareTo(prev.subtract(sum).abs()) < 0);

    return sum.setScale(precision.scale(), RoundingMode.HALF_EVEN);
  }

  private static BigDecimal minusOnePow(int n) {
    return BigDecimal.valueOf(1 - (n % 2) * 2);
  }

  private static BigDecimal prod(double x, int n) {
    BigDecimal accum = BigDecimal.ONE;

    for (int i = 1; i <= n; i++) {
      accum = accum.multiply(BigDecimal.valueOf(x / i));
    }

    return accum;
  }
}
