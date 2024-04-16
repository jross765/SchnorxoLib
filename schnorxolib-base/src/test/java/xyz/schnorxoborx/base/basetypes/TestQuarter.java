package xyz.schnorxoborx.base.basetypes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.basetypes.InvalidQuarterException;
import xyz.schnorxoborx.base.basetypes.Quarter;

public class TestQuarter 
{
  private static Quarter quarter = null;
  
  // -----------------------------------------------------------------

  public static void main(String[] args) throws Exception
  {
    junit.textui.TestRunner.run(suite());
  }

  public static junit.framework.Test suite() 
  {
    return new JUnit4TestAdapter(TestQuarter.class);  
  }
  
  @Before
  public void initialize() throws Exception
  {
    quarter = new Quarter();
  }

  // -----------------------------------------------------------------

  @Test
  public void test01() throws Exception
  {
    try 
    {
      quarter.reset();
      quarter.set(0); // invalid
    }
    catch (InvalidQuarterException exc)
    {
      assertEquals(Quarter.UNSET_VALUE, quarter.get());
    }
    
    try 
    {
      quarter.reset();
      quarter.set(5); // invalid
    }
    catch (InvalidQuarterException exc)
    {
      assertEquals(Quarter.UNSET_VALUE, quarter.get());
    }

    try
    {
      quarter.reset();
      quarter.set(1); // valid
      assertEquals(1, quarter.get());
    }
    catch (InvalidQuarterException exc)
    {
      assertEquals(1, quarter.get()); // Shall not be reached
    }
    
    try
    {
      quarter.reset();
      quarter.set(Quarter.MIN_QUARTER); // valid
      assertEquals(Quarter.MIN_QUARTER, quarter.get());
    }
    catch (InvalidQuarterException exc)
    {
      assertEquals(Quarter.UNSET_VALUE, quarter.get()); // Shall not be reached
    }
  
    try
    {
      quarter.reset();
      quarter.set(Quarter.MAX_QUARTER); // valid
      assertEquals(Quarter.MAX_QUARTER, quarter.get());
    }
    catch (InvalidQuarterException exc)
    {
      assertEquals(Quarter.UNSET_VALUE, quarter.get()); // Shall not be reached
    }

    try
    {
      quarter.reset();
      quarter.set(Quarter.MIN_QUARTER - 1); // invalid
    }
    catch (InvalidQuarterException exc)
    {
      assertEquals(Quarter.UNSET_VALUE, quarter.get());
    }
  
    try
    {
      quarter.reset();
      quarter.set(Quarter.MAX_QUARTER + 1); // valid
    }
    catch (InvalidQuarterException exc)
    {
      assertEquals(Quarter.UNSET_VALUE, quarter.get());
    }
  }

  @Test
  public void test02() throws Exception
  {
    assertEquals(1, Quarter.MIN_QUARTER);
    assertEquals(4, Quarter.MAX_QUARTER);
  }
}
