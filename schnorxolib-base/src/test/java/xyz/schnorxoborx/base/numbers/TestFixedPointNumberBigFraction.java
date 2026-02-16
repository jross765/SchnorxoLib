package xyz.schnorxoborx.base.numbers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.numbers.fraction.BigFraction;
import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.ConstTest;

public class TestFixedPointNumberBigFraction {
	
	private static final int MAX_ITER = 30;

    // -----------------------------------------------------------------

	public static void main(String[] args) throws Exception {
		junit.textui.TestRunner.run(suite());
    }

	@SuppressWarnings("exports")
	public static junit.framework.Test suite() {
    	return new JUnit4TestAdapter(TestFixedPointNumberBigFraction.class);
    }

    @Before
    public void initialize() throws Exception {
		// ::EMPTY
    }

    // -----------------------------------------------------------------

    @Test
    public void test10_1() throws Exception {
    	FixedPointNumber num  = FixedPointNumber.ZERO.copy();  // important: copy()
    	BigFraction      brat = BigFraction.ZERO;
    	assertEquals(0, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(0, num.getBigDecimal().compareTo( brat.bigDecimalValue() ));
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
    	FixedPointNumber num  = FixedPointNumber.ZERO.copy(); // important: copy()
    	BigFraction      brat = BigFraction.ZERO;
    	assertEquals(0, brat.getNumerator().longValue());
    	assertEquals(1, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(num.toString(), brat.toString());
    	assertEquals(num.toBigFraction(), brat);

    	num  = FixedPointNumber.ZERO.copy();
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
    	// assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
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
    	// assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
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
    	assertEquals(num.toRatString(), brat.toString());
    	assertEquals(num.toGnuCashString(), brat.toString().replaceAll(" ",""));
    	assertEquals(num.toBigFraction(), brat);
    	
    	num  = new FixedPointNumber("0.325");
    	brat = BigFraction.of(325, 1000);
    	assertEquals(13, brat.getNumerator().longValue());
    	assertEquals(40, brat.getDenominator().longValue());
    	assertEquals(num.doubleValue(), brat.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	// assertEquals(num.toString(), brat.toString());
    	assertEquals(BigFraction.parse(num.toGnuCashString()), brat);
    	assertEquals(num.toRatString(), brat.toString());
    	assertEquals(num.toGnuCashString(), brat.toString().replaceAll(" ",""));
    	assertEquals(num.toBigFraction(), brat);
    }

    @Test
    public void test07_1() throws Exception {
    	double eps = 0.01;
    	FixedPointNumber fp = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	fp.setScale(2, RoundingMode.HALF_UP);
    	assertEquals(Math.PI, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.PI)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.PI))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	eps = 0.001;
    	fp = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	fp.setScale(3, RoundingMode.HALF_UP);
    	assertEquals( Math.PI, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.PI)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.PI))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	eps = 1.0e-5;
    	fp = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	fp.setScale(5, RoundingMode.HALF_UP);
    	assertEquals( Math.PI, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.PI)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.PI))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	eps = 1.0e-7;
    	fp = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	fp.setScale(7, RoundingMode.HALF_UP);
    	// assertEquals( Math.PI, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.PI)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.PI))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	eps = 1.0e-10;
    	fp = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	// assertEquals( Math.PI, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.PI)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.PI))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	eps = 1.0e-15;
    	fp = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	// assertEquals( Math.PI, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.PI)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.PI))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	// ---
    	
    	eps = 0.01;
    	fp = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	assertEquals(Math.E, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.E)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.E))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	eps = 0.001;
    	fp = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	assertEquals(Math.E, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.E)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.E))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	eps = 1.0e-5;
    	fp = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	assertEquals(Math.E, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.E)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.E))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	eps = 1.0e-7;
    	fp = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	// assertEquals(Math.E, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.E)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.E))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	eps = 1.0e-10;
    	fp = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	// assertEquals(Math.E, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.E)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.E))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	eps = 1.0e-15;
    	fp = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	// assertEquals(Math.E, fp.doubleValue(), eps);
    	assertEquals(fp.getBigDecimal().doubleValue(), (new BigDecimal(Math.E)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( (new BigDecimal(Math.E))
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    }
    
}
