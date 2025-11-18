package xyz.schnorxoborx.base.numbers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.commons.numbers.fraction.BigFraction;
import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.ConstTest;

public class TestFixedPointNumberBigRational {
	
	private static final int MAX_ITER = 30;

    // -----------------------------------------------------------------

	public static void main(String[] args) throws Exception {
		junit.textui.TestRunner.run(suite());
    }

	public static junit.framework.Test suite() {
    	return new JUnit4TestAdapter(TestFixedPointNumberBigRational.class);
    }

    @Before
    public void initialize() throws Exception {
		// ::EMPTY
    }

    // -----------------------------------------------------------------

    @Test
    public void test10_1() throws Exception {
    	FixedPointNumber num  = new FixedPointNumber();
    	BigFraction      brat = BigFraction.ZERO;
    	assertEquals(0, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("0");
    	brat = BigFraction.parse("0");
    	assertEquals(0, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("1");
    	brat = BigFraction.parse("1");
    	assertEquals(1, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("1/1");
    	brat = BigFraction.parse("1/1");
    	assertEquals(1, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("100/100");
    	brat = BigFraction.parse("100/100");
    	assertEquals(1, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("100/200");
    	brat = BigFraction.parse("100/200");
    	assertEquals(1, brat.getNumerator().longValue());
    	assertEquals(2, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num   = new FixedPointNumber("200/100");
    	brat = BigFraction.parse("200/100");
    	assertEquals(2, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("1287472/1000000");
    	brat = BigFraction.parse("1287472/1000000");
    	assertEquals(80467L, brat.getNumerator().longValue());
    	assertEquals(62500L, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	// ::TODO
    	// assertEquals(num.toBigFraction(), brat);
    	
    	num  = new FixedPointNumber("355/113");
    	brat = BigFraction.parse("355/113");
    	assertEquals(355, brat.getNumerator().longValue());
    	assertEquals(113, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(Math.PI, num.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(Math.PI, brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	// ::TODO
    	// assertEquals(num.toBigFraction(), brat);
    	
    	// ---

    	num  = new FixedPointNumber("-1"); // valid
    	brat = BigFraction.parse("-1");
    	assertEquals(-1, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("-100/100"); // valid
    	brat = BigFraction.parse("-100/100");
    	assertEquals(-1, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);
    	
    	// ---
    	// CAUTION: in the following two cases, BigFraction 
    	// can parse a negative denominator correctly, but
    	// will not normalize the generated instance 
    	// (one might expect that just the numerator can
    	// be negative, but this is not the case.

    	num  = new FixedPointNumber("100/-100"); // valid
    	brat = BigFraction.parse("100/-100"); // technically valid, but semantically not
    	assertEquals(-1, brat.signum()); // sic
    	assertEquals(1, brat.getNumerator().longValue()); // sic: Not normalized
    	assertEquals(-1, brat.getDenominator().longValue()); // sic: Not normalized
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("-100/-100"); // valid
    	brat = BigFraction.parse("-100/-100");
    	assertEquals(1, brat.signum()); // sic
    	assertEquals(-1, brat.getNumerator().longValue()); // sic: Not normalized
    	assertEquals(-1, brat.getDenominator().longValue()); // sic: Not normalized
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);
    }
    
    @Test
    public void test10_2() throws Exception {
    	FixedPointNumber num  = new FixedPointNumber();
    	BigFraction      brat = BigFraction.ZERO;
    	assertEquals(0, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber(0);
    	brat = BigFraction.ZERO;
    	assertEquals(0, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber(1);
    	brat = BigFraction.of(1);
    	assertEquals(1, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("1/1");
    	brat = BigFraction.of(1, 1);
    	assertEquals(1, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("100/100");
    	brat = BigFraction.of(100, 100);
    	assertEquals(1, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("100/200");
    	brat = BigFraction.of(100, 200);
    	assertEquals(1, brat.getNumerator().longValue());
    	assertEquals(2, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("200/100");
    	brat = BigFraction.of(200, 100);
    	assertEquals(2, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("1287472/1000000");
    	brat = BigFraction.of(1287472, 1000000);
    	assertEquals(80467L, brat.getNumerator().longValue());
    	assertEquals(62500L, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	// ::TODO
    	// assertEquals(new BigRational(num.toGnuCashString()), brat);
    	// assertEquals(num, new FixedPointNumber(brat.toGnuCashString()));
    	// ::TODO
    	// assertEquals(num.toBigFraction(), brat);
    	
    	num  = new FixedPointNumber("355/113");
    	brat = BigFraction.of(355, 113);
    	assertEquals(355, brat.getNumerator().longValue());
    	assertEquals(113, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(Math.PI, num.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(Math.PI, brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	// ::TODO
    	// assertEquals(new BigRational(num.toGnuCashString()), brat);
    	// ::TODO
    	// assertEquals(num.toBigFraction(), brat);
    	
    	// ---

    	num  = new FixedPointNumber(-1); // valid
    	brat = BigFraction.of(-1);
    	assertEquals(-1, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("-100/100"); // valid
    	brat = BigFraction.of(-100, 100);
    	assertEquals(-1, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
    	assertEquals(num.toBigFraction(), brat);

    	// ---
    	// CAUTION: in the following two cases, BigFraction 
    	// can parse a negative denominator correctly, but
    	// will not normalize the generated instance 
    	// (one might expect that just the numerator can
    	// be negative, but this is not the case.

    	num  = new FixedPointNumber("100/-100"); // valid
    	brat = BigFraction.of(100, -100);
    	assertEquals(1, brat.getNumerator().longValue());
    	assertEquals(-1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
    	assertEquals(num.toBigFraction(), brat);

    	num  = new FixedPointNumber("-100/-100"); // valid
    	brat = BigFraction.of(-100, -100);
    	assertEquals(-1, brat.getNumerator().longValue()); // sic: not normalized
    	assertEquals(-1, brat.getDenominator().longValue()); // sic: not normalized
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
    	assertEquals(num.toBigFraction(), brat);
    }
    
    @Test
    public void test10_3() throws Exception {
    	FixedPointNumber num  = new FixedPointNumber("1.02");
    	BigFraction      brat = BigFraction.of(102, 100);
    	assertEquals(51, brat.getNumerator().longValue());
    	assertEquals(50, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
    	assertEquals(num.toGnuCashString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);
    	
    	num  = new FixedPointNumber("0.325");
    	brat = BigFraction.of(325, 1000);
    	assertEquals(13, brat.getNumerator().longValue());
    	assertEquals(40, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
    	assertEquals(num.toGnuCashString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);
    }

    @Test
    public void test07_1() throws Exception {
    	double eps = 0.01;
    	FixedPointNumber br = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	assertEquals(Math.PI, br.doubleValue(), eps);
    	assertNotEquals(Math.PI, br.doubleValue(), eps / 10);
    	
    	eps = 0.001;
    	br = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	assertEquals( Math.PI, br.doubleValue(), eps);
    	assertNotEquals(Math.PI, br.doubleValue(), eps / 100); // <-- CAUTION
    	
    	eps = 1.0e-5;
    	br = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	assertEquals( Math.PI, br.doubleValue(), eps);
    	assertNotEquals(Math.PI, br.doubleValue(), eps / 10);
    	
    	eps = 1.0e-7;
    	br = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	// ::TODO
    	// assertEquals( Math.PI, br.doubleValue(), eps);
    	assertNotEquals(Math.PI, br.doubleValue(), eps / 10);
    	
    	eps = 1.0e-10;
    	br = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	// ::TODO
    	// assertEquals( Math.PI, br.doubleValue(), eps);
    	assertNotEquals(Math.PI, br.doubleValue(), eps / 10);
    	
    	// ---
    	
    	eps = 0.01;
    	br = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	assertEquals(Math.E, br.doubleValue(), eps);
    	assertNotEquals(Math.E, br.doubleValue(), eps / 10);
    	
    	eps = 0.001;
    	br = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	assertEquals(Math.E, br.doubleValue(), eps);
    	assertNotEquals(Math.E, br.doubleValue(), eps / 10);
    	
    	eps = 1.0e-5;
    	br = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	assertEquals(Math.E, br.doubleValue(), eps);
    	assertNotEquals(Math.E, br.doubleValue(), eps / 10);
    	
    	eps = 1.0e-7;
    	br = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	// ::TODO
    	// assertEquals(Math.E, br.doubleValue(), eps);
    	assertNotEquals(Math.E, br.doubleValue(), eps / 10);
    	
    	eps = 1.0e-10;
    	br = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	// ::TODO
    	// assertEquals(Math.E, br.doubleValue(), eps);
    	assertNotEquals(Math.E, br.doubleValue(), eps / 10);
    }
    
}
