package xyz.schnorxoborx.base.cmdlinetools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class TestHelper
{
	public static void main(String[] args) throws Exception
	{
		junit.textui.TestRunner.run( suite() );
	}

	@SuppressWarnings("exports")
	public static junit.framework.Test suite()
	{
		return new JUnit4TestAdapter( TestHelper.class );
	}

	@Test
	public void test04()
	{
		Helper.DateFormat df = Helper.DateFormat.ISO;
		assertEquals("yyyy-MM-dd", df.getPattern());
		
		df = Helper.DateFormat.EU;
		assertEquals("dd/MM/yyyy", df.getPattern());
		
		df = Helper.DateFormat.DIN;
		assertEquals("dd.MM.yyyy", df.getPattern());
	}
}
