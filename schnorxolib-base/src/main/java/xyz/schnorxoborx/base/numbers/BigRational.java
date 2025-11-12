// -------------------------------------------------------------------
// Based on: https://introcs.cs.princeton.edu/java/92symbolic/BigRational.java.html
// 
// Copyright on orig. code: © 2000–2022, Robert Sedgewick and Kevin Wayne.
// -------------------------------------------------------------------

package xyz.schnorxoborx.base.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BigRational implements Comparable<BigRational> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BigRational.class);
    
	// ---------------------------------------------------------------

	private static final String DIV_SYMB = "/";

    public static final BigRational ZERO = new BigRational(0);
    public static final BigRational ONE  = new BigRational(1);
    public static final BigRational TWO  = new BigRational(2);
    
    private static final int SCALE         = 32;
    private static final int MANTISSA_BITS = 53;
    
    // ---------------------------------------------------------------

    private BigInteger numer;   // numerator
    private BigInteger denom;   // denominator (always a positive integer)

    // ---------------------------------------------------------------

    public BigRational() {
        this(BigInteger.ZERO, BigInteger.ONE);
    }
    
    public BigRational(final BigInteger numerator, final BigInteger denominator) {
        init(numerator, denominator);
    }

    // ---

    public BigRational(final int numerator, final int denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    public BigRational(final int numerator) {
        this(numerator, 1);
    }

    // ---

    public BigRational(long numerator, long denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    public BigRational(long numerator) {
        this(numerator, 1L);
    }

    // ---

    public BigRational(final String numStr) {
        String[] tokens = numStr.split(DIV_SYMB);
        if (tokens.length == 2)
            init(new BigInteger(tokens[0]), new BigInteger(tokens[1]));
        else if (tokens.length == 1)
            init(new BigInteger(tokens[0]), BigInteger.ONE);
        else
            throw new IllegalArgumentException("For input string: '" + numStr + "'");
    }

    // ---------------------------------------------------------------

    private void init(final BigInteger numerator, final BigInteger denominator) {
        // deal with x / 0
        if ( denominator.equals(BigInteger.ZERO) ) {
           throw new ArithmeticException("Denominator is zero");
        }

        numer = numerator;
        denom = denominator;
        
        reduce();
    }

	public void reduce()
	{
		// reduce fraction (if num = 0, will always yield den = 0)
        BigInteger g = numer.gcd(denom);
        numer = numer.divide(g);
        denom = denom.divide(g);

        // to ensure invariant that denominator is positive
        if ( denom.compareTo(BigInteger.ZERO) < 0 ) {
            denom = denom.negate();
            numer = numer.negate();
        }
	}

    // ---------------------------------------------------------------
    
    public void set(final BigRational brat) {
    	this.numer = brat.getNumerator();
    	this.denom = brat.getNumerator();
    }

    // Cf. https://stackoverflow.com/questions/63975340/convert-double-into-bigrational-two-biginteger-for-numerator-denominator
    public static BigRational of(final double num) {
    	int exp = Math.getExponent(num);
    	long man = Math.round(Math.scalb(num, MANTISSA_BITS - exp));
    	long den = Math.round(Math.scalb(1.0, MANTISSA_BITS - exp));
    	
    	return new BigRational(BigInteger.valueOf(man), BigInteger.valueOf(den));
    }
    
    public static BigRational of(final BigDecimal num) {
    	return of(num.doubleValue());
    }
    
    // ---------------------------------------------------------------
    
    public BigInteger getNumerator() {
    	return numer;
    }

    public BigInteger getDenominator() {
    	return denom;
    }

    // ---------------------------------------------------------------

    // return { -1, 0, + 1 } if a < b, a = b, or a > b
    public int compareTo(BigRational b) {
        BigRational a = this;
        return a.numer.multiply(b.denom).compareTo(a.denom.multiply(b.numer));
    }

    // ---------------------------------------------------------------

    // is this BigRational negative, zero, or positive?
    public boolean isZero() { 
    	return ( numer.signum() == 0 );
    }
    
    public boolean isPositive() { 
    	return ( numer.signum() > 0 );
    }
    
    public boolean isNegative() { 
    	return ( numer.signum() <  0 );
    }
    
    // ---------------------------------------------------------------
    // CAUTION:
    // The methods in this section do *not* create new objects.
    // Instead, they *change* the current instance. 

    public BigRational add(final BigRational b) {
    	numer = numer.multiply(b.denom).add(b.numer.multiply(this.denom));
        denom = denom.multiply(b.denom);
        reduce();
        return this;
    }

    public BigRational add(final int b) {
    	BigRational temp = new BigRational(b);
        return add(temp);
    }

    public BigRational add(final BigDecimal b) {
    	BigRational temp = BigRational.of(b);
        return add(temp);
    }
    
    public BigRational add(final double b) {
    	return add(BigDecimal.valueOf(b));
    }

    // ----------------------------

    public BigRational subtract(final BigRational b) {
    	add(b.negate());
        reduce();
        return this;
    }

    public BigRational subtract(final int b) {
    	BigRational temp = new BigRational(b);
        return subtract(temp);
    }

    public BigRational subtract(final BigDecimal b) {
    	BigRational temp = BigRational.of(b);
        return subtract(temp);
    }

    public BigRational subtract(final double b) {
    	return subtract(BigDecimal.valueOf(b));
    }

    // ----------------------------

    public BigRational multiplyBy(final BigRational b) {
    	BigInteger numerator   = numer.multiply(b.numer);
    	BigInteger denominator = denom.multiply(b.denom);
        init(numerator, denominator);
        return this;
    }

    public BigRational multiplyBy(final int b) {
    	BigRational temp = new BigRational(b);
        return multiplyBy(temp);
    }

    public BigRational multiplyBy(final BigDecimal b) {
    	BigRational temp = BigRational.of(b);
        return multiplyBy(temp);
    }

    public BigRational multiplyBy(final double b) {
        return multiplyBy(BigDecimal.valueOf(b));
    }

    // ----------------------------

    public BigRational divideBy(final BigRational b) {
        multiplyBy(b.reciprocal());
        return this;
    }
    
    public BigRational divideBy(final int b) {
    	BigRational temp = new BigRational(b);
        return divideBy(temp);
    }

    public BigRational divideBy(final BigDecimal b) {
    	BigRational temp = BigRational.of(b);
        return divideBy(temp);
    }

    public BigRational divideBy(final double b) {
        return divideBy(BigDecimal.valueOf(b));
    }

    // ---------------------------------------------------------------

    // return -a
    public BigRational negate() {
        numer = numer.negate();
        return this;
    }

    // return 1 / a
    public BigRational reciprocal() {
    	BigInteger temp = numer;
    	numer = denom;
    	denom = temp;
        return this;
    }

    // return |a|
    public BigRational abs() {
        if ( isNegative() ) 
        	return negate();
        else 
        	return this;
    }

    // ---------------------------------------------------------------

    public BigDecimal getBigDecimal() {
        BigDecimal numerator   = new BigDecimal(numer);
        BigDecimal denominator = new BigDecimal(denom);
        BigDecimal quotient    = numerator.divide(denominator, SCALE, RoundingMode.HALF_EVEN);
        return quotient;
    }

    public FixedPointNumber asFixedPointNumber() {
        return new FixedPointNumber(toString());
    }

    // return double reprentation (within given precision)
    public double doubleValue() {
        return getBigDecimal().doubleValue();
    }

    // ---------------------------------------------------------------

    // hashCode consistent with equals() and compareTo()
    public int hashCode() {
        return Objects.hash(numer, denom);
    }

    // is this Rational object equal to y?
    public boolean equals(Object y) {
        if ( y == this ) 
        	return true;
        
        if ( y == null ) 
        	return false;
        
        if ( y.getClass() != this.getClass() ) 
        	return false;
        
        BigRational b = (BigRational) y;
        return ( compareTo(b) == 0 );
    }

    // ---------------------------------------------------------------

    // return string representation of (this)
    @Override
    public String toString() {
        if ( denom.equals(BigInteger.ONE) ) 
        	return numer + "";
        else
        	return numer + "/" + denom;
    }
    
}
