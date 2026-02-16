package xyz.schnorxoborx.base.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.numbers.fraction.BigFraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to handle computations of numbers as used by, e.g., 
 * GnuCash (gnucash.org)
 * and  
 * KMyMoney (kmymoney.org).
 * E.g.: the string "2/100" means "0.02"
 * <br>
 * <b>CAUTION</b>: The name of this class is misleading: It is, in fact, <b>not</b> a 
 * fixed-point number (there is just one small piece of code that suggests that it 
 * all once <b>started</b> with a fixed point number), but rather a (inconsistent and 
 * ugly) wrapper for BigDecimal (which is inadequate an internal representation) plus 
 * some extra capabilities, above all:
 * <ul>
 *   <li>Safe parsing of strings:</li>
 *   <ul>
 *     <li>decimal number strings</li>
 *     <li>rational number strings (fractions)</li>
 *   </ul>
 *   <li>Safe approximations of double values (optimal for GnuCash and KMyMoney)</li>
 *   <li>High-precision computations (but not exact!)</li>
 *   <li>Safe string output (primarily for GnuCash and KMyMoney, i.e. as fractions)</li>
 * </ul>
 * And thus, as opposed to computations with genuine fixed point numbers, this class 
 * enables you to make computations with (virtually) <b>arbitrary precision</b>. However, 
 * it cannot make <b>exact</b> computations, which is sub-optimal for a class
 * used for GnuCash and KMyMoney files, as these two programs do compute exactly, 
 * using only rational numbers.
 * <br>
 * But For historical reasons and because it's now used in thousands of instances
 * and in possibly tens of variants of the JGnuCash lib, we had to keep the name -- 
 * for a while, at least.
 * <br>
 * <b>CAUTION</b>: For historical reasons, this class is <b>mutable</b> 
 * (as opposed to what one might expect).
 */
