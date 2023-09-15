package com.demo;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import ch.obermuhlner.math.big.BigDecimalMath;
import java.math.BigDecimal;
import java.math.MathContext;

import com.demo.trigonometric.Cos;
import com.demo.trigonometric.Cot;
import com.demo.trigonometric.Sin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CotTest {

  private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.0001");

  @Mock private Sin mockSin;
  @Mock private Cos mockCos;

  @Test
  void shouldCallSinAndCosFunctions() {
    final Sin spySin = spy(new Sin());
    final Cos cos = new Cos(spySin);
    final Cos spyCos = spy(cos);
    final Cot cot = new Cot(spySin, spyCos);

    cot.calculate(ONE, DEFAULT_PRECISION);

    verify(spySin, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    verify(spyCos, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
  }


  @Test
  void shouldCalculateWithMockSinAndMockCos() {
    final BigDecimal arg = new BigDecimal(5);

    when(mockSin.calculate(eq(arg), any(BigDecimal.class)))
        .thenReturn(new BigDecimal("-0.95892427"));
    when(mockCos.calculate(eq(arg), any(BigDecimal.class)))
        .thenReturn(new BigDecimal("0.28366218"));

    final Cot cot = new Cot(mockSin, mockCos);
    final BigDecimal expectedResult = new BigDecimal("-0.2958");
    assertEquals(expectedResult, cot.calculate(arg, DEFAULT_PRECISION));
  }

  @Test
  void shouldCalculateWithMockSin() {
    final BigDecimal arg = new BigDecimal(5);

    when(mockSin.calculate(eq(arg), any(BigDecimal.class)))
        .thenReturn(new BigDecimal("-0.95892427"));

    final Cot cot = new Cot(mockSin, new Cos());
    final BigDecimal expectedResult = new BigDecimal("-0.2959");
    assertEquals(expectedResult, cot.calculate(arg, DEFAULT_PRECISION));
  }

  @Test
  void shouldCalculateWithMockCos() {
    final BigDecimal arg = new BigDecimal(5);

    when(mockCos.calculate(eq(arg), any(BigDecimal.class)))
        .thenReturn(new BigDecimal("0.28366218"));

    final Cot cot = new Cot(new Sin(), mockCos);
    final BigDecimal expectedResult = new BigDecimal("-0.2958");
    assertEquals(expectedResult, cot.calculate(arg, DEFAULT_PRECISION));
  }

  @Test
  void shouldNotCalculateForZero() {
    final Cot cot = new Cot();
    final BigDecimal arg = BigDecimal.ZERO;

    Throwable exception = assertThrows(ArithmeticException.class,
            () -> cot.calculate(arg, DEFAULT_PRECISION));

    assertEquals("Function value for argument 0 doesn't exist", exception.getMessage());
  }



  @Test
  void shouldCalculateForOne() {
    final Cot cot = new Cot();
    final BigDecimal expected = new BigDecimal("0.6421");
    assertEquals(expected, cot.calculate(ONE, DEFAULT_PRECISION));
  }

  @Test
  void shouldCalculateForPeriodic() {
    final Cot cot = new Cot();
    final BigDecimal expected = new BigDecimal("-0.5235");
    assertEquals(expected, cot.calculate(new BigDecimal(134), DEFAULT_PRECISION));
  }
}
