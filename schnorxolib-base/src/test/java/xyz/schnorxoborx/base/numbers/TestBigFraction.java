package xyz.schnorxoborx.base.numbers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.commons.numbers.fraction.BigFraction;
import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

// I know, I know: 
// Why should we test BigFraction here when it comes from the well-tested
// and reputable Apache Commons lib collection?
// Because we want to document the symmetry -- the really interesting test
// cases are the symmetric ones for FixedPointNumber.
public class TestBigFraction {
	
	private static final int MAX_ITER = 30;

    // -----------------------------------------------------------------

	public static void main(String[] args) throws Exception {
		junit.textui.TestRunner.run(suite());
    }

	@SuppressWarnings("exports")
	public static junit.framework.Test suite() {
    	return new JUnit4TestAdapter(TestBigFraction.class);
    }

    @Before
    public void initialize() throws Exception {
		// ::EMPTY
    }

    // -----------------------------------------------------------------

    @Test
    public void test07_1() throws Exception {
    	double eps = 0.01;
    	BigFraction br = BigFraction.from(Math.PI, eps, MAX_ITER);
    	assertEquals(22, br.getNumerator().longValue());
    	assertEquals(7,  br.getDenominator().longValue());
    	assertEquals(Math.PI, br.doubleValue(), eps);
    	assertNotEquals(Math.PI, br.doubleValue(), eps / 10);
    	
    	eps = 0.001;
    	br = BigFraction.from(Math.PI, eps, MAX_ITER);
    	assertEquals(333, br.getNumerator().longValue());
    	assertEquals(106,  br.getDenominator().longValue());
    	assertEquals(Math.PI, br.doubleValue(), eps);
    	assertNotEquals(Math.PI, br.doubleValue(), eps / 100); // <-- CAUTION
    	
    	eps = 1.0e-5;
    	br = BigFraction.from(Math.PI, eps, MAX_ITER);
    	assertEquals(355, br.getNumerator().longValue());
    	assertEquals(113, br.getDenominator().longValue());
    	assertEquals(Math.PI, br.doubleValue(), eps);
    	assertNotEquals(Math.PI, br.doubleValue(), eps / 100); // <-- CAUTION
    	
    	eps = 1.0e-7;
    	br = BigFraction.from(Math.PI, eps, MAX_ITER);
    	assertEquals(103993, br.getNumerator().longValue());
    	assertEquals(33102,  br.getDenominator().longValue());
    	// ::TODO
    	assertEquals(Math.PI, br.doubleValue(), eps);
    	assertNotEquals(Math.PI, br.doubleValue(), eps / 1000); // <-- CAUTION
    	
    	eps = 1.0e-10;
    	br = BigFraction.from(Math.PI, eps, MAX_ITER);
    	assertEquals(312689, br.getNumerator().longValue());
    	assertEquals(99532,  br.getDenominator().longValue());
    	assertEquals(Math.PI, br.doubleValue(), eps);
    	assertNotEquals(Math.PI, br.doubleValue(), eps / 10);
    	
    	// ---
    	
    	eps = 0.01;
    	br = BigFraction.from(Math.E, eps, MAX_ITER);
    	assertEquals(19, br.getNumerator().longValue());
    	assertEquals(7,  br.getDenominator().longValue());
    	assertEquals(Math.E, br.doubleValue(), eps);
    	assertNotEquals(Math.E, br.doubleValue(), eps / 10);
    	
    	eps = 0.001;
    	br = BigFraction.from(Math.E, eps, MAX_ITER);
    	assertEquals(87, br.getNumerator().longValue());
    	assertEquals(32,  br.getDenominator().longValue());
    	assertEquals(Math.E, br.doubleValue(), eps);
    	assertNotEquals(Math.E, br.doubleValue(), eps / 10);
    	
    	eps = 1.0e-5;
    	br = BigFraction.from(Math.E, eps, MAX_ITER);
    	assertEquals(1264, br.getNumerator().longValue());
    	assertEquals(465,  br.getDenominator().longValue());
    	assertEquals(Math.E, br.doubleValue(), eps);
    	assertNotEquals(Math.E, br.doubleValue(), eps / 10);
    	
    	eps = 1.0e-7;
    	br = BigFraction.from(Math.E, eps, MAX_ITER);
    	assertEquals(23225, br.getNumerator().longValue());
    	assertEquals(8544,  br.getDenominator().longValue());
    	assertEquals(Math.E, br.doubleValue(), eps);
    	assertNotEquals(Math.E, br.doubleValue(), eps / 100); // <-- CAUTION: 10 not enough in this particular case
    	
    	eps = 1.0e-10;
    	br = BigFraction.from(Math.E, eps, MAX_ITER);
    	assertEquals(517656, br.getNumerator().longValue());
    	assertEquals(190435,  br.getDenominator().longValue());
    	assertEquals(Math.E, br.doubleValue(), eps);
    	assertNotEquals(Math.E, br.doubleValue(), eps / 10);
    }
    
}