public class FixedPointNumber extends BigDecimalWrapper 
							  implements Cloneable 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FixedPointNumber.class);
    
	// ---------------------------------------------------------------

	private static final long serialVersionUID = -881174269202431057L;
	
	// ----------------------------
	// ::MAGIC
	
	private static final char DIV_SYMB = '/';
	
	private static final char DECIMAL_SEP_POINT = '.';
	private static final char DECIMAL_SEP_COMMA = ',';
	
    private static final int  SCALE_MIN = 5;
    private static final int  SCALE_MAX = 12; // Far more than enough for our needs.
                                              // In theory, we can go much further,
                                              // but GnuCash / KMyMoney cannot handle
                                              // the fractions with ridiculously large
    										  // numerators and denominators that will 
    										  // be generated with too high a number here.

	// ----------------------------
    // CAUTION: Use the following >> always with copy() <<
    // (remember, this class is mutable):
    //
    // ==> not:
    // FixedPointNumber fp1 = FixedPointNumber.ZERO; 
    // FixedPointNumber fp2 = FixedPointNumber.ONE_HALF; 
    // but rather:
    // FixedPointNumber fp1 = FixedPointNumber.ZERO.copy();
    // FixedPointNumber fp2 = FixedPointNumber.ONE_HALF.copy(); 
    
    public static final FixedPointNumber ZERO         = new FixedPointNumber(0);
    public static final FixedPointNumber ONE          = new FixedPointNumber(1);
    public static final FixedPointNumber TWO          = new FixedPointNumber(2);
    public static final FixedPointNumber TEN          = new FixedPointNumber(10);
    public static final FixedPointNumber ONE_HUNDRED  = new FixedPointNumber(100);
    public static final FixedPointNumber ONE_THOUSAND = new FixedPointNumber(1000);
    
    public static final FixedPointNumber ONE_HALF       = new FixedPointNumber("1/2");
    public static final FixedPointNumber ONE_THIRD      = new FixedPointNumber("1/3");
    public static final FixedPointNumber ONE_FOURTH     = new FixedPointNumber("1/4");
    public static final FixedPointNumber ONE_FIFTH      = new FixedPointNumber("1/5");
    public static final FixedPointNumber ONE_TENTH      = new FixedPointNumber("1/10");
    public static final FixedPointNumber ONE_HUNDREDTH  = new FixedPointNumber("1/100");
    public static final FixedPointNumber ONE_THOUSANDTH = new FixedPointNumber("1/1000");
	
	// ---------------------------------------------------------------

	private BigDecimal value;

	// ---------------------------------------------------------------

	/**
	 * same as new FixedPointNumber(0) or FixedPointNumber("0").
	 */
	public FixedPointNumber() {
		value = BigDecimal.ZERO;
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
		value = new BigDecimal(num);
	}

	/**
	 * @param num the new value
	 */
	public FixedPointNumber(final long num) {
		value = new BigDecimal(num);
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
		FixedPointNumber fp = parse(numStr);
		value = fp.getBigDecimal();
	}
	
	// ---------------------------------------------------------------

	/**
	 * Accepts string in rational string-format ("5/100" = 0.5) or 
	 * in decimal format ( "0,5", "0.5" and "123"). 
	 * Also ignores currency-symbols like or &euro; .
	 *
	 * @param numStr the String to parse
	 * @return 
	 */
	public static FixedPointNumber parse(final String numStr)
	{
		if ( numStr == null ) {
			throw new IllegalArgumentException("argument <numStr> is null");
		}
		
		if ( numStr.trim().length() == 0 ) {
			return new FixedPointNumber();
		}
		
		FixedPointNumber result = new FixedPointNumber();
		int divIdx = numStr.indexOf(DIV_SYMB);
		if ( divIdx == -1 ) {
			result.parseDecimal( numStr );
		} else {
			result.parseRational( numStr );
		}

		return result;
	}
	
	// Accepts string in the decimal format ("0,5", "0.5" and "123"). 
	// Also ignores currency-symbols like or &euro; .
	private void parseDecimal(/*final*/ String numStr)
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
		
		if ( value == null ) {
			LOGGER.error("parseDecimal: Could not parse string: '" + numStr + "'" );
			throw new IllegalArgumentException("value is null! Original string: '" + numStr + "'");
		}
	}
	
	// Accepts string in rational-string-format ("5/2" = 2.5).
	private void parseRational(final String numStr)
	{
		BigFraction bf = BigFraction.parse(numStr);
		
		try {
			set(bf);
		} catch (IllegalArgumentException exc ) {
			throw new IllegalArgumentException("value is null! Original string: '" + numStr + "'");
		}
	}

	// ::TODO ::CHECK: Make it public (and rename it)?
	private void set(final BigFraction bf)
	{
		if ( bf == null ) {
			throw new IllegalArgumentException("argument <bf> is null");
		}
		
		if ( bf.getNumerator().longValue() == 0 ) {
			// Is zero
			value = BigDecimal.ZERO;
			return;
		}
		
		if ( bf.getDenominator().longValue() == 1 ||
		     bf.getDenominator().longValue() == -1 ) {
			// Is whole number
			value = bf.bigDecimalValue(SCALE_MIN, RoundingMode.HALF_UP);
			return;
		}
		
		// Not safe:
		// value = bf.bigDecimalValue();
		// Instead:
		// "Creative" method: Increment scale until the string 
		// representation does not have only zeroes after the 
		// decimal point.
		// Inefficient, but clean and safe (as opposed to old implementation).
		int scale = SCALE_MIN;
		while ( scale <= SCALE_MAX ) {
			value = bf.bigDecimalValue(scale, RoundingMode.HALF_UP); // get scale-specific BigDecimal representation of BigFraction
		    String bfStr = toStringCoreStat(value, scale); // get scale-specific string representation of BigDecimal
			String fractStr = bfStr.split("\\.")[1]; // e.t. after the decimal point
			String fractRestStr = fractStr.replaceAll( "0", "" ); // remove all zero digits
			if ( fractRestStr.equals( "" ) ) {
				// there are only zero digits after of the decimal point
				// (in the scale-specific string representation)
				if ( scale == SCALE_MAX ) {
					LOGGER.warn("parseRational: Could not find adequate scale for BigDecimal representation of non-zero BigFraction " + bf);
					break; // redundant
				}
			} else {
				// We found a non-zero digit after of the decimal point
				// (in the scale-specific string representation)
				// ==> We found the smallest scale which we can adequately
				// represent the non-zero fraction with.
				LOGGER.debug("parseRational: Found adequate scale for BigDecimal representation of non-zero BigFraction " + bf);
				break;
			}

			scale++;
		}
		
		if ( value == null ) {
			// ::CHECK: Can this actually happen?
			// (remains from old implementation)
			LOGGER.error("set: Could not set from BigFraction: '" + bf + "'" );
			throw new IllegalArgumentException("value is null! BigFraction: '" + bf + "'");
		}
	}
	
    // ---------------------------------------------------------------
    
    public static FixedPointNumber of(final BigFraction num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}

		// Altern. 1 -- elegant, but not safe:
		// FixedPointNumber result = new FixedPointNumber();
		// result.set(num);
		// Altern. 2 -- not quite as elegant, but safe:
    	return parse("" + num.getNumerator() + DIV_SYMB + num.getDenominator());
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
        if ( epsilon <= 0.0 ) {
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
        // Doesn't work well:
        // return new FixedPointNumber( bf.bigDecimalValue(RoundingMode.HALF_UP) );
        // Better:
        return new FixedPointNumber( bf.toString() );
    }
    
	// ---------------------------------------------------------------
    
