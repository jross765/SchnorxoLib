package xyz.schnorxoborx.base.numbers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.ConstTest;

public class TestFixedPointNumber {

    // -----------------------------------------------------------------

	public static void main(String[] args) throws Exception {
		junit.textui.TestRunner.run(suite());
    }

    @SuppressWarnings("exports")
	public static junit.framework.Test suite() {
    	return new JUnit4TestAdapter(TestFixedPointNumber.class);
    }

    @Before
    public void initialize() throws Exception {
		// ::EMPTY
    }

    // -----------------------------------------------------------------

    @Test
    public void test01_1() throws Exception {
    	FixedPointNumber num = new FixedPointNumber();
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);

    	num = new FixedPointNumber("0");
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(0, num.intValue());

    	num = new FixedPointNumber("1");
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber("1/1");
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber("100/100");
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber("100/200");
    	assertEquals(0.5, num.doubleValue(), ConstTest.DIFF_TOLERANCE);

    	num = new FixedPointNumber("200/100");
    	assertEquals(2.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(2, num.intValue());

    	num = new FixedPointNumber("1287472/1000000");
    	assertEquals(1.287472, num.doubleValue(), 0.0000005);
    	
    	num = new FixedPointNumber("355/113");
    	assertEquals(Math.PI, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	
    	// ---

    	num = new FixedPointNumber("-1"); // valid
    	assertEquals(-1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(-1, num.intValue());

    	num = new FixedPointNumber("-100/100"); // valid
    	assertEquals(-1.0, num.doubleValue(), 0.0000005);
    	assertEquals(-1, num.intValue());

    	num = new FixedPointNumber("100/-100"); // valid
    	assertEquals(-1.0, num.doubleValue(), 0.0000005);
    	assertEquals(-1, num.intValue());

    	num = new FixedPointNumber("-100/-100"); // valid
    	assertEquals(1.0, num.doubleValue(), 0.0000005);
    	assertEquals(1, num.intValue());
    }

    @Test
    public void test01_2() throws Exception {
    	FixedPointNumber num = new FixedPointNumber(0);
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(0, num.intValue());

    	num = new FixedPointNumber(1);
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber(18);
    	assertEquals(18.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(18, num.intValue());

    	num = new FixedPointNumber(-18);
    	assertEquals(-18.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(-18, num.intValue());

    	num = new FixedPointNumber(48238112);
    	assertEquals(48238112.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(48238112, num.intValue());
    }

    @Test
    public void test01_3() throws Exception {
    	FixedPointNumber num = new FixedPointNumber(BigDecimal.valueOf(0));
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(0, num.intValue());

    	num = new FixedPointNumber(BigDecimal.valueOf(1));
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber(BigDecimal.valueOf(18));
    	assertEquals(18.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(18, num.intValue());

    	num = new FixedPointNumber(BigDecimal.valueOf(-18));
    	assertEquals(-18.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(-18, num.intValue());

    	num = new FixedPointNumber(BigDecimal.valueOf(48238112));
    	assertEquals(48238112.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(48238112, num.intValue());
    }

    @Test
    public void test02_1() throws Exception {
    	FixedPointNumber num = new FixedPointNumber();
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(0, num.intValue());

    	num.add("1");
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(1, num.intValue());

    	num.add(new FixedPointNumber("1"));
    	assertEquals(2.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(2, num.intValue());

    	num.add(-12);
    	assertEquals(-10.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(-10, num.intValue());

    	num.add(BigDecimal.valueOf(23.127));
    	assertEquals(13.127, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	
    	// ---

    	num.subtract("13");
    	assertEquals(0.127, num.doubleValue(), ConstTest.DIFF_TOLERANCE);

    	num.subtract(new FixedPointNumber("127/1000"));
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(0, num.intValue());

    	num.subtract(-12);
    	assertEquals(12.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	assertEquals(12, num.intValue());

    	num.subtract(BigDecimal.valueOf(12.1));
    	assertEquals(-0.1, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    }

    @Test
    public void test02_2() throws Exception {
    	FixedPointNumber num = new FixedPointNumber(12.1);
    	assertEquals(12.1, num.doubleValue(), ConstTest.DIFF_TOLERANCE);

    	num.multiply(2);
    	assertEquals(24.2, num.doubleValue(), ConstTest.DIFF_TOLERANCE);

    	num.multiply(BigDecimal.valueOf(3.2));
    	assertEquals(77.44, num.doubleValue(), ConstTest.DIFF_TOLERANCE);

    	num.multiply(new FixedPointNumber("3/2"));
    	assertEquals(116.16, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	
    	num.multiply(new FixedPointNumber("2/3"));
    	assertEquals(77.44, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	
    	num.divide(BigDecimal.valueOf(4));
    	assertEquals(19.36, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    	
    	num.divide(BigDecimal.TEN);
    	assertEquals(1.936, num.doubleValue(), ConstTest.DIFF_TOLERANCE);
    }

    // Compare with an alternative implementation
    @Test
    public void test03_1() throws Exception {
    	FixedPointNumber fp = new FixedPointNumber("3/2");
    	BigRational br = new BigRational(3, 2);
    	
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);
    	
    	// ---

    	fp.add("77/82");
    	br = br.plus(new BigRational(77, 82));
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);

    	fp.add("783/11");
    	br = br.plus(new BigRational(783, 11));
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);
    	
    	// ---

    	fp.subtract("812/749");
    	br = br.minus(new BigRational(812, 749));
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);

    	fp.subtract("387/21");
    	br = br.minus(new BigRational(387, 21));
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);
    	
    	// ---

    	fp.multiply("12/8");
    	br = br.times(new BigRational(12, 8));
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);

    	fp.multiply("275/18");
    	br = br.times(new BigRational(275, 18));
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);

    	fp.multiply("12/8");
    	br = br.times(new BigRational(12, 8));
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);

    	// ---

    	fp.divide("67/33");
    	br = br.divides(new BigRational(67, 33));
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);

    	fp.divide("59/24");
    	br = br.divides(new BigRational(59, 24));
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);

    	// ---

    	fp.negate();
    	br = br.negate();
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);

    	fp = fp.reciprocal();
    	br = br.reciprocal();
    	assertEquals(fp.getBigDecimal().doubleValue(), br.getBigDecimal().doubleValue(), ConstTest.DIFF_TOLERANCE);
    }

//    @Test
//    public void test03_2() throws Exception {
//    	FixedPointNumber fp = new FixedPointNumber("3/2");
//    	BigRational br = new BigRational(3, 2);
//
//      // No, does not work like that:
//    	assertEquals(fp.toGnuCashString(), br.toString());
//    }
    
    @Test
    public void test04_1() throws Exception {
    	FixedPointNumber fp1 = new FixedPointNumber("2123");
    	FixedPointNumber fp2 = new FixedPointNumber("2123.00");
    	assertEquals( fp1, fp2 );
    	assertEquals( false, fp1.isLessThan( fp2 ) );
    	assertEquals( false, fp1.isGreaterThan( fp2 ) );
    	
    	fp1 = new FixedPointNumber("2123");
    	fp2 = new FixedPointNumber("2125");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2 ) );
    	assertEquals( false, fp1.isGreaterThan( fp2 ) );
    	assertEquals( false, fp2.isLessThan( fp1 ) );
    	assertEquals( true, fp2.isGreaterThan( fp1 ) );
    	
    	fp1 = new FixedPointNumber("2123");
    	fp2 = new FixedPointNumber("2123.01");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2 ) );
    	assertEquals( false, fp1.isGreaterThan( fp2 ) );
    	assertEquals( false, fp2.isLessThan( fp1 ) );
    	assertEquals( true, fp2.isGreaterThan( fp1 ) );
    	
    	fp1 = new FixedPointNumber("-2123");
    	fp2 = new FixedPointNumber("-2123.01");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( false, fp1.isLessThan( fp2 ) );
    	assertEquals( true, fp1.isGreaterThan( fp2 ) );
    	assertEquals( true, fp2.isLessThan( fp1 ) );
    	assertEquals( false, fp2.isGreaterThan( fp1 ) );
    	
    	fp1 = new FixedPointNumber("-2123.00");
    	fp2 = new FixedPointNumber("-2124.00");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( false, fp1.isLessThan( fp2 ) );
    	assertEquals( true, fp1.isGreaterThan( fp2 ) );
    	assertEquals( true, fp2.isLessThan( fp1 ) );
    	assertEquals( false, fp2.isGreaterThan( fp1 ) );
    }
    
    @Test
    public void test04_2() throws Exception {
    	FixedPointNumber fp1 = new FixedPointNumber("2123");
    	FixedPointNumber fp2 = new FixedPointNumber("2123.00");
    	assertEquals( fp1, fp2 );
    	assertEquals( false, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	
    	fp1 = new FixedPointNumber("2123");
    	fp2 = new FixedPointNumber("2125");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( false, fp2.isLessThan( fp1, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( true, fp2.isGreaterThan( fp1, ConstTest.DIFF_TOLERANCE ) );
    	
    	fp1 = new FixedPointNumber("2123");
    	fp2 = new FixedPointNumber("2123.01");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( false, fp2.isLessThan( fp1, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( true, fp2.isGreaterThan( fp1, ConstTest.DIFF_TOLERANCE ) );
    	
    	fp1 = new FixedPointNumber("-2123");
    	fp2 = new FixedPointNumber("-2123.01");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( false, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( true, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( true, fp2.isLessThan( fp1, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( false, fp2.isGreaterThan( fp1, ConstTest.DIFF_TOLERANCE ) );
    	
    	fp1 = new FixedPointNumber("-2123.00");
    	fp2 = new FixedPointNumber("-2124.00");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( false, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( true, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( true, fp2.isLessThan( fp1, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( false, fp2.isGreaterThan( fp1, ConstTest.DIFF_TOLERANCE ) );
    }
    
    @Test
    public void test04_3() throws Exception {
    	FixedPointNumber fp1 = new FixedPointNumber("2123");
    	FixedPointNumber fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE * 2 ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2 ) );
    	assertEquals( false, fp1.isGreaterThan( fp2 ) );
    	
    	fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2 ) );
    	assertEquals( false, fp1.isGreaterThan( fp2 ) );
    	
    	fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE * 0.5 ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2 ) );
    	assertEquals( false, fp1.isGreaterThan( fp2 ) );
    }
    
    @Test
    public void test04_4() throws Exception {
    	FixedPointNumber fp1 = new FixedPointNumber("2123");
    	FixedPointNumber fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE * 2 ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( false, fp1.equals( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( true, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE) );
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE ) );
    	
    	fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.equals( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( false, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE ) ); // sic, as opposed to result in test_04_3
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE ) ); // sic
    	
    	fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE * 0.5 ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.equals( fp2, ConstTest.DIFF_TOLERANCE ) );
    	assertEquals( false, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE ) ); // sic, as opposed to result in test_04_3
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE ) ); // dto.
    }
    
}
