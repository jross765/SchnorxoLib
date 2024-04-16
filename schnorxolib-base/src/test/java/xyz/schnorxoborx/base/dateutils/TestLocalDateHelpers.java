package xyz.schnorxoborx.base.dateutils;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.basetypes.Quarter;
import xyz.schnorxoborx.base.dateutils.LocalDateHelpers;

public class TestLocalDateHelpers
{
  public static void main(String[] args) throws Exception
  {
    junit.textui.TestRunner.run(suite());
  }

  public static junit.framework.Test suite() 
  {
    return new JUnit4TestAdapter(TestLocalDateHelpers.class);  
  }

  @Test
  public void test01() throws Exception
  {
    LocalDate date = LocalDateHelpers.parseLocalDate("01.02.2020");
    assertEquals(2020, date.getYear());
    assertEquals(Month.FEBRUARY, date.getMonth());
    assertEquals(1, date.getDayOfMonth());
  }

  @Test
  public void test02() throws Exception
  {
    LocalDate date = LocalDateHelpers.parseLocalDate("01.02.2020");
    assertEquals(new Quarter(1), LocalDateHelpers.getQuarter(date));
    
    date = LocalDateHelpers.parseLocalDate("31.03.2020");
    assertEquals(new Quarter(1), LocalDateHelpers.getQuarter(date));
    
    date = LocalDateHelpers.parseLocalDate("01.04.2020");
    assertEquals(new Quarter(2), LocalDateHelpers.getQuarter(date));
    
    date = LocalDateHelpers.parseLocalDate("02.04.2020");
    assertEquals(new Quarter(2), LocalDateHelpers.getQuarter(date));

    date = LocalDateHelpers.parseLocalDate("30.09.2020");
    assertEquals(new Quarter(3), LocalDateHelpers.getQuarter(date));

    date = LocalDateHelpers.parseLocalDate("1.10.2020");
    assertEquals(new Quarter(4), LocalDateHelpers.getQuarter(date));

    date = LocalDateHelpers.parseLocalDate("1.10.2020");
    assertEquals(new Quarter(4), LocalDateHelpers.getQuarter(date));
  }
}

