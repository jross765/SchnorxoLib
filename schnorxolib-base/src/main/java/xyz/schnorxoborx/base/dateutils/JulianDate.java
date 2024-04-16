package xyz.schnorxoborx.base.dateutils;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

/*
 * Internal representation of month:  1 for jan, 2 for feb etc.
 */
public class JulianDate
{
  public static final int INDEX_YEAR  = 0;
  public static final int INDEX_MONTH = 1;
  public static final int INDEX_DAY   = 2;

  /**
   * Returns the Julian day number that begins at noon of this day, Positive
   * year signifies A.D., negative year B.C. Remember that the year after 1 B.C.
   * was 1 A.D.
   * 
   * ref : Numerical Recipes in C, 2nd ed., Cambridge University Press 1992
   */
  // Gregorian Calendar adopted Oct. 15, 1582 (2299161)
  public static int JGREG = 15 + 31 * (10 + 12 * 1582); // ::MAGIC

  public static double HALFSECOND = 0.5;
  
  // -----------------------------------------------------------------

  public static double toJulian(Calendar cal) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    Day day = new Day(cal);
    return toJulian(day);
  }

  // bewusste Redundanz zur Day-Variante
  public static double toJulian(LocalDate date) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    int[] temp = { 0, 0, 0 };
    
    temp[INDEX_YEAR]  = date.getYear();
    temp[INDEX_MONTH] = date.getMonthValue(); // ACHTUNG
    temp[INDEX_DAY]   = date.getDayOfMonth();
    
    return toJulian(temp);
  }

  @Deprecated
  public static double toJulian(Date date) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    Day day = new Day(date);
    return toJulian(day);
  }

  public static double toJulian(Day day) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    int[] temp = { 0, 0, 0 };
    
    temp[INDEX_YEAR]  = day.getYear();
    temp[INDEX_MONTH] = day.getMonthNo(); // ACHTUNG
    temp[INDEX_DAY]   = day.getDayOfMonth();
    
    return toJulian(temp);
  }

  // -----------------------------------------------------------------
  
  public static double toJulian(Year year, MonthDay monthDay) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    return toJulian(year, monthDay.getMonth(), monthDay.getDayOfMonth());
  }

  public static double toJulian(Year year, Month month, int dayOfMonth) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    return toJulian(year.getValue(), month.getValue(), dayOfMonth);
  }

  public static double toJulian(int year, int monthVal, int dayOfMonth) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    int[] day = { year, monthVal, dayOfMonth };
    
    return toJulian(day);
  }

  // -----------------------------------------------------------------
  
  // ::MAGIC
  public static double toJulian(int[] ymd) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    int year  = ymd[INDEX_YEAR];
    int month = ymd[INDEX_MONTH]; // jan=1, feb=2,...
    int day   = ymd[INDEX_DAY];
    
    if ( month < Month.JANUARY.getValue() || 
         month > Month.DECEMBER.getValue()  )
      throw new IllegalMonthValueException();
    
    if ( day < 1 || day > 31 )
      throw new IllegalDayOfMonthValueException();
    
    int julianYear = year;
    if (year < 0)
      julianYear++;
    int julianMonth = month;
    if (month > 2)
    {
      julianMonth++;
    }
    else
    {
      julianYear--;
      julianMonth += 13;
    }

    double julian = (java.lang.Math.floor(365.25 * julianYear)
        + java.lang.Math.floor(30.6001 * julianMonth) + day + 1720995.0);
    if (day + 31 * (month + 12 * year) >= JGREG)
    {
      // change over to Gregorian calendar
      int ja = (int) (0.01 * julianYear);
      julian += 2 - ja + (0.25 * ja);
    }
    return java.lang.Math.floor(julian);
  }
  
  // -----------------------------------------------------------------

  public static Day fromJulianDay(double injulian)
  {
    int[] temp = fromJulian(injulian);

    Day day = new Day(temp[INDEX_YEAR], temp[INDEX_MONTH] - 1, temp[INDEX_DAY]);
    return day;
  }
  
  public static LocalDate fromJulianLocalDate(double injulian)
  {
    int[] temp = fromJulian(injulian);

    LocalDate date = LocalDate.of(temp[INDEX_YEAR], temp[INDEX_MONTH], temp[INDEX_DAY]);
    return date;
  }

  @Deprecated
  public static Date fromJulianDate(double injulian)
  {
    Day day = fromJulianDay(injulian);

    return day.getDate();
  }
  
  /**
   * Converts a Julian day to a calendar date ref : Numerical Recipes in C, 2nd
   * ed., Cambridge University Press 1992
   * 
   * ::MAGIC
   */
  public static int[] fromJulian(double injulian)
  {
    int jalpha, ja, jb, jc, jd, je, year, month, day;
    double julian = injulian + HALFSECOND / 86400.0;
    ja = (int) injulian;
    if (ja >= JGREG)
    {
      jalpha = (int) (((ja - 1867216) - 0.25) / 36524.25);
      ja = ja + 1 + jalpha - jalpha / 4;
    }

    jb = ja + 1524;
    jc = (int) (6680.0 + ((jb - 2439870) - 122.1) / 365.25);
    jd = 365 * jc + jc / 4;
    je = (int) ((jb - jd) / 30.6001);
    day = jb - jd - (int) (30.6001 * je);
    month = je - 1;
    if (month > 12)
      month = month - 12;
    year = jc - 4715;
    if (month > 2)
      year--;
    if (year <= 0)
      year--;

    return new int[] { year, month, day };
  }
  
  // -----------------------------------------------------------------
  
  public static LocalDate addLocalDate(LocalDate date1, int nofDays) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    double julian = addJulian(date1, nofDays);
    int ymd[] = fromJulian(julian);
    return LocalDate.of(ymd[INDEX_YEAR], ymd[INDEX_MONTH], ymd[INDEX_DAY]);
  }

  @Deprecated
  public static Date addDate(Date date1, int nofDays) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    return addDay(new Day(date1), nofDays).getDate();
  }

  public static Day addDay(Day day1, int nofDays) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    double julian = addJulian(day1, nofDays);
    int ymd[] = fromJulian(julian);
    return new Day(ymd[INDEX_YEAR], ymd[INDEX_MONTH] - 1, ymd[INDEX_DAY]);
  }

  public static double addJulian(LocalDate date1, int nofDays) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    return toJulian(date1) + nofDays; 
  }

  public static double addJulian(Day day1, int nofDays) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    return toJulian(day1) + nofDays; 
  }

  // -----------------------------------------------------------------
  
  public static double subtract(Day day1, Day day2) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    double temp1 = toJulian(day1); 
    double temp2 = toJulian(day2);
    return temp1 - temp2;
  }

  public static double subtract(LocalDate date1, LocalDate date2) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    double temp1 = toJulian(date1); 
    double temp2 = toJulian(date2);
    return temp1 - temp2;
  }

  @Deprecated
  public static double subtract(Date date1, Date date2) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    Day day1 = new Day(date1); 
    Day day2 = new Day(date2); 
    return subtract(day1, day2);
  }

  public static double subtract(Day day1, LocalDate date2) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    return subtract(day1.getLocalDate(), date2);
  }

  @Deprecated
  public static double subtract(Day day1, Date date2) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    Day day2 = new Day(date2); 
    return subtract(day1, day2);
  }

  public static double subtract(LocalDate date1, Day day2) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    return subtract(date1, day2.getLocalDate());
  }
  
  @Deprecated
  public static double subtract(Date date1, Day day2) throws IllegalMonthValueException, IllegalDayOfMonthValueException
  {
    Day day1 = new Day(date1); 
    return subtract(day1, day2);
  }
}
