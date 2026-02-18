package xyz.schnorxoborx.base.dateutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.basetypes.Quarter;
import xyz.schnorxoborx.base.cmdlinetools.Helper;

public class TestLocalDateHelpers
{
	public static void main(String[] args) throws Exception
	{
		junit.textui.TestRunner.run( suite() );
	}

	@SuppressWarnings("exports")
	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter( TestLocalDateHelpers.class );
	}

	@Test
	public void test00() throws Exception
	{
		LocalDate date = LocalDateHelpers.DATE_UNSET;
		assertEquals( LocalDateHelpers.DATE_UNSET_STR, date.toString() );
		assertEquals( LocalDateHelpers.DATE_UNSET_STR, LocalDateHelpers.getStr2( date ) );
	}

	@Test
	public void test01() throws Exception
	{
		LocalDate date = LocalDateHelpers.parseLocalDate( "2020-02-01" );
		assertEquals( 2020, date.getYear() );
		assertEquals( Month.FEBRUARY, date.getMonth() );
		assertEquals( 1, date.getDayOfMonth() );
	}

	@Test
	public void test02() throws Exception
	{
		LocalDate date = LocalDateHelpers.parseLocalDate( LocalDateHelpers.DATE_UNSET_STR );
		assertEquals( LocalDateHelpers.DATE_UNSET_STR, date.toString() );

		date = LocalDateHelpers.parseLocalDate( "2001-12-31" );
		assertEquals( "2001-12-31", date.toString() );
		assertEquals( "2001-12-31", LocalDateHelpers.getStr2( date ) );

		date = LocalDateHelpers.parseLocalDate( "09.11.1989", Helper.DateFormat.DIN );
		assertEquals( "1989-11-09", date.toString() );
		assertEquals( "1989-11-09", LocalDateHelpers.getStr2( date ) );

		date = LocalDateHelpers.parseLocalDate( "17/12/2001", Helper.DateFormat.EU );
		assertEquals( "2001-12-17", date.toString() );
		assertEquals( "2001-12-17", LocalDateHelpers.getStr2( date ) );

		date = LocalDateHelpers.parseLocalDate( "09/11/2001", Helper.DateFormat.US );
		assertEquals( "2001-09-11", date.toString() );
		assertEquals( "2001-09-11", LocalDateHelpers.getStr2( date ) );

		date = LocalDateHelpers.parseLocalDate( "1945/08/06", Helper.DateFormat.ASIA );
		assertEquals( "1945-08-06", date.toString() );
		assertEquals( "1945-08-06", LocalDateHelpers.getStr2( date ) );
	}

	@Test
	public void test03() throws Exception
	{
		LocalDate date = LocalDateHelpers.parseLocalDate( "2020-02-01" );
		assertEquals( new Quarter( 1 ), LocalDateHelpers.getQuarter( date ) );

		date = LocalDateHelpers.parseLocalDate( "2020-03-31" );
		assertEquals( new Quarter( 1 ), LocalDateHelpers.getQuarter( date ) );

		date = LocalDateHelpers.parseLocalDate( "2020-04-01" );
		assertEquals( new Quarter( 2 ), LocalDateHelpers.getQuarter( date ) );

		date = LocalDateHelpers.parseLocalDate( "2020-04-02" );
		assertEquals( new Quarter( 2 ), LocalDateHelpers.getQuarter( date ) );

		date = LocalDateHelpers.parseLocalDate( "2020-09-30" );
		assertEquals( new Quarter( 3 ), LocalDateHelpers.getQuarter( date ) );

		date = LocalDateHelpers.parseLocalDate( "2020-10-01" );
		assertEquals( new Quarter( 4 ), LocalDateHelpers.getQuarter( date ) );

		date = LocalDateHelpers.parseLocalDate( "2020-10-01" );
		assertEquals( new Quarter( 4 ), LocalDateHelpers.getQuarter( date ) );
	}

	@Test
	public void test04()
	{
		Timestamp ts = new Timestamp( 562500000000l );
		LocalDate date = LocalDateHelpers.toLocalDate( ts );
		assertEquals( ts.getYear() + 1900, date.getYear() );
		assertEquals( ts.getMonth() + 1, date.getMonthValue() );
		assertEquals( ts.getDate(), date.getDayOfMonth() );
	}

	@Test
	public void test05()
	{
		LocalDate date1 = LocalDate.of(2000, 1, 1);
		
		LocalDate date2 = LocalDate.of(2000, 1, 1);
		assertEquals( date2, date1 );
		
		date2 = LocalDate.of(2000, 1, 2);
		assertNotEquals( date2, date1 );
	}

	@Test
	public void test06()
	{
		LocalDate date1 = LocalDate.of(2000, 1, 1);
		
		LocalDate date2 = LocalDateHelpers.copy(date1);
		assertFalse( date1 == date2 );
		assertEquals( date2, date1 );
		
		date2 = date2.plusDays(1);
		assertNotEquals( date2, date1 );
	}

}