//    @Override
//	public BigDecimal setScale(int newScale, RoundingMode roundingMode) {
//    	return value.setScale(newScale, roundingMode);
//    }

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
    public int compareTo(final FixedPointNumber num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
        return value.compareTo(num);
    }

	// ---------------------------
	
	/**
	 * @param other the value to compare to
	 * @return true if and only if this &gt; other
	 */
	public boolean isGreaterThan(final FixedPointNumber other) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		return isGreaterThan(other.getBigDecimal());
	}

	/**
	 * @param other     the value to compare to
	 * @param tolerance
	 * @return as ifGreaterThan, but with given tolerance allowed
	 */
	public boolean isGreaterThan(final FixedPointNumber other, double tolerance) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		return isGreaterThan(other.getBigDecimal(), tolerance);
	}

	/**
	 * @param other the value to compare to
	 * @return true if and only if this &gt; other
	 */
	public boolean isGreaterThan(final BigDecimal other) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		return ( value.compareTo(other) > 0 );
	}

	/**
	 * @param other     the value to compare to
	 * @param tolerance
	 * @return as ifGreaterThan, but with given tolerance allowed
	 */
	public boolean isGreaterThan(final BigDecimal other, double tolerance) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		if ( tolerance <= 0.0 ) {
			throw new IllegalArgumentException("Tolerance must be > 0.0");
		}

		BigDecimal diff = value.subtract(other);
		// System.err.println("diff: " + diff);

		return ( diff.doubleValue() > tolerance );
	}
	
	/**
	 * @param other the value to compare to
	 * @return true if and only if this &gt; other
	 */
	public boolean isGreaterThan(final BigFraction other) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		FixedPointNumber otherFP = FixedPointNumber.of(other);
		return isGreaterThan(otherFP);
	}

	/**
	 * @param other     the value to compare to
	 * @param tolerance
	 * @return as ifGreaterThan, but with given tolerance allowed
	 */
	public boolean isGreaterThan(final BigFraction other, double tolerance) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		if ( tolerance <= 0.0 ) {
			throw new IllegalArgumentException("Tolerance must be > 0.0");
		}

		FixedPointNumber otherFP = FixedPointNumber.of(other);
		return isGreaterThan(otherFP, tolerance);
	}
	
	// ----------------------------

	/**
	 * @param other the value to compare to
	 * @return true if and only if this &lt; other
	 */
	public boolean isLessThan(final FixedPointNumber other) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		return isLessThan(other.getBigDecimal());
	}

	public boolean isLessThan(final FixedPointNumber other, double tolerance) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		return other.isGreaterThan(this, tolerance);
	}

	/**
	 * @param other the value to compare to
	 * @return true if and only if this &lt; other
	 */
	public boolean isLessThan(final BigDecimal other) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		return value.compareTo(other) < 0;
	}

	public boolean isLessThan(final BigDecimal other, double tolerance) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		if ( tolerance <= 0.0 ) {
			throw new IllegalArgumentException("Tolerance must be > 0.0");
		}

		FixedPointNumber temp = new FixedPointNumber(other);
		return temp.isGreaterThan(this, tolerance);
	}
	
	/**
	 * @param other the value to compare to
	 * @return true if and only if this &lt; other
	 */
	public boolean isLessThan(final BigFraction other) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		FixedPointNumber otherFP = FixedPointNumber.of(other);
		return isLessThan(otherFP);
	}

	public boolean isLessThan(final BigFraction other, double tolerance) {
		if ( other == null ) {
			throw new IllegalArgumentException("argument <other> is null");
		}
		
		if ( tolerance <= 0.0 ) {
			throw new IllegalArgumentException("Tolerance must be > 0.0");
		}

		FixedPointNumber otherFP = FixedPointNumber.of(other);
		return isLessThan(otherFP, tolerance);
	}
	
	// ---------------------------------------------------------------
    // CAUTION:
    // The methods in this section do *not* create new objects.
    // Instead, they *change* the current instance. 

	/**
	 * @param num the value to add
	 * @return this (we are mutable)
	 */
	public FixedPointNumber add(final FixedPointNumber num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		return add(num.getBigDecimal());
	}

	/**
	 * @param num the value to add
	 * @return this (we are mutable)
	 */
	@Override
	public FixedPointNumber add(final BigDecimal num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		value = value.add(num);
		return this;
	}

	public FixedPointNumber add(final BigFraction num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		return add( FixedPointNumber.of( num ) );
	}

	/**
	 * @param num the value to add
	 * @return this (we are mutable)
	 */
	public FixedPointNumber add(final int num) {
		return add(new BigDecimal(num));
	}

	/**
	 * @param num the value to add
	 * @return this (we are mutable)
	 */
	public FixedPointNumber add(final long num) {
		return add(new BigDecimal(num));
	}

	/**
	 * @param numStr the value to add
	 * @return this (we are mutable)
	 */
	public FixedPointNumber add(final String numStr) {
		if ( numStr == null ) {
			throw new IllegalArgumentException("argument <numStr> is null");
		}
		
		if ( numStr.trim().length() == 0 ) {
			throw new IllegalArgumentException("argument <numStr> is empty");
		}
		
		return add(new FixedPointNumber(numStr));
	}

	// ----------------------------

	/**
	 * @param num the value to subtract from this value
	 * @return this (we are mutable)
	 */
	public FixedPointNumber subtract(final FixedPointNumber num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		return subtract(num.getBigDecimal());
	}

	/**
	 * @param num the value to subtract from this value
	 * @return this (we are mutable)
	 */
	@Override
	public FixedPointNumber subtract(final BigDecimal num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		value = value.subtract(num);
		return this;
	}

	public FixedPointNumber subtract(final BigFraction num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		return subtract( FixedPointNumber.of( num ) );
	}

	/**
	 * @param num the value to subtract from this value
	 * @return this (we are mutable)
	 */
	public FixedPointNumber subtract(final int num) {
		return subtract(new BigDecimal(num));
	}

	/**
	 * @param num the value to subtract from this value
	 * @return this (we are mutable)
	 */
	public FixedPointNumber subtract(final long num) {
		return subtract(new BigDecimal(num));
	}

	/**
	 * @param numStr the value to subtract from this value
	 * @return this (we are mutable)
	 */
	public FixedPointNumber subtract(final String numStr) {
		if ( numStr == null ) {
			throw new IllegalArgumentException("argument <numStr> is null");
		}
		
		if ( numStr.trim().length() == 0 ) {
			throw new IllegalArgumentException("argument <numStr> is empty");
		}
		
		return subtract(new FixedPointNumber(numStr));
	}

	// ----------------------------

	/**
	 * @param num the value to multiply this value with (this object will contain the
	 *          new value)
	 * @return this (we are mutable)
	 */
	public FixedPointNumber multiply(final FixedPointNumber num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		return multiply(num.getBigDecimal());
	}

	/**
	 * @param num the value to multiply this value with (this object will contain the
	 *          new value)
	 * @return this (we are mutable)
	 */
	@Override
	public FixedPointNumber multiply(final BigDecimal num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		value = value.multiply(num);
		return this;
	}

	public FixedPointNumber multiply(final BigFraction num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		return multiply( FixedPointNumber.of(num) );
	}

	/**
	 * @param num the value to multiply this value with (this object will contain the
	 *          new value)
	 * @return this (we are mutable)
	 */
	public FixedPointNumber multiply(final int num) {
		return multiply(new BigDecimal(num));
	}

	/**
	 * @param num the value to multiply this value with (this object will contain the
	 *          new value)
	 * @return this (we are mutable)
	 */
	public FixedPointNumber multiply(final long num) {
		return multiply(new BigDecimal(num));
	}

	public FixedPointNumber multiply(final String numStr) {
		if ( numStr == null ) {
			throw new IllegalArgumentException("argument <numStr> is null");
		}
		
		if ( numStr.trim().length() == 0 ) {
			throw new IllegalArgumentException("argument <numStr> is empty");
		}
		
		return multiply(new FixedPointNumber(numStr));
	}

	// ----------------------------

	/**
	 * @param num the value to divide by
	 * @return this (we are mutable)
	 */
	public FixedPointNumber divide(final FixedPointNumber num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		int scale = Math.max( value.scale(), num.scale() );
		value = value.divide( num.getBigDecimal(), scale, RoundingMode.HALF_UP );
//		value = value.divide( num.getBigDecimal(), MathContext.DECIMAL128 );
		return this;
	}

	/**
	 * @param num the value to divide by
	 * @return this (we are mutable)
	 */
	public FixedPointNumber divide(final BigDecimal num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		BigDecimal n2 = num;
	
		// make sure we have enough digits 
		// after the comma:
		value = value.setScale(value.scale() + num.precision()); 
	
		// workaround for a bug in BigDecimal
		if ( num.scale() < value.scale() ) {
			n2 = num.setScale(value.scale());
		}
		
		value = value.divide(n2, RoundingMode.HALF_UP);
		return this;
	}

	public FixedPointNumber divide(final BigFraction num) {
		if ( num == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		return divide( FixedPointNumber.of(num) );
	}

	/**
	 * @param num the value to divide by
	 * @return this (we are mutable)
	 */
	public FixedPointNumber divide(final int num) {
		return divide(new BigDecimal(num));
	}

	/**
	 * @param num the value to divide by
	 * @return this (we are mutable)
	 */
	public FixedPointNumber divide(final long num) {
		return divide(new BigDecimal(num));
	}

	public FixedPointNumber divide(final String numStr) {
		if ( numStr == null ) {
			throw new IllegalArgumentException("argument <numStr> is null");
		}
		
		if ( numStr.trim().length() == 0 ) {
			throw new IllegalArgumentException("argument <numStr> is empty");
		}
		
		return divide(new FixedPointNumber(numStr));
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
	 * @return true if this &gt;= 0
	 */
	public boolean isPositive() {
		return ( value.signum() != -1 );
	}

	/**
	 * @return true if this &lt; 0
	 */
	public boolean isNegative() {
		return ! isPositive();
	}

	// ---------------------------------------------------------------

	public static FixedPointNumber min(final FixedPointNumber a, final FixedPointNumber b) {
		if ( a == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		if ( b == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		if ( a.getBigDecimal().compareTo(b.getBigDecimal()) == -1 ) {
			return a;
		} else {
			return b;
		}
	}
	
	public static FixedPointNumber max(final FixedPointNumber a, final FixedPointNumber b) {
		if ( a == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		if ( b == null ) {
			throw new IllegalArgumentException("argument <num> is null");
		}
		
		if ( a.getBigDecimal().compareTo(b.getBigDecimal()) == -1 ) {
			return b;
		} else {
			return a;
		}
	}

	// ---------------------------------------------------------------

	// Small helper: Remove the curency-symbol from the string (if it has one)
	// ::TODO ::CHECK: Get rid of it?
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
		return toRatString().replaceAll(" ", "");
	}
	
	// ---

	// Helper method
	// Cf. https://stackoverflow.com/questions/30893770/how-to-retain-trailing-zeroes-when-converting-bigdecimal-to-string
	private static String toStringCoreStat(final BigDecimal val, /* DecimalFormat df, */ final int nofDigits) {
		if ( val == null ) {
			throw new IllegalAccessError("argument <val> is null");
		}
		
		String pattern = "#0.";
		for ( int i = 0; i < nofDigits; i++ ) {
			pattern += "0";
		}
		DecimalFormat fmt = new DecimalFormat(pattern, new DecimalFormatSymbols(Locale.US));

		return fmt.format(val);
	}
}
