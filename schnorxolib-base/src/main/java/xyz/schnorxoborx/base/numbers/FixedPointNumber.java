package xyz.schnorxoborx.base.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.numbers.fraction.BigFraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of representations of numbers as rational number strings,
 * as used by, e.g., 
 * GnuCash (gnucash.org)
 * and  
 * KMyMoney (kmymoney.org).
 * E.g.: the string "2/100" means "0.02"
 * <br>
 * <b>CAUTION</b>: The name of this class is misleading: It is, in fact, <b>not</b> a 
 * fixed-point number (not even internally; there is just one small piece of code 
 * that suggests that it all once <b>started</b> with a fixed point number), 
 * but rather an alternative implementation for BigRational (in the same package)
 * plus some extra capabilities.
 * And thus, as opposed to computations with genuine fixed point numbers, this class 
 * enables you to make computations with (virtually) <b>arbitrary precision</b>. However, 
 * it cannot make <b>exact</b> computations, which is sub-optimal for a class
 * used for GnuCash and KMyMoney files, as these two programs do compute exactly, 
 * using only rational numbers..
 * <br>
 * But For historical reasons and because it's now used in thousands of instances
 * and in possibly tens of variants of the JGnuCash lib, we had to keep the name -- 
 * for a while, at least.
 * <br>
 * <b>CAUTION</b>: For historical reasons, this implementation is 
 * <b>not immutable</b> (as opposed to what one might expect).
 */
