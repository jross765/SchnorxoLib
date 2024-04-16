package xyz.schnorxoborx.base.dateutils;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.dateutils.LocalDateTimeHelpers;

public class TestLocalDateTimeHelpers
{
  public static void main(String[] args) throws Exception
  {
    junit.textui.TestRunner.run(suite());
  }

  public static junit.framework.Test suite() 
  {
    return new JUnit4TestAdapter(TestLocalDateTimeHelpers.class);  
  }

  @Test
  public void test01() throws Exception
  {
    LocalDateTime dateTime = LocalDateTimeHelpers.parseLocalDateTime("2020-02-01 13:15:12", "yyyy-MM-dd HH:mm:ss");
    assertEquals(2020, dateTime.getYear());
    assertEquals(Month.FEBRUARY, dateTime.getMonth());
    assertEquals(1, dateTime.getDayOfMonth());
    assertEquals(13, dateTime.getHour());
    assertEquals(15, dateTime.getMinute());
    assertEquals(12, dateTime.getSecond());

    dateTime = LocalDateTimeHelpers.parseLocalDateTime("2020-02-01T13:15:12", "yyyy-MM-dd'T'HH:mm:ss");
    assertEquals(2020, dateTime.getYear());
    assertEquals(Month.FEBRUARY, dateTime.getMonth());
    assertEquals(1, dateTime.getDayOfMonth());
    assertEquals(13, dateTime.getHour());
    assertEquals(15, dateTime.getMinute());
    assertEquals(12, dateTime.getSecond());
  }

  @Test
  public void test02() throws Exception
  {
    LocalDateTime dateTime = LocalDateTimeHelpers.parseLocalDateTime("2020-02-01T13:15:12");
    assertEquals(2020, dateTime.getYear());
    assertEquals(Month.FEBRUARY, dateTime.getMonth());
    assertEquals(1, dateTime.getDayOfMonth());
    assertEquals(13, dateTime.getHour());
    assertEquals(15, dateTime.getMinute());
    assertEquals(12, dateTime.getSecond());
  }
}

