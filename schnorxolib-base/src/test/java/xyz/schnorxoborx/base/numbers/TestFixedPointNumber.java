package xyz.schnorxoborx.base.numbers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.numbers.fraction.BigFraction;
import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.ConstTest;

public class TestFixedPointNumber {

	private static final int MAX_ITER = 30;

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
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(0, num.intValue());

    	num = FixedPointNumber.ZERO.copy(); // Important: copy()
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(0, num.intValue());

    	num = new FixedPointNumber("0");
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(0, num.intValue());

    	num = FixedPointNumber.ONE.copy(); // Important: copy()
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber("1");
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber("1/1");
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber("100/100");
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber("100/200");
    	assertEquals(0.5, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);

    	num = new FixedPointNumber("200/100");
    	assertEquals(2.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(2, num.intValue());

    	num = new FixedPointNumber("1/1000000");
    	assertEquals(0.000001, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(num.getBigDecimal().doubleValue(), (new BigDecimal(0.000001)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( new BigDecimal(0.000001)
    					.subtract( num.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_HARD ) ) <= 0 );

    	num = new FixedPointNumber("1/100000000");
    	assertEquals(0.00000001, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(num.getBigDecimal().doubleValue(), (new BigDecimal(0.00000001)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( new BigDecimal(0.00000001)
    					.subtract( num.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_HARD ) ) <= 0 );
    	
    	num = new FixedPointNumber("1287472/1000000");
    	// ::TODO
    	// assertEquals(1.287472, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(num.getBigDecimal().doubleValue(), (new BigDecimal(1.287472)).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( new BigDecimal(1.287472)
    					.subtract( num.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!
    	
    	num = new FixedPointNumber("355/113");
    	assertEquals(Math.PI, num.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	
    	// ---

    	num = new FixedPointNumber("-1"); // valid
    	assertEquals(-1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(-1, num.intValue());

    	num = new FixedPointNumber("-100/100"); // valid
    	assertEquals(-1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(-1, num.intValue());

    	num = new FixedPointNumber("100/-100"); // valid
    	assertEquals(-1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(-1, num.intValue());

    	num = new FixedPointNumber("-100/-100"); // valid
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(1, num.intValue());
    }

    @Test
    public void test01_2() throws Exception {
    	FixedPointNumber num = new FixedPointNumber(0);
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(0, num.intValue());

    	num = new FixedPointNumber(1);
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber(18);
    	assertEquals(18.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(18, num.intValue());

    	num = new FixedPointNumber(-18);
    	assertEquals(-18.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(-18, num.intValue());

    	num = new FixedPointNumber(48238112);
    	assertEquals(48238112.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(48238112, num.intValue());
    }

    @Test
    public void test01_3() throws Exception {
    	FixedPointNumber num = new FixedPointNumber(BigDecimal.valueOf(0));
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(0, num.intValue());

    	num = new FixedPointNumber(BigDecimal.valueOf(1));
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(1, num.intValue());

    	num = new FixedPointNumber(BigDecimal.valueOf(18));
    	assertEquals(18.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(18, num.intValue());

    	num = new FixedPointNumber(BigDecimal.valueOf(-18));
    	assertEquals(-18.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(-18, num.intValue());

    	num = new FixedPointNumber(BigDecimal.valueOf(48238112));
    	assertEquals(48238112.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(48238112, num.intValue());
    }

    @Test
    public void test02_1() throws Exception {
    	FixedPointNumber num = new FixedPointNumber();
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(0, num.intValue());

    	num.add("1");
    	assertEquals(1.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(1, num.intValue());

    	num.add(new FixedPointNumber("1"));
    	assertEquals(2.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(2, num.intValue());

    	num.add(-12);
    	assertEquals(-10.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(-10, num.intValue());

    	num.add(BigDecimal.valueOf(23.127));
    	assertEquals(13.127, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	
    	// ---

    	num.subtract("13");
    	assertEquals(0.127, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);

    	num.subtract(new FixedPointNumber("127/1000"));
    	assertEquals(0.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(0, num.intValue());

    	num.subtract(-12);
    	assertEquals(12.0, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	assertEquals(12, num.intValue());

    	num.subtract(BigDecimal.valueOf(12.1));
    	assertEquals(-0.1, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    }

    @Test
    public void test02_2() throws Exception {
    	FixedPointNumber num = new FixedPointNumber(12.1);
    	assertEquals(12.1, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);

    	num.multiply(2);
    	assertEquals(24.2, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);

    	num.multiply(BigDecimal.valueOf(3.2));
    	assertEquals(77.44, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);

    	num.multiply(new FixedPointNumber("3/2"));
    	assertEquals(116.16, num.doubleValue(), ConstTest.DIFF_TOLERANCE_HARD);
    	
    	num.multiply(new FixedPointNumber("2/3"));
    	assertEquals(77.44, num.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX); // TOLERANCE!
    	
    	num.divide(BigDecimal.valueOf(4));
    	assertEquals(19.36, num.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX); // dto.
    	
    	num.divide(BigDecimal.TEN);
    	assertEquals(1.936, num.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX); // dto.
    }

    // Compare with an alternative implementation
    @Test
    public void test03_1() throws Exception {
    	FixedPointNumber fp = new FixedPointNumber("3/2");
    	BigFraction      br = BigFraction.of(3, 2);
    	
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
    					.subtract( fp.getBigDecimal() )
    					.abs()
    					.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_HARD ) ) <= 0 );
    	
    	// ---

    	fp.add("77/82");
    	br = br.add(BigFraction.of(77, 82));
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
						.subtract( fp.getBigDecimal() )
						.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_HARD ) ) <= 0 );

    	fp.add("783/11");
    	br = br.add(BigFraction.of(783, 11));
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
						.subtract( fp.getBigDecimal() )
						.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_HARD ) ) <= 0 );
    	
    	// ---

    	fp.subtract("812/749");
    	br = br.subtract(BigFraction.of(812, 749));
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
						.subtract( fp.getBigDecimal() )
						.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_HARD ) ) <= 0 );

    	fp.subtract("387/21");
    	br = br.subtract(BigFraction.of(387, 21));
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
						.subtract( fp.getBigDecimal() )
						.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_HARD ) ) <= 0 );
    	
    	// ---

    	fp.multiply("12/8");
    	br = br.multiply(BigFraction.of(12, 8));
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
						.subtract( fp.getBigDecimal() )
						.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!

    	fp.multiply("275/18");
    	br = br.multiply(BigFraction.of(275, 18));
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
						.subtract( fp.getBigDecimal() )
						.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!

    	fp.multiply("12/8");
    	br = br.multiply(BigFraction.of(12, 8));
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
						.subtract( fp.getBigDecimal() )
						.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!

    	// ---

    	fp.divide("67/33");
    	br = br.divide(BigFraction.of(67, 33));
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!

    	fp.divide("59/24");
    	br = br.divide(BigFraction.of(59, 24));
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!

    	// ---

    	fp.negate();
    	br = br.negate();
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
    					.subtract( fp.getBigDecimal() )
    					.abs()
						.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) <= 0 ); // tolerance!

    	fp = fp.reciprocal();
    	br = br.reciprocal();
    	assertEquals(fp.doubleValue(), br.doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertEquals(fp.getBigDecimal().doubleValue(), br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP).doubleValue(), ConstTest.DIFF_TOLERANCE_LAX);
    	assertTrue( br.bigDecimalValue(fp.scale(), RoundingMode.HALF_UP)
    					.subtract( fp.getBigDecimal() )
    					.abs()
    					.compareTo( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_HARD ) ) <= 0 );
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
    	assertEquals( false, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	
    	fp1 = new FixedPointNumber("2123");
    	fp2 = new FixedPointNumber("2125");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( false, fp2.isLessThan( fp1, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( true, fp2.isGreaterThan( fp1, ConstTest.DIFF_TOLERANCE_LAX ) );
    	
    	fp1 = new FixedPointNumber("2123");
    	fp2 = new FixedPointNumber("2123.01");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( false, fp2.isLessThan( fp1, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( true, fp2.isGreaterThan( fp1, ConstTest.DIFF_TOLERANCE_LAX ) );
    	
    	fp1 = new FixedPointNumber("-2123");
    	fp2 = new FixedPointNumber("-2123.01");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( false, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( true, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( true, fp2.isLessThan( fp1, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( false, fp2.isGreaterThan( fp1, ConstTest.DIFF_TOLERANCE_LAX ) );
    	
    	fp1 = new FixedPointNumber("-2123.00");
    	fp2 = new FixedPointNumber("-2124.00");
    	assertNotEquals( fp1, fp2 );
    	assertEquals( false, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( true, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( true, fp2.isLessThan( fp1, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( false, fp2.isGreaterThan( fp1, ConstTest.DIFF_TOLERANCE_LAX ) );
    }
    
    @Test
    public void test04_3() throws Exception {
    	FixedPointNumber fp1 = new FixedPointNumber("2123");
    	FixedPointNumber fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX * 2 ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2 ) );
    	assertEquals( false, fp1.isGreaterThan( fp2 ) );
    	
    	fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2 ) );
    	assertEquals( false, fp1.isGreaterThan( fp2 ) );
    	
    	fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX * 0.5 ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.isLessThan( fp2 ) );
    	assertEquals( false, fp1.isGreaterThan( fp2 ) );
    }
    
    @Test
    public void test04_4() throws Exception {
    	FixedPointNumber fp1 = new FixedPointNumber("2123");
    	FixedPointNumber fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX * 2 ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( false, fp1.equals( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( true, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE_LAX) );
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	
    	fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.equals( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( false, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) ); // sic, as opposed to result in test_04_3
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) ); // sic
    	
    	fp2 = new FixedPointNumber( fp1.getBigDecimal().add( BigDecimal.valueOf( ConstTest.DIFF_TOLERANCE_LAX * 0.5 ) ) );
    	assertNotEquals( fp1, fp2 );
    	assertEquals( true, fp1.equals( fp2, ConstTest.DIFF_TOLERANCE_LAX ) );
    	assertEquals( false, fp1.isLessThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) ); // sic, as opposed to result in test_04_3
    	assertEquals( false, fp1.isGreaterThan( fp2, ConstTest.DIFF_TOLERANCE_LAX ) ); // dto.
    }
    
    @Test
    public void test07_1() throws Exception {
    	int nofDigits = 2;
    	FixedPointNumber br = FixedPointNumber.from(Math.PI, nofDigits);
    	assertEquals(Math.PI, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	nofDigits = 4;
    	br = FixedPointNumber.from(Math.PI, nofDigits);
    	assertEquals(Math.PI, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	nofDigits = 6;
    	br = FixedPointNumber.from(Math.PI, nofDigits);
    	assertEquals(Math.PI, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	nofDigits = 9;
    	br = FixedPointNumber.from(Math.PI, nofDigits);
    	assertEquals(Math.PI, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	nofDigits = 12;
    	br = FixedPointNumber.from(Math.PI, nofDigits);
    	assertEquals(Math.PI, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	nofDigits = 15;
    	br = FixedPointNumber.from(Math.PI, nofDigits);
    	assertEquals(Math.PI, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	// ---
    	
    	nofDigits = 2;
    	br = FixedPointNumber.from(Math.E, nofDigits);
    	assertEquals(Math.E, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	nofDigits = 4;
    	br = FixedPointNumber.from(Math.E, nofDigits);
    	assertEquals(Math.E, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	nofDigits = 6;
    	br = FixedPointNumber.from(Math.E, nofDigits);
    	assertEquals(Math.E, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	nofDigits = 9;
    	br = FixedPointNumber.from(Math.E, nofDigits);
    	assertEquals(Math.E, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	nofDigits = 12;
    	br = FixedPointNumber.from(Math.E, nofDigits);
    	assertEquals(Math.E, br.doubleValue(), Math.pow(10, -nofDigits));
    	
    	nofDigits = 15;
    	br = FixedPointNumber.from(Math.E, nofDigits);
    	assertEquals(Math.E, br.doubleValue(), Math.pow(10, -nofDigits));
    }
    
    @Test
    public void test07_2() throws Exception {
    	double eps = 0.01;
    	FixedPointNumber br = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	assertEquals(Math.PI, br.doubleValue(), eps);
    	
    	eps = 1.0e-4;
    	br = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
    	assertEquals( Math.PI, br.doubleValue(), eps);

    	// ::TODO
//    	eps = 1.0e-6;
//    	br = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
//    	assertEquals( Math.PI, br.doubleValue(), eps);
//    	
//    	eps = 1.0e-9;
//    	br = FixedPointNumber.from(Math.PI, eps, MAX_ITER);
//    	assertEquals( Math.PI, br.doubleValue(), eps);
    	
    	// ---
    	
    	eps = 0.001;
    	br = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	assertEquals(Math.E, br.doubleValue(), eps);
    	
    	eps = 1.0e-4;
    	br = FixedPointNumber.from(Math.E, eps, MAX_ITER);
    	assertEquals(Math.E, br.doubleValue(), eps);
    	
//    	eps = 1.0e-6;
//    	br = FixedPointNumber.from(Math.E, eps, MAX_ITER);
//    	assertEquals(Math.E, br.doubleValue(), eps);
    	
//    	eps = 1.0e-9;
//    	br = FixedPointNumber.from(Math.E, eps, MAX_ITER);
//    	assertEquals(Math.E, br.doubleValue(), eps);
    }
    
}
