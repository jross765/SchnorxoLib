package xyz.schnorxoborx.base.dateutils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.schnorxoborx.base.cmdlinetools.Helper;

public class DateHelpers
{
  private static final Logger LOGGER = LoggerFactory.getLogger(LocalDateHelpers.class);

  // -----------------------------------------------------------------
  // ::MAGIC

  public final static String DATE_UNSET_STR = "1900-01-01";
  
  public       static Date   DATE_UNSET     = null;

  // ----------------------------------------------------------------

  static
  {
	  try
	  {
		  DATE_UNSET  = parseDate(DATE_UNSET_STR);
	  }
	  catch ( Exception e )
	  {
		  LOGGER.error("static block: Could not set DATE_UNSET");
	  }
  }
  
  // -----------------------------------------------------------------

  /**
   * Parst ein Datum im deutschen Standard-Format (kurz).
   * @param dateStr
   * @return
 * @throws Exception 
   */
  public static Date parseDate(String dateStr) throws Exception
  {
    return parseDate(dateStr, Helper.DateFormat.ISO); 
  }
  
  /**
   * Parst ein Datum im angegebenen Format.
   * @param dateStr
   * @param pattern 
   * @return
   * @throws Exception 
   */
  public static Date parseDate(String dateStr, String pattern) throws Exception
  {
    DateFormat formatter = new SimpleDateFormat(pattern); 
    return (Date) formatter.parse(dateStr);
  }
  
  public static Date parseDate(String dateStr, Helper.DateFormat dfAdd) throws Exception
  {
    return parseDate(dateStr, dfAdd.getPattern()); 
  }
  
  public static Date parseDate(String dateStr, Locale loc) throws Exception
  {
	  // https://stackoverflow.com/questions/4594519/how-do-i-get-localized-date-pattern-string
	  DateFormat fmt = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
//	  String pattern       = ((SimpleDateFormat)fmt).toPattern();
	  String localPattern  = ((SimpleDateFormat)fmt).toLocalizedPattern();
//	  DateFormat formatter = new SimpleDateFormat(localPattern, loc); 
//	  return (Date) formatter.parse(dateStr);
	  return parseDate(dateStr, localPattern);
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
  @Deprecated
  public static String getStr1(Date date) throws Exception
  {
    return getStr(date, Helper.DateFormat.DIN.getPattern());
  }
  
  /* dto. */
  @Deprecated
  public static String getStr1(Day day) throws Exception
  {
    return getStr(day.getDate(), Helper.DateFormat.DIN.getPattern());
  }
  
  /**
   * Gibt ein Datum im ISO-Datei-Format aus.
   * @param dateStr
   * @return
   */
  @Deprecated
  public static String getStr2(Date date) throws Exception
  {
    return getStr(date, Helper.DateFormat.ISO.getPattern());
  }
  
  /* dto. */
  @Deprecated
  public static String getStr2(Day day) throws Exception
  {
    return getStr(day.getDate(), Helper.DateFormat.ISO.getPattern());
  }
  
  /**
   * Gibt ein Datum im Format wie angegeben aus.
 * @param date 
 * @param dateFmt 
   * @param dateStr
   * @return
 * @throws Exception 
   */
  public static String getStr(Date date, Helper.DateFormat dateFmt) throws Exception
  {
    return getStr(date, dateFmt.getPattern()); 
  }
  
  public static String getStr(Date date, Locale loc) throws Exception
  {
    // https://stackoverflow.com/questions/4594519/how-do-i-get-localized-date-pattern-string
    DateFormat fmt = DateFormat.getDateInstance(DateFormat.SHORT, loc);
    // String pattern       = ((SimpleDateFormat) fmt).toPattern();
    String pattern = ((SimpleDateFormat) fmt).toLocalizedPattern();
    return getStr(date, pattern); 
  }
  
  public static String getStr(Date date, String pattern) throws Exception
  {
    DateFormat formatter = new SimpleDateFormat(pattern); 
    return formatter.format(date);
  }
  
  public static String getStr(Day day, String pattern) throws Exception
  {
    return getStr(day.getDate(), pattern);
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
    return parseDate(DATE_UNSET_STR);
  }
}