public class FixedPointNumber extends BigDecimalWrapper 
							  implements Cloneable 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FixedPointNumber.class);
    
	// ---------------------------------------------------------------

	private static final long serialVersionUID = -881174269202431057L;
	
	// ----------------------------
	
	private static final char DIV_SYMB = '/';
	
	private static final char DECIMAL_SEP_POINT = '.';
	private static final char DECIMAL_SEP_COMMA = ',';
	
    private static final int  SCALE_MIN = 5;
    private static final int  SCALE_MAX = 12;

	// ---------------------------------------------------------------

	private BigDecimal value;

	// ---------------------------------------------------------------

	/**
	 * same as new FixedPointNumber(0) or FixedPointNumber("0").
	 */
	public FixedPointNumber() {
		value = BigDecimal.valueOf(0);
	}

	/**
	 * @param num the value to initialize to
	 */
	public FixedPointNumber(final BigDecimal num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		value = num;
	}

	/**
	 * @param num the value to initialize to
	 */
	public FixedPointNumber(final BigInteger num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		value = new BigDecimal(num);
	}

	/**
	 * @param num the new value
	 */
	public FixedPointNumber(final int num) {
		value = new BigDecimal("" + num);

	}

	/**
	 * @param num the new value
	 */
	public FixedPointNumber(final long num) {
		value = new BigDecimal("" + num);

	}

	/**
	 * internally converts the double to a String.
	 * @param num the new value 
	 *
	 * @deprecated Try not to use floating-point numbers. This class is for EXACT
	 *             computation or use the static method of(double value, int nofDigits). 
	 */
	@Deprecated
	public FixedPointNumber(final double num) {
		value = new BigDecimal(num);
	}

	/**
	 * Accepts string in rational string-format ("5/100" = 0.5) or 
	 * in decimal format ( "0,5", "0.5" and "123"). 
	 * Also ignores currency-symbols like or &euro; .
	 *
	 * @param numStr the String to parse
	 */
	public FixedPointNumber(final String numStr) {
		if ( numStr == null ) {
			throw new IllegalArgumentException("argument <numStr> is null");
		}
		
		int divIdx = numStr.indexOf(DIV_SYMB);
		
		if ( divIdx == -1 ) {
			parseDecimal( numStr );
		} else {
			parseRational( numStr, divIdx );
		}

		if ( value == null ) {
			throw new IllegalArgumentException("value is null! Original string: '" + numStr + "'");
		}
	}
	
	// ---------------------------------------------------------------

	/*
	 * Accepts string in the decimal format ("0,5", "0.5" and "123"). 
	 * Also ignores currency-symbols like or &euro; .
	 *
	 * @param numStr the String to parse
	 */
	private void parseDecimal(String numStr)
	{
		int cpIdx = numStr.indexOf(DECIMAL_SEP_COMMA);
		if ( cpIdx != -1 ) {
			numStr = numStr.replaceAll("\\.", "").replaceAll("'", "");
			cpIdx = numStr.indexOf(DECIMAL_SEP_COMMA);
		}
		if ( cpIdx == -1 ) {
			cpIdx = numStr.indexOf(DECIMAL_SEP_POINT);
		}

		// int divider = 1;

		if ( cpIdx == -1 ) {
			// Assume that it's an integer
			// ::TODO: why should we allow currency symbols in the
			// first place? If at all, then in a sub-class of this one.
			String rightOfDecPt = removeCurrency(numStr);

			try {
				value = new BigDecimal(rightOfDecPt);
			} catch (NumberFormatException e) {
				LOGGER.error("parseDecimal (1): '" + rightOfDecPt + "' cannot be parsed by Biginteger.");
				LOGGER.error("parseDecimal (1): Original input was '" + numStr + "'");
				throw new NumberFormatException("'" + rightOfDecPt + "' cannot be parsed by Biginteger. " +
												"Original input was '" + numStr + "'");
			}
		} else {
			// It's a genuine decimal number
			String leftOfDecPt  = numStr.substring(0, cpIdx).trim();
			String rightOfDecPt = numStr.substring(cpIdx + 1).trim();

			rightOfDecPt = removeCurrency(rightOfDecPt);

			try {
				value = new BigDecimal(leftOfDecPt + DECIMAL_SEP_POINT + rightOfDecPt);
			} catch ( NumberFormatException exc ) {
				LOGGER.error("parseDecimal (2): '" + leftOfDecPt + DECIMAL_SEP_POINT + rightOfDecPt + "' cannot be parsed by Biginteger.");
				LOGGER.error("parseDecimal (2): Original input was '" + numStr + "'");
				throw new NumberFormatException("'" + leftOfDecPt + DECIMAL_SEP_POINT + rightOfDecPt + "' cannot be parsed by Biginteger. " +
												"Original input was '" + numStr + "'");
			}
		}
	}
	
	/*
	 * Accepts string in rational-string-format ("5/2" = 2.5).
	 *
	 * @param numStr the String to parse
	 */
	private void parseRational(String numStr, int divIdx)
	{
		String numerStr = numStr.substring(0, divIdx).trim();

		// ::TODO: Have the following done by BigFraction
		// entirely, and do not compute, i.e., do not allow
		// numerator and denominator to be BigDecimals/FPN's themselves,
		// with scale and all the shebang.
		
		int addIdx = numerStr.indexOf('+');
		BigDecimal addMe = null;
		if ( addIdx > 1 ) {
			addMe = new BigDecimal(numerStr.substring(0, addIdx).trim());
			numerStr = numerStr.substring(addIdx + 1).trim();
		}

		String denomStr = numStr.substring(divIdx + 1).trim();

		// special handling if the divider is 1000...
		boolean simpleDenom = isSimpleDenominator( denomStr );
		if ( simpleDenom ) {
			int scale = denomStr.length() - 1;
			value = new BigDecimal(numerStr).movePointLeft(scale);
		} else {
			BigDecimal numer = new BigDecimal(numerStr);
			// if (numer.scale() < SCALE_MIN) numer.setScale(SCALE_MIN);
			BigDecimal denom = new BigDecimal(denomStr);
			// if (denom.scale() < SCALE_MIN) denom.setScale(SCALE_MIN);
			int scale = Math.max( Math.max(SCALE_MIN, numer.scale()), denom.scale() );
			if ( denom.compareTo( new BigDecimal(0) ) != 0 ) {
				value = numer.divide(denom, scale, RoundingMode.HALF_UP);
			}
		}

		if ( addMe != null ) {
			add(addMe);
		}
	}

	// Checks whether divider is of form 1000...
	private boolean isSimpleDenominator(final String denom)
	{
		boolean simpleDivider = denom.charAt(0) == '1';
		
		if ( simpleDivider ) {
			for ( int i = 1; i < denom.length(); i++ ) {
				if ( denom.charAt(i) != '0' ) {
					simpleDivider = false;
					break;
				}
			}
		}
		
		return simpleDivider;
	}
	
    // ---------------------------------------------------------------
    
    public static FixedPointNumber of(final BigFraction num) {
    	return new FixedPointNumber("" + num.getNumerator() + DIV_SYMB + num.getDenominator());
    }
    
    // ---------------------------------------------------------------
    
    public static FixedPointNumber from(double val, final int nofDigits) {
        if ( nofDigits <= 0 ) {
            throw new IllegalArgumentException("argument <nofDigits> must be >= 1");
        }
        
        if ( val == 0.0 ) {
            return new FixedPointNumber();
        }
        
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMaximumFractionDigits(nofDigits);
        
        FixedPointNumber result = new FixedPointNumber();
        result.parseDecimal(nf.format(val));
        return result;
    }

    public static FixedPointNumber from(double val, final double epsilon, final int maxIterations) {
        if ( epsilon <= 0 ) {
            throw new IllegalArgumentException("argument <epsilon> must be > 0.0");
        }
        
        if ( maxIterations <= 0 ) {
            throw new IllegalArgumentException("argument <maxIterations> must be >= 1");
        }
        
        if ( val == 0.0 ) {
            return new FixedPointNumber();
        }
        
        BigFraction bf = BigFraction.from(val, epsilon, maxIterations);
        // Not safe:
        // return new FixedPointNumber( bf.bigDecimalValue() );
        // Better:
        return new FixedPointNumber( bf.toString() );
    }
	// ---------------------------------------------------------------

	/**
	 * @return the value as a BigDecimal.
	 */
	@Override
	public BigDecimal getBigDecimal() {
		return value;
	}

	public BigFraction toBigFraction() {
		// Sic, via string rep. and not via getBigDecimal()!
		// Else, some test cases will fail, because the 
		// numer./denom. will not be reduced.
		return BigFraction.parse(toGCshKmmString_nonred());  
	}
	
	// --

	public double doubleValue() { 
		return value.doubleValue(); 
	} 
	
	public float floatValue() { 
		return value.floatValue(); 
	} 
	
	public BigInteger toBigInteger() {
		return value.toBigInteger();
	}

	public int intValue() { 
		return value.intValue(); 
	} 
	
	public long longValue() { 
		return value.longValue(); 
	}
	
	// ---------------------------------------------------------------

	@Override
	public Object clone() {
		FixedPointNumber fp2 = new FixedPointNumber(getBigDecimal());
		return fp2;
	}

	/**
	 * @return a new FixedPointNumber object which is a copy of this one
	 */
	public FixedPointNumber copy() {
		FixedPointNumber fp2 = new FixedPointNumber(getBigDecimal());
		return fp2;
	}

    // ---------------------------------------------------------------

    // return { -1, 0, + 1 } if a < b, a = b, or a > b
    public int compareTo(FixedPointNumber b) {
        return value.compareTo(b);
    }

	// ---------------------------
	
	/**
	 * @param other the value to compare to
	 * @return true if and only if this &gt; other
	 */
	public boolean isGreaterThan(final FixedPointNumber other) {
		return isGreaterThan(other.getBigDecimal());
	}

	/**
	 * @param other     the value to compare to
	 * @param tolerance
	 * @return as ifGreaterThan, but with given tolerance allowed
	 */
	public boolean isGreaterThan(final FixedPointNumber other, double tolerance) {
		return isGreaterThan(other.getBigDecimal(), tolerance);
	}

	/**
	 * @param other the value to compare to
	 * @return true if and only if this &gt; other
	 */
	public boolean isGreaterThan(final BigDecimal other) {
		return ( value.compareTo(other) > 0 );
	}

	/**
	 * @param other     the value to compare to
	 * @param tolerance
	 * @return as ifGreaterThan, but with given tolerance allowed
	 */
	public boolean isGreaterThan(final BigDecimal other, double tolerance) {
		if ( tolerance <= 0.0 )
			throw new IllegalArgumentException("Tolerance must be > 0.0");

		BigDecimal diff = value.subtract(other);
		// System.err.println("diff: " + diff);

		return ( diff.doubleValue() > tolerance );
	}
	
	// ----------------------------

	/**
	 * @param other the value to compare to
	 * @return true if and only if this&lt;other
	 */
	public boolean isLessThan(final FixedPointNumber other) {
		return isLessThan(other.getBigDecimal());
	}

	public boolean isLessThan(final FixedPointNumber other, double tolerance) {
		return other.isGreaterThan(this, tolerance);
	}

	/**
	 * @param other the value to compare to
	 * @return true if and only if this&lt;other
	 */
	public boolean isLessThan(final BigDecimal other) {
		return value.compareTo(other) < 0;
	}

	public boolean isLessThan(final BigDecimal other, double tolerance) {
		if ( tolerance <= 0.0 )
			throw new IllegalArgumentException("Tolerance must be > 0.0");

		FixedPointNumber temp = new FixedPointNumber(other);
		return temp.isGreaterThan(this, tolerance);
	}
	
	// ---------------------------------------------------------------
    // CAUTION:
    // The methods in this section do *not* create new objects.
    // Instead, they *change* the current instance. 

	/**
	 * @param n the value to add
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber add(final FixedPointNumber n) {
		return add(n.getBigDecimal());
	}

	/**
	 * @param n the value to add
	 * @return this (we are mutable) for easy operation-chaining
	 */
	@Override
	public FixedPointNumber add(final BigDecimal n) {
		value = value.add(n);
		return this;
	}

	/**
	 * @param n the value to add
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber add(final int n) {
		return add(new BigDecimal(n));
	}

	/**
	 * @param n the value to add
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber add(final long n) {
		return add(new BigDecimal(n));
	}

	/**
	 * @param n the value to add
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber add(final String n) {
		return add(new FixedPointNumber(n));
	}

	// ----------------------------

	/**
	 * @param n the value to subtract from this value
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber subtract(final FixedPointNumber n) {
		return subtract(n.getBigDecimal());
	}

	/**
	 * @param n the value to subtract from this value
	 * @return this (we are mutable) for easy operation-chaining
	 */
	@Override
	public FixedPointNumber subtract(final BigDecimal n) {
		value = value.subtract(n);
		return this;
	}

	/**
	 * @param n the value to subtract from this value
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber subtract(final int n) {
		return subtract(new BigDecimal(n));
	}

	/**
	 * @param n the value to subtract from this value
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber subtract(final long n) {
		return subtract(new BigDecimal(n));
	}

	/**
	 * @param n the value to subtract from this value
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber subtract(final String n) {
		return subtract(new FixedPointNumber(n));
	}

	// ----------------------------

	/**
	 * @param n the value to multiply this value with (this object will contain the
	 *          new value)
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber multiply(final FixedPointNumber n) {
		return multiply(n.getBigDecimal());
	}

	/**
	 * @param n the value to multiply this value with (this object will contain the
	 *          new value)
	 * @return this (we are mutable) for easy operation-chaining
	 */
	@Override
	public FixedPointNumber multiply(final BigDecimal n) {
		value = value.multiply(n);
		return this;
	}

	/**
	 * @param n the value to multiply this value with (this object will contain the
	 *          new value)
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber multiply(final int n) {
		return multiply(new BigDecimal(n));
	}

	/**
	 * @param n the value to multiply this value with (this object will contain the
	 *          new value)
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber multiply(final long n) {
		return multiply(new BigDecimal(n));
	}

	public FixedPointNumber multiply(final String n) {
		return multiply(new FixedPointNumber(n));
	}

	// ----------------------------

	/**
	 * @param n the value to divide by
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber divide(final FixedPointNumber n) {
		return divide(n.getBigDecimal());
	}

	/**
	 * @param n the value to divide by
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber divide(final BigDecimal n) {
		BigDecimal n2 = n;
	
		// make sure we have enough digits 
		// after the comma:
		value = value.setScale(value.scale() + n.precision()); 
	
		// workaround for a bug in BigDecimal
		if ( n.scale() < value.scale() ) {
			n2 = n.setScale(value.scale());
		}
		
		value = value.divide(n2, RoundingMode.HALF_UP);
		return this;
	}

	/**
	 * @param n the value to divide by
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber divide(final int n) {
		return divide(new BigDecimal(n));
	}

	/**
	 * @param n the value to divide by
	 * @return this (we are mutable) for easy operation-chaining
	 */
	public FixedPointNumber divide(final long n) {
		return divide(new BigDecimal(n));
	}

	public FixedPointNumber divide(final String n) {
		return divide(new FixedPointNumber(n));
	}

	// ---------------------------------------------------------------

	/**
	 * @return a new FixedPointNumber that has the value of this one times -1.
	 */
	@Override
	public FixedPointNumber negate() {
		value = value.negate();
		return this;
	}

	public FixedPointNumber reciprocal() {
		value = BigDecimal.ONE.divide(value, value.scale(), RoundingMode.HALF_UP);
		return this;
	}

	public FixedPointNumber abs() {
        if ( isNegative() ) 
        	value = value.negate();

        return this;
	}

	// ---------------------------------------------------------------

    public boolean isZero() { 
    	return ( value.signum() == 0 );
    }
    
    public boolean isZero(double tolerance) { 
		if ( tolerance <= 0.0 )
			throw new IllegalArgumentException("Tolerance must be > 0.0");

    	return ( Math.abs( value.doubleValue() ) <= tolerance );
    }
    
	/**
	 * @return true if we are &gt;= 0
	 */
	public boolean isPositive() {
		return ( value.signum() != -1 );
	}

	/**
	 * @return true if we are &lt 0
	 */
	public boolean isNegative() {
		return ! isPositive();
	}

	// ---------------------------------------------------------------

	public static FixedPointNumber min(final FixedPointNumber a, final FixedPointNumber b) {
		if ( a.getBigDecimal().compareTo(b.getBigDecimal()) == -1 ) {
			return a;
		} else {
			return b;
		}
	}
	
	public static FixedPointNumber max(final FixedPointNumber a, final FixedPointNumber b) {
		if ( a.getBigDecimal().compareTo(b.getBigDecimal()) == -1 ) {
			return b;
		} else {
			return a;
		}
	}

	// ---------------------------------------------------------------

	/**
	 * @param input the string to remove the curency-symbol from (if it has one)
	 * @return the String without the currency
	 */
	private String removeCurrency(final String input) {
		String rightOfDecPt = input.replace('€', ' ');
		rightOfDecPt = rightOfDecPt.replace('$', ' ');
		rightOfDecPt = rightOfDecPt.replace('£', ' ');
		rightOfDecPt = rightOfDecPt.replace('￥', ' ');
		rightOfDecPt = rightOfDecPt.replaceAll("&euro;", "");
		rightOfDecPt = rightOfDecPt.replaceAll("&dollar;", "");
		rightOfDecPt = rightOfDecPt.replaceAll("&pound;", "");
		rightOfDecPt = rightOfDecPt.replaceAll("&yen;", "");
		rightOfDecPt = rightOfDecPt.trim();
		while ( rightOfDecPt.length() > 0 ) {
			if ( Character.isDigit(rightOfDecPt.charAt(rightOfDecPt.length() - 1)) ) {
				break;
			}
			rightOfDecPt = rightOfDecPt.substring(0, rightOfDecPt.length() - 1);
		}

		return rightOfDecPt;
	}

	// ---------------------------------------------------------------

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return value.hashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if ( o instanceof FixedPointNumber ) {
			FixedPointNumber n = (FixedPointNumber) o;
			return equals(n.getBigDecimal());
		}

		if ( o instanceof BigDecimal ) {
			/*
			 * //vvvvv fix for an issue with BigDecimal.compareTo // "-0.0" compared to
			 * "0.0" is NOT 0 if (this.value.abs().compareTo(MINUSZERO) == 0) this.value =
			 * MINUSZERO;
			 * 
			 */
			BigDecimal otherBigDecimal = (BigDecimal) o;/*
														 * if (otherBigDecimal.abs().compareTo(MINUSZERO) == 0)
														 * otherBigDecimal = MINUSZERO; //^^^^^^^^
														 */
			return (otherBigDecimal).compareTo(value) == 0;
		}

		if ( o instanceof Number ) {
			return ((Number) o).doubleValue() == doubleValue();
		}

		return false;
	}

	public boolean equals(final FixedPointNumber other, double tolerance) {
		
		BigDecimal diff = value.subtract(other.getBigDecimal());
		// System.err.println("diff: " + diff);
		if ( Math.abs( diff.doubleValue() ) <= tolerance ) {
			return true;
		} else {
			return false;
		}
	}

	// ---------------------------------------------------------------

	/**
	 * Format using the default NumberFormat.
	 */
	public String toString() {
		return toString(value.scale()); 
	}
	
	public String toString(final int nofDigits) {
        return toString(NumberFormat.getInstance(), nofDigits);
	}
	
	public String toString(final NumberFormat nf, final int nofDigits) {
        nf.setMaximumFractionDigits(nofDigits);
        return nf.format(super.doubleValue());
	}
	
	// ---

    public String toRatString() {
        return toBigFraction().toString();
    }
    
	// ---

	public String toGnuCashString() {
		return toGCshKmmString_red();
	}
	
	public String toKMyMoneyString() {
		return toGCshKmmString_red();
	}
	
	private String toGCshKmmString_nonred() {
		StringBuffer sb = new StringBuffer();

		if ( value.scale() > SCALE_MAX ) {
			value = value.setScale(SCALE_MAX, RoundingMode.HALF_UP);
		}
		
		// try to have a divider of "100"
		int scaleAdjust = 2 - value.scale();

		sb.append(value.unscaledValue().toString());
		for ( int i = 0; i < scaleAdjust; i++ ) {
			sb.append('0');
		}
		
		sb.append("/1");
		for ( int i = 0; i < value.scale(); i++ ) {
			sb.append('0');
		}
		
		for ( int i = 0; i < scaleAdjust; i++ ) {
			sb.append('0');
		}

		return sb.toString();
	}

	private String toGCshKmmString_red() {
		return toBigFraction().toString();
	}

}
