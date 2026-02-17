package xyz.schnorxoborx.base.dateutils;

import static org.junit.Assert.assertEquals;

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
	public void test01() throws Exception
	{
		Date date = DateHelpers.parseDate(LocalDateHelpers.DATE_UNSET);
		assertEquals(DateHelpers.DATE_UNSET, df.format(date));
		assertEquals(DateHelpers.DATE_UNSET, DateHelpers.getStr2(date));
		
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
	
	@Test
	public void test04()
	{
		Timestamp ts = new Timestamp( 562500000000l );
		Date date = DateHelpers.toDate( ts );
		assertEquals( ts.getYear(), date.getYear() );
		assertEquals( ts.getMonth(), date.getMonth() );
		assertEquals( ts.getDate(), date.getDate() );
	}
}
