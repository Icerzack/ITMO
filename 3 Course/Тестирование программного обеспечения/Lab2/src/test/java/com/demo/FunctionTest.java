package com.demo;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.stream.Stream;

import com.demo.function.Function;
import com.demo.logariphmic.Ln;
import com.demo.logariphmic.Log;
import com.demo.trigonometric.Cos;
import com.demo.trigonometric.Cot;
import com.demo.trigonometric.Sin;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FunctionTest {

  private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.000001");

  @ParameterizedTest
  @MethodSource("functions")
  void shouldNotAcceptNullArgument(final Function function) {
    assertThrows(NullPointerException.class, () -> function.calculate(null, DEFAULT_PRECISION));
  }

  @ParameterizedTest
  @MethodSource("functions")
  void shouldNotAcceptNullPrecision(final Function function) {
    assertThrows(NullPointerException.class, () -> function.calculate(ONE, null));
  }

  private static Stream<Arguments> functions() {
    return Stream.of(
        Arguments.of(new Sin()),
        Arguments.of(new Cos()),
        Arguments.of(new Cot()),
        Arguments.of(new Ln()),
        Arguments.of(new Log(3)),
        Arguments.of(new Log(5)),
        Arguments.of(new Log(10)));
  }
}
