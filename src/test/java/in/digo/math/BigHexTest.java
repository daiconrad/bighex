package in.digo.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigHexTest {
    @Test
    public void shouldParse255() {
        BigDecimal expected = new BigDecimal(255);
        BigDecimal actual = BigHex.toBigDecimal("0xf.fp+4");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldParseOneTenth() {
        BigDecimal expected = new BigDecimal(0.1);
        BigDecimal actual = BigHex.toBigDecimal("0x1.999999999999ap-4");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldParseNegative() {
        BigDecimal expected = new BigDecimal(-2);
        BigDecimal actual = BigHex.toBigDecimal("-0x1p+1");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldIgnoreLeadingPlus() {
        BigDecimal expected = new BigDecimal(16);
        BigDecimal actual = BigHex.toBigDecimal("+0x1p+4");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldAcceptTrailingDot() {
        BigDecimal expected = new BigDecimal(4);
        BigDecimal actual = BigHex.toBigDecimal("0x1.p2");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldDivideForNegativeExponent() {
        BigDecimal expected = BigDecimal.ONE;
        BigDecimal actual = BigHex.toBigDecimal("0x10p-4");
        assertEquals(expected, actual);
    }

    @Test
    public void shouldHandleLargeExponents() {
        BigDecimal expected = new BigDecimal(BigInteger.valueOf(2).pow(4096));
        BigDecimal actual = BigHex.toBigDecimal("0x1p+4096");
        assertEquals(expected, actual);
    }
}
