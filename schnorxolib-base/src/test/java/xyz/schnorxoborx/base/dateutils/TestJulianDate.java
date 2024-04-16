package xyz.schnorxoborx.base.dateutils;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.dateutils.Day;
import xyz.schnorxoborx.base.dateutils.IllegalDayOfMonthValueException;
import xyz.schnorxoborx.base.dateutils.IllegalMonthValueException;
import xyz.schnorxoborx.base.dateutils.JulianDate;

public class TestJulianDate
{
  public static void main(String[] args) throws Exception
  {
    junit.textui.TestRunner.run(suite());
  }

  public static junit.framework.Test suite() 
  {
    return new JUnit4TestAdapter(TestJulianDate.class);  
  }
  
  // -----------------------------------------------------------------

  public void test01(String args[]) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    // FIRST TEST reference point
    System.out.println("Julian date for May 23, 1968 : " + JulianDate.toJulian(new int[] { 1968, 5, 23 }));
    // output : 2440000
    int results[] = JulianDate.fromJulian(JulianDate.toJulian(new int[] { 1968, 5, 23 }));
    System.out.println("... back to calendar : " + results[0] + " " + results[1] + " " + results[2]);

    // SECOND TEST "today"
    LocalDate today = LocalDate.of(2020, 4, 1);
    double todayJulian = JulianDate.toJulian( new int[] { today.getYear(),
                                                          today.getMonthValue() + 1, 
                                                          today.getDayOfMonth() } );
    System.out.println("Julian date for today : " + todayJulian);
    results = JulianDate.fromJulian(todayJulian);
    System.out.println("... back to calendar : " + results[0] + " " + results[1] + " " + results[2]);

    // THIRD TEST
    double date1 = JulianDate.toJulian(new int[] { 2005, 1, 1 });
    double date2 = JulianDate.toJulian(new int[] { 2005, 1, 31 });
    System.out.println("Between 2005-01-01 and 2005-01-31 : " + (date2 - date1)  + " days");

    /*
     * expected output : Julian date for May 23, 1968 : 2440000.0 ... back to
     * calendar 1968 5 23 Julian date for today : 2453487.0 ... back to calendar
     * 2005 4 26 Between 2005-01-01 and 2005-01-31 : 30.0 days
     */
  }
  
  @Test
  public void test01() throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    // Cf. http://de.wikipedia.org/wiki/Julianisches_Datum
    int[] ymd = {0, 0, 0};
    ymd[0] = 1582;
    ymd[1] = 10; // oct
    ymd[2] = 15;
    assertEquals(2299161, JulianDate.toJulian(ymd), 0.1);
    
    ymd[0] = 2000;
    ymd[1] = 1; // jan
    ymd[2] = 1;
    assertEquals(2451545, JulianDate.toJulian(ymd), 0.1);

    ymd[0] = 2011;
    ymd[1] = 5; // may
    ymd[2] = 23;
    assertEquals(2455705, JulianDate.toJulian(ymd), 0.1);
  }

  @Test
  public void test02() throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    Day day1 = new Day(1582, Calendar.OCTOBER, 15);
    assertEquals(2299161, JulianDate.toJulian(day1), 0.1);
    
    day1 = new Day(2000, Calendar.JANUARY, 1);
    assertEquals(2451545, JulianDate.toJulian(day1), 0.1);
    
    day1 = new Day(2011, Calendar.MAY, 23);
    assertEquals(2455705, JulianDate.toJulian(day1), 0.1);
  }

  @Test
  public void test03() throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    Day day1 = new Day(1971, Calendar.MAY, 23); // 2441094.5
    
    // ACHTUNG, sic: in den folgenden Zeilen eben NICHT Calendar.MAY, 
    // sondern 5 usw.
    assertEquals(1971, JulianDate.addDay(day1, 0).getYear());
    assertEquals(5, JulianDate.addDay(day1, 0).getMonthNo());
    assertEquals(23, JulianDate.addDay(day1, 0).getDayOfMonth());

    assertEquals(1971, JulianDate.addDay(day1, -3).getYear());
    assertEquals(5, JulianDate.addDay(day1, -3).getMonthNo());
    assertEquals(20, JulianDate.addDay(day1, -3).getDayOfMonth());

    assertEquals(1971, JulianDate.addDay(day1, 3).getYear());
    assertEquals(5, JulianDate.addDay(day1, 3).getMonthNo());
    assertEquals(26, JulianDate.addDay(day1, 3).getDayOfMonth());

    assertEquals(1971, JulianDate.addDay(day1, 9).getYear());
    assertEquals(6, JulianDate.addDay(day1, 9).getMonthNo());
    assertEquals(1, JulianDate.addDay(day1, 9).getDayOfMonth());
  }

  @Test
  public void test04() throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    Date date1 = new Day(1971, Calendar.MAY, 23).getDate(); // 2441094.5
    
    assertEquals(71, JulianDate.addDate(date1, -3).getYear());
    assertEquals(Calendar.MAY, JulianDate.addDate(date1, -3).getMonth());
    assertEquals(20, JulianDate.addDate(date1, -3).getDate()); // ACHTUNG; falsch!

    assertEquals(71, JulianDate.addDate(date1, 3).getYear());
    assertEquals(Calendar.MAY, JulianDate.addDate(date1, 3).getMonth());
    assertEquals(26, JulianDate.addDate(date1, 3).getDate()); // ACHTUNG; falsch!

    assertEquals(71, JulianDate.addDate(date1, 9).getYear());
    assertEquals(Calendar.JUNE, JulianDate.addDate(date1, 9).getMonth());
    assertEquals(1, JulianDate.addDate(date1, 9).getDate()); // ACHTUNG; falsch!
  }

  @Test
  public void test05() throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    LocalDate date1 = LocalDate.of(1971, 5, 23); // sic, 5
    
    assertEquals(1971, JulianDate.addLocalDate(date1, -3).getYear());
    assertEquals(5, JulianDate.addLocalDate(date1, -3).getMonthValue());
    assertEquals(20, JulianDate.addLocalDate(date1, -3).getDayOfMonth());

    assertEquals(1971, JulianDate.addLocalDate(date1, 3).getYear());
    assertEquals(5, JulianDate.addLocalDate(date1, 3).getMonthValue());
    assertEquals(26, JulianDate.addLocalDate(date1, 3).getDayOfMonth());

    assertEquals(1971, JulianDate.addLocalDate(date1, 9).getYear());
    assertEquals(6, JulianDate.addLocalDate(date1, 9).getMonthValue());
    assertEquals(1, JulianDate.addLocalDate(date1, 9).getDayOfMonth());
  }

  @Test
  public void test06() throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    Day day1 = new Day(1971, Calendar.MAY, 20);
    Day day2 = new Day(1971, Calendar.MAY, 23);
    assertEquals(3, JulianDate.subtract(day2, day1), 0.1);

    day1 = new Day(1971, Calendar.MAY, 23);
    day2 = new Day(1972, Calendar.MAY, 22);
    assertEquals(365, JulianDate.subtract(day2, day1), 0.1);

    day1 = new Day(1971, Calendar.MAY, 23);
    day2 = new Day(2011, Calendar.MAY, 22);
    assertEquals(14609, JulianDate.subtract(day2, day1), 0.1);
  }
}

