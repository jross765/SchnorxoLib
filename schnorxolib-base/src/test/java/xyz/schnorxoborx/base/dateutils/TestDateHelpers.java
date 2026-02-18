package xyz.schnorxoborx.base.dateutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.cmdlinetools.Helper;

public class TestDateHelpers
{
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	// ---------------------------------------------------------------

	public static void main(String[] args) throws Exception
	{
		junit.textui.TestRunner.run( suite() );
	}

	@SuppressWarnings("exports")
	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter( TestDateHelpers.class );
	}

	@Test
	public void test00() throws Exception
	{
		Date date = DateHelpers.DATE_UNSET;
		assertEquals(DateHelpers.DATE_UNSET_STR, df.format(date));
		assertEquals(DateHelpers.DATE_UNSET_STR, DateHelpers.getStr2(date));
	}
	
	@Test
	public void test01() throws Exception
	{
		Date date = DateHelpers.parseDate( "2020-02-01" );
		assertEquals( "2020-02-01", DateHelpers.getStr2(date) );
	}

	@Test
	public void test02() throws Exception
	{
		Date date = DateHelpers.parseDate(LocalDateHelpers.DATE_UNSET_STR);
		assertEquals(DateHelpers.DATE_UNSET_STR, df.format(date));
		assertEquals(DateHelpers.DATE_UNSET_STR, DateHelpers.getStr2(date));
		
		date = DateHelpers.parseDate("2001-12-31");
		assertEquals("2001-12-31", df.format(date));
		assertEquals("2001-12-31", DateHelpers.getStr2(date));
		
		date = DateHelpers.parseDate("09.11.1989", Helper.DateFormat.DIN);
		assertEquals("1989-11-09", df.format(date));
		assertEquals("1989-11-09", DateHelpers.getStr2(date));
		
		date = DateHelpers.parseDate("17/12/2001", Helper.DateFormat.EU);
		assertEquals("2001-12-17", df.format(date));
		assertEquals("2001-12-17", DateHelpers.getStr2(date));
		
		date = DateHelpers.parseDate("09/11/2001", Helper.DateFormat.US);
		assertEquals("2001-09-11", df.format(date));
		assertEquals("2001-09-11", DateHelpers.getStr2(date));
		
		date = DateHelpers.parseDate("1945/08/06", Helper.DateFormat.ASIA);
		assertEquals("1945-08-06", df.format(date));
		assertEquals("1945-08-06", DateHelpers.getStr2(date));
	}
	
//	@Test
//	public void test03() throws Exception
//	{
//		Date date = DateHelpers.parseDate( "2020-02-01" );
//		assertEquals( new Quarter( 1 ), DateHelpers.getQuarter( date ) );
//
//		date = DateHelpers.parseDate( "2020-03-31" );
//		assertEquals( new Quarter( 1 ), DateHelpers.getQuarter( date ) );
//
//		date = DateHelpers.parseDate( "2020-04-01" );
//		assertEquals( new Quarter( 2 ), DateHelpers.getQuarter( date ) );
//
//		date = DateHelpers.parseDate( "2020-04-02" );
//		assertEquals( new Quarter( 2 ), DateHelpers.getQuarter( date ) );
//
//		date = DateHelpers.parseDate( "2020-09-30" );
//		assertEquals( new Quarter( 3 ), DateHelpers.getQuarter( date ) );
//
//		date = DateHelpers.parseDate( "2020-10-01" );
//		assertEquals( new Quarter( 4 ), DateHelpers.getQuarter( date ) );
//
//		date = DateHelpers.parseDate( "2020-10-01" );
//		assertEquals( new Quarter( 4 ), DateHelpers.getQuarter( date ) );
//	}

	@Test
	public void test04()
	{
		Timestamp ts = new Timestamp( 562500000000l );
		Date date = DateHelpers.toDate( ts );
		assertEquals( ts.getYear(), date.getYear() );
		assertEquals( ts.getMonth(), date.getMonth() );
		assertEquals( ts.getDate(), date.getDate() );
	}

	@Test
	public void test05() throws Exception
	{
		Date date1 = DateHelpers.parseDate("2000-01-01");
		
		Date date2 = DateHelpers.parseDate("2000-01-01");
		assertEquals( date2, date1 );
		
		date2 = DateHelpers.parseDate("2000-01-02");
		assertNotEquals( date2, date1 );
	}

}
