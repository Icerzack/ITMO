package com.demo.function;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.HALF_UP;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.demo.logariphmic.Ln;
import com.demo.logariphmic.Log;
import com.demo.trigonometric.Cot;
import com.demo.trigonometric.Sin;

import java.math.BigDecimal;
import java.math.MathContext;

public class FunctionsSystem implements Function {

  private final Sin sin;
  private final Cot cot;
  private final Ln ln;
  private final Log log3;
  private final Log log5;
  private final Log log10;

  public FunctionsSystem() {
    this.sin = new Sin();
    this.cot = new Cot();
    this.ln = new Ln();
    this.log3 = new Log(3);
    this.log5 = new Log(5);
    this.log10 = new Log(10);
  }

  public BigDecimal calculate(final BigDecimal x, final BigDecimal precision) {
    final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
    final BigDecimal correctedX = x.remainder(BigDecimalMath.pi(mc).multiply(new BigDecimal(2)));
    if (x.compareTo(ZERO) <= 0) {
      return cot.calculate(correctedX, precision).setScale(precision.scale(), HALF_EVEN);
    }
    if (ln.calculate(correctedX, precision).equals(ZERO)) return null;
    return log5.calculate(correctedX, precision)
            .divide(log10.calculate(correctedX, precision), HALF_UP)
            .subtract(log3.calculate(correctedX, precision))
            .subtract(ln.calculate(correctedX, precision))
            .pow(2)
            .add(
                    log5.calculate(correctedX, precision)
                            .add(log5.calculate(correctedX, precision))
                            .pow(2)
                            .pow(2)
            )
            .setScale(precision.scale(), HALF_EVEN);
  }
}
