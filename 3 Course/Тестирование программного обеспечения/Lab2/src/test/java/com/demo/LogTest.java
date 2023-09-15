package com.demo;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import com.demo.logariphmic.Ln;
import com.demo.logariphmic.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogTest {

  private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.0001");

  @Mock private Ln mockLn;
  @Spy private Ln spyLn;

  @Test
  void shouldCallLnFunction() {
    final Log log = new Log(spyLn, 5);
    log.calculate(new BigDecimal(6), new BigDecimal("0.001"));

    verify(spyLn, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
  }

  @Test
  void shouldCalculateWithLnMock() {
    final BigDecimal arg = new BigDecimal(126);

    when(mockLn.calculate(eq(new BigDecimal(126)), any(BigDecimal.class)))
        .thenReturn(new BigDecimal("4.8362819"));
    when(mockLn.calculate(eq(new BigDecimal(5)), any(BigDecimal.class)))
        .thenReturn(new BigDecimal("1.6094379"));

    final Log log = new Log(mockLn, 5);
    final BigDecimal expected = new BigDecimal("3.004951");
    assertEquals(expected, log.calculate(arg, new BigDecimal("0.000001")));
  }

  @Test
  void shouldNotCalculateForZero() {
    final Log log = new Log(5);
    assertThrows(ArithmeticException.class, () -> log.calculate(ZERO, DEFAULT_PRECISION));
  }

  @Test
  void shouldCalculateForOne() {
    final Log log = new Log(5);
    assertEquals(
        ZERO.setScale(DEFAULT_PRECISION.scale(), HALF_EVEN), log.calculate(ONE, DEFAULT_PRECISION));
  }

  @Test
  void shouldCalculateForPositive() {
    final Log log = new Log(5);
    final BigDecimal expected = new BigDecimal("2.4663");
    assertEquals(expected, log.calculate(new BigDecimal(53), DEFAULT_PRECISION));
  }
}
