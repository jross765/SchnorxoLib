package xyz.schnorxoborx.base.dateutils;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.dateutils.DateHelpers;

public class TestDateHelpers
{
  public static void main(String[] args) throws Exception
  {
    junit.textui.TestRunner.run(suite());
  }

  public static junit.framework.Test suite() 
  {
    return new JUnit4TestAdapter(TestDateHelpers.class);  
  }
  
  @Test
  public void test04()
  {
    Timestamp ts = new Timestamp(562500000000l);
    Date date = DateHelpers.toDate(ts);
    assertEquals(ts.getYear(), date.getYear());
    assertEquals(ts.getMonth(), date.getMonth());
    assertEquals(ts.getDate(), date.getDate());
  }
}

