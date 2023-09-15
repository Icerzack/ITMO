package com.demo.function;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class LimitedIterations implements Function {

  private static final int DEFAULT_MAX_ITERATIONS = 1000;

  protected final int maxIterations;

  protected LimitedIterations() {
    this.maxIterations = DEFAULT_MAX_ITERATIONS;
  }

  protected void checkValidity(final BigDecimal x, final BigDecimal precision) {
    Objects.requireNonNull(x, "Function argument can not be null");
    Objects.requireNonNull(precision, "Precision can not be null");
    if (precision.compareTo(ZERO) <= 0 || precision.compareTo(ONE) >= 0) {
      throw new ArithmeticException("Precision must be less than one and more than zero");
    }
  }
}
