bighex
======

Convert a hexadecimal floating point constant from String to BigDecimal.

Since Java 7 the Java language has supported a hexadecimal format for
floating point numbers. For example, 0x1p-3 is equal to 0.125; there is
a one in the mantissa, and a binary exponent of -3, so it is equal to a
one shifted right three places past the binary point, or 3/8.

Similarly, 0xAp-3 is equal to 0xA in the mantissa (0b1010 in binary)
shifted right three places. In a hypothetical binary floating point
notation we could right this as 1.010 and its values is 1.25. The largest
value a double can represent is 0x1.fffffffffffffp1023 (alternatively,
this can be written 0x1fffffffffffffp971). That's 53 bits of 1's in the
mantissa and the maximum exponent possible.

A double can be created either by writing a literal using this notation
in the Java programming language, or by parsing a string in this format
with Double::parseDouble.

However, we can easily write values larger than that in this notation,
and *we can easily work with them in Java* using BigDecimal. But at
least as of Java 9, BigDecimal doesn't know how to parse a number in
this format. You could parse it with Double::parseDouble and then use
that to create your BigDecimal, but then you would be restricted to
the range of values that double supports.

This project provides a parser that can convert a hexadecimal floating
point number that is too large to fit in a double into a BigDecimal.
