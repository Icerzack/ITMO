package com.demo.function;

import java.math.BigDecimal;

public interface Function {

  BigDecimal calculate(final BigDecimal x, final BigDecimal precision);

}
