package com.demo.logariphmic;

import com.demo.function.LimitedIterations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Ln extends LimitedIterations {

  public Ln() {
    super();
  }

  @Override
  public BigDecimal calculate(final BigDecimal x, final BigDecimal precision)
          throws ArithmeticException {
    Objects.requireNonNull(x, "Function argument can not be null");
    Objects.requireNonNull(precision, "Precision can not be null");
    if (precision.compareTo(BigDecimal.ZERO) <= 0 || precision.compareTo(BigDecimal.ONE) >= 0) {
      throw new ArithmeticException("Precision must be less than one and more than zero");
    }
    if (x.compareTo(BigDecimal.ZERO) <= 0) {
      throw new ArithmeticException(String.format("Function value for argument %s doesn't exist", x));
    }
    if (x.compareTo(BigDecimal.ONE) == 0) {
      return BigDecimal.ZERO;
    }

    double X = x.doubleValue();

    BigDecimal curValue = BigDecimal.ZERO, prevValue;
    int i = 1;

    if (Math.abs(X - 1) <= 1) {
      do {
        prevValue = curValue;
        curValue = curValue.add(
                BigDecimal.valueOf(-1).pow(i - 1)
                        .multiply(BigDecimal.valueOf(X - 1).pow(i))
                        .divide(BigDecimal.valueOf(i), precision.scale(), RoundingMode.HALF_UP)
        );
        i++;
      } while (BigDecimal.valueOf(0.1).pow(precision.scale()).compareTo((prevValue.subtract(curValue)).abs()) < 0 && i < maxIterations);
      return curValue.add(prevValue).divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN);
    } else {
      do {
        prevValue = curValue;
        curValue = curValue.add(
                BigDecimal.valueOf(-1).pow(i - 1)
                        .divide(BigDecimal.valueOf(X - 1).pow(i), precision.scale(), RoundingMode.HALF_UP)
                        .divide(BigDecimal.valueOf(i), precision.scale(), RoundingMode.HALF_UP)
        );
        i++;
      } while (BigDecimal.valueOf(0.1).pow(precision.scale()).compareTo((prevValue.subtract(curValue)).abs()) < 0 && i < maxIterations);

      curValue = curValue.add(calculate(BigDecimal.valueOf(X - 1), precision));
    }

    return curValue.setScale(precision.scale(), RoundingMode.HALF_EVEN);
  }
}
