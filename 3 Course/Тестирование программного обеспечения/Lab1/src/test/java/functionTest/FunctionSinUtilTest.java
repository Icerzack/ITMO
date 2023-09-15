package functionTest;

import static org.junit.Assert.assertEquals;
import static java.lang.Float.NaN;

import org.junit.Test;
import function.FunctionSinUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FunctionSinUtilTest {

    // sin(0)
    @Test
    public void testSinZero() {
        double val = 0;
        double expected = Math.sin(val);
        double actual = FunctionSinUtil.sin(val);
        assertEquals(expected, actual, FunctionSinUtil.PRECISION);
    }

    // для значений угла, выходящих за пределы [-π, π]
    @Test
    public void testSinOutOfRange() {
        double val = Math.PI * 2;
        double actual = FunctionSinUtil.sin(val);
        assertEquals(NaN, actual, FunctionSinUtil.PRECISION);
    }

    // для 1 квадранта
    @ParameterizedTest(name = "x={0}")
    @ValueSource(doubles = {Math.PI / 6, Math.PI / 4, Math.PI / 3, Math.PI / 2})
    public void testSinFirstQuadrant(double val) {
        double expected = Math.sin(val);
        double actual = FunctionSinUtil.sin(val);
        assertEquals(expected, actual, FunctionSinUtil.PRECISION);
    }

    // для 2 квадранта
    @ParameterizedTest(name = "x={0}")
    @ValueSource(doubles = {2*Math.PI / 3, 3* Math.PI / 4, 5*Math.PI / 6})
    public void testSinSecondQuadrant(double val) {
        double expected = Math.sin(val);
        double actual = FunctionSinUtil.sin(val);
        assertEquals(expected, actual, FunctionSinUtil.PRECISION);
    }

    // для 3 квадранта
    @ParameterizedTest(name = "x={0}")
    @ValueSource(doubles = {-Math.PI / 6, -Math.PI / 4, -Math.PI / 3, -Math.PI / 2})
    public void testSinThirdQuadrant(double val) {
        double expected = Math.sin(val);
        double actual = FunctionSinUtil.sin(val);
        assertEquals(expected, actual, FunctionSinUtil.PRECISION);
    }

    // для 4 квадранта
    @ParameterizedTest(name = "x={0}")
    @ValueSource(doubles = {-2*Math.PI / 3, -3* Math.PI / 4, -5*Math.PI / 6})
    public void testSinFourthQuadrant(double val) {
        double expected = Math.sin(val);
        double actual = FunctionSinUtil.sin(val);
        assertEquals(expected, actual, FunctionSinUtil.PRECISION);
    }

    // для граничных значений периода
    @ParameterizedTest(name = "x={0}")
    @ValueSource(doubles = {-Math.PI, Math.PI})
    public void testSinBorders(double val) {
        double expected = Math.sin(val);
        double actual = FunctionSinUtil.sin(val);
        assertEquals(expected, actual, FunctionSinUtil.PRECISION);
    }

}
