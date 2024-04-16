package xyz.schnorxoborx.base.dateutils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateHelpers
{
  // ::MAGIC
  public final static String DATE_FORMAT_1 = "dd.MM.yyyy";
  public final static String DATE_FORMAT_2 = "yyyy-MM-dd";
  public final static String DATE_UNSET = "01.01.1900";
  
  // -----------------------------------------------------------------

  /**
   * Parst ein Datum im deutschen Standard-Format (kurz).
   * @param dateStr
   * @return
   */
  public static Date parseDate(String dateStr) throws Exception
  {
    return parseDate(dateStr, DATE_FORMAT_1); 
  }
  
  /**
   * Parst ein Datum im angegebenen Format.
   * @param dateStr
   * @return
   */
  public static Date parseDate(String dateStr, String format) throws Exception
  {
    DateFormat formatter = new SimpleDateFormat(format); 
    return (Date) formatter.parse(dateStr);
  }
  
  // -----------------------------------------------------------------

  @Deprecated
  public static Date toDate(Timestamp ts)
  {
    Date result = new Date();
    
    result.setDate(ts.getDate());
    result.setMonth(ts.getMonth());
    result.setYear(ts.getYear());

    result.setHours(0);
    result.setMinutes(0);
    result.setSeconds(0);

    return result;
  }

  // -----------------------------------------------------------------

  /**
   * Gibt ein Datum im deutschen Standard-Format aus.
   * @param dateStr
   * @return
   */
  public static String getStr1(Date date) throws Exception
  {
    return getStr(date, DATE_FORMAT_1);
  }
  
  /* dto. */
  public static String getStr1(Day day) throws Exception
  {
    return getStr(day.getDate(), DATE_FORMAT_1);
  }
  
  /**
   * Gibt ein Datum im Thilo-Datei-Format aus.
   * @param dateStr
   * @return
   */
  public static String getStr2(Date date) throws Exception
  {
    return getStr(date, DATE_FORMAT_2);
  }
  
  /* dto. */
  public static String getStr2(Day day) throws Exception
  {
    return getStr(day.getDate(), DATE_FORMAT_2);
  }
  
  /**
   * Gibt ein Datum im Format wie angegeben aus.
   * @param dateStr
   * @return
   */
  public static String getStr(Date date, String format) throws Exception
  {
    DateFormat formatter = new SimpleDateFormat(format); 
    return formatter.format(date);
  }
  
  public static String getStr(Day day, String format) throws Exception
  {
    return getStr(day.getDate(), format);
  }
  
  // -----------------------------------------------------------------

  /**
   * Vergleich auf Datums-Basis, ohne Zeit (i.Ggs. zu Date.equals).
   * @param date1
   * @param date2
   * @return
   */
  public static boolean areEqual(Date date1, Date date2)
  {
    Calendar day1 = Calendar.getInstance();
    Calendar day2 = Calendar.getInstance();

    day1.setTime(date1);
    day2.setTime(date2);
    if (day1.get(Calendar.YEAR)         == day2.get(Calendar.YEAR) && 
        day1.get(Calendar.MONTH)        == day2.get(Calendar.MONTH) && 
        day1.get(Calendar.DAY_OF_MONTH) == day2.get(Calendar.DAY_OF_MONTH))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  // -----------------------------------------------------------------

  /**
   * Return Date object representing 01. Jan. of current year.
   * @return
   */
  public static Date getFirstOfJan()
  {
    Calendar cal = new GregorianCalendar();
    
    cal.set(Calendar.DAY_OF_YEAR, 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    
    return cal.getTime();
  }
  
  public static Date getUnset() throws Exception
  {
    return parseDate(DATE_UNSET);
  }
}
