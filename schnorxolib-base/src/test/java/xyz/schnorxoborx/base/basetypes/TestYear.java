package xyz.schnorxoborx.base.basetypes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.basetypes.InvalidYearException;
import xyz.schnorxoborx.base.basetypes.Year;

public class TestYear 
{
  private static Year year = null;
  
  // -----------------------------------------------------------------

  public static void main(String[] args) throws Exception
  {
    junit.textui.TestRunner.run(suite());
  }

  public static junit.framework.Test suite() 
  {
    return new JUnit4TestAdapter(TestYear.class);  
  }
  
  @Before
  public void initialize() throws Exception
  {
    year = new Year();
  }

  // -----------------------------------------------------------------

  @Test
  public void test01() throws Exception
  {
    try 
    {
      year.reset();
      year.set(-88888); // invalid
    }
    catch (InvalidYearException exc)
    {
      assertEquals(Year.UNSET_VALUE, year.get());
    }
    
    try 
    {
      year.reset();
      year.set(77777); // invalid
    }
    catch (InvalidYearException exc)
    {
      assertEquals(Year.UNSET_VALUE, year.get());
    }

    try
    {
      year.reset();
      year.set(2020); // valid
      assertEquals(2020, year.get());
    }
    catch (InvalidYearException exc)
    {
      assertEquals(1234, year.get()); // Shall not be reached
    }
    
    try
    {
      year.reset();
      year.set(Year.MIN_YEAR); // valid
      assertEquals(Year.MIN_YEAR, year.get());
    }
    catch (InvalidYearException exc)
    {
      assertEquals(1234, year.get()); // Shall not be reached
    }
  
    try
    {
      year.reset();
      year.set(Year.MAX_YEAR); // valid
      assertEquals(Year.MAX_YEAR, year.get());
    }
    catch (InvalidYearException exc)
    {
      assertEquals(1234, year.get()); // Shall not be reached
    }

    try
    {
      year.reset();
      year.set(Year.MIN_YEAR - 1); // invalid
    }
    catch (InvalidYearException exc)
    {
      assertEquals(Year.UNSET_VALUE, year.get());
    }
  
    try
    {
      year.reset();
      year.set(Year.MAX_YEAR + 1); // valid
    }
    catch (InvalidYearException exc)
    {
      assertEquals(Year.UNSET_VALUE, year.get());
    }
  }

  @Test
  public void test02() throws Exception
  {
    assertEquals(-9999, Year.MIN_YEAR);
    assertEquals(9999, Year.MAX_YEAR);
  }
}
