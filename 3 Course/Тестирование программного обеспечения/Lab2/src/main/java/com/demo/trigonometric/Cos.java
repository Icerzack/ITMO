package com.demo.trigonometric;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.demo.function.LimitedIterations;

import java.math.BigDecimal;
import java.math.MathContext;

public class Cos extends LimitedIterations {

    private final Sin sin;

    public Cos() {
        super();
        this.sin = new Sin();
    }

    public Cos(final Sin sin) {
        super();
        this.sin = sin;
    }

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal precision) throws ArithmeticException {
        checkValidity(x, precision);

        final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
        final BigDecimal correctedX = x.remainder(BigDecimalMath.pi(mc).multiply(new BigDecimal(2)));

        if (correctedX.compareTo(ZERO) == 0) {
            return ONE;
        }

        final BigDecimal result = sin.calculate(
                        BigDecimalMath.pi(mc)
                                .divide(new BigDecimal(2), DECIMAL128.getPrecision(), HALF_EVEN)
                                .subtract(correctedX), precision);
        return result.setScale(precision.scale(), HALF_EVEN);
    }
}
