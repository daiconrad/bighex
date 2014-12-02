package in.digo.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A utility to convert a String containing a hexadecimal
 * floating point constant to BigDecimal.
 *
 * @author David Conrad
 */
public class BigHex {
    private BigHex() {
        throw new AssertionError("no instance for you!");
    }

    private final static BigInteger TWO = BigInteger.valueOf(2);
    private final static BigDecimal MINUS_ONE = new BigDecimal(-1);

    // used for one-off manual testing
    public static void main(String[] args) {
        for (String bighex : args) {
            System.out.println(bighex + ": " + toBigDecimal(bighex));
        }
    }

    /**
     * Converts a String containing a hexadecimal floating point
     * constant into a BigDecimal. The constant consists of an
     * optional sign followed by "0x" (or "0X"), the mantissa,
     * a "p" (or "P", for "power"), and the exponent. Unlike with
     * decimal floating point constants the exponent is required
     * but may be zero. The mantissa is treated as hexadecimal;
     * the exponent is in base 10, and is the power of 2 by which
     * the mantissa will be multiplied.
     *
     * <p>For example {@code -0xf.f+4} would be equal to negative
     * fifteen and fifteen sixteenths, times two to the fourth power,
     * or -255.
     *
     * @param hex a hexadecimal floating point constant
     * @return the value of the constant as a BigDecimal
     * @throws NullPointerException if hex is null
     * @throws NumberFormatException if hex is not a valid
     *          hexadecimal floating point constant
     */
    public static BigDecimal toBigDecimal(String hex) {
        // handle leading sign
        BigDecimal sign = null;
        if (hex.startsWith("-")) {
            hex = hex.substring(1);
            sign = MINUS_ONE;
        } else if (hex.startsWith("+")) {
            hex = hex.substring(1);
        }

        // constant must start with 0x or 0X
        if (!(hex.startsWith("0x") || hex.startsWith("0X"))) {
            throw new NumberFormatException(
                    "not a hexadecimal floating point constant: " + hex);
        }
        hex = hex.substring(2);

        // ... and end in 'p' or 'P' and an exponent
        int p = hex.indexOf("p");
        if (p < 0) p = hex.indexOf("P");
        if (p < 0) {
            throw new NumberFormatException(
                    "not a hexadecimal floating point constant");
        }
        String mantissa = hex.substring(0, p);
        String exponent = hex.substring(p+1);

        // find the hexadecimal point, if any
        int hexadecimalPoint = mantissa.indexOf(".");
        int hexadecimalPlaces = 0;
        if (hexadecimalPoint >= 0) {
            hexadecimalPlaces = mantissa.length() - 1 - hexadecimalPoint;
            mantissa = mantissa.substring(0, hexadecimalPoint) +
                mantissa.substring(hexadecimalPoint + 1);
        }

        // reduce the exponent by 4 for every hexadecimal place
        int binaryExponent = Integer.valueOf(exponent) - (hexadecimalPlaces * 4);
        boolean positive = true;
        if (binaryExponent < 0) {
            binaryExponent = -binaryExponent;
            positive = false;
        }

        BigDecimal base = new BigDecimal(new BigInteger(mantissa, 16));
        BigDecimal factor = new BigDecimal(TWO.pow(binaryExponent));
        BigDecimal value = positive? base.multiply(factor) : base.divide(factor);
        if (sign != null) value = value.multiply(sign);

        return value;
    }
}
