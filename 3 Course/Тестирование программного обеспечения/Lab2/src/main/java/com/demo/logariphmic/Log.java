package com.demo.logariphmic;

import com.demo.function.LimitedIterations;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

import java.math.BigDecimal;

public class Log extends LimitedIterations {

  private final Ln ln;
  private final int base;

  public Log(final int base) {
    super();
    this.ln = new Ln();
    this.base = base;
  }

  public Log(final Ln ln, final int base) {
    super();
    this.ln = ln;
    this.base = base;
  }

  @Override
  public BigDecimal calculate(final BigDecimal x, final BigDecimal precision)
          throws ArithmeticException {
    checkValidity(x, precision);

    if (x.compareTo(ZERO) <= 0) {
      throw new ArithmeticException(format("Function value for argument %s doesn't exist", x));
    }

    final BigDecimal result =
            ln.calculate(x, precision)
                    .divide(
                            ln.calculate(new BigDecimal(base), precision),
                            DECIMAL128.getPrecision(),
                            HALF_EVEN);
    return result.setScale(precision.scale(), HALF_EVEN);
  }
}
