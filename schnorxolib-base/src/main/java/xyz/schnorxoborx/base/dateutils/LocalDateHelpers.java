package xyz.schnorxoborx.base.dateutils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import xyz.schnorxoborx.base.basetypes.InvalidQuarterException;
import xyz.schnorxoborx.base.basetypes.Quarter;

public class LocalDateHelpers
{
  public final static String DATE_UNSET = "01.01.1900"; // ::MAGIC

  // -----------------------------------------------------------------

  // https://beginnersbook.com/2017/10/java-convert-localdate-to-date/
  public static Date toDate(LocalDate localDate) 
  {
    // default time zone
    ZoneId defaultZoneId = ZoneId.systemDefault();
    
    //local date + atStartOfDay() + default time zone + toInstant() = Date
    Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    
    return date;
  }

  // https://www.baeldung.com/java-date-to-localdate-and-localdatetime
  public static LocalDate toLocalDate(java.util.Date date) 
  {
    // default time zone
    ZoneId defaultZoneId = ZoneId.systemDefault();
    
    LocalDateTime temp = LocalDateTime.ofInstant(date.toInstant(), defaultZoneId);
    return temp.toLocalDate();
  }
  
  // https://stackoverflow.com/questions/29750861/convert-between-localdate-and-sql-date
  public static LocalDate toLocalDate(java.sql.Date date) 
  {
    return date.toLocalDate(); // sic
  }
  
  public static LocalDate toLocalDate(Day day) 
  {
    return LocalDate.of(day.getYear(), day.getMonthNo(), day.getDayOfMonth());
  }
  
  public static LocalDate copy(LocalDate localDate) 
  {
    return LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
  }
  
  // -----------------------------------------------------------------

  public static String getStr(LocalDate datum, String format) throws Exception
  {
    return DateHelpers.getStr(toDate(datum), format);
  }

  public static String getStr1(LocalDate date) throws Exception
  {
    return DateHelpers.getStr1(toDate(date));
  }

  public static String getStr1(Day date) throws Exception
  {
    return DateHelpers.getStr1(date);
  }

  public static String getStr2(LocalDate date) throws Exception
  {
    return DateHelpers.getStr2(toDate(date));
  }

  // -----------------------------------------------------------------

  public static LocalDate parseLocalDate(String dateStr) throws Exception
  {
    return toLocalDate(DateHelpers.parseDate(dateStr));
  }

  public static LocalDate parseLocalDate(String dateStr, String dateFormat) throws Exception
  {
    return toLocalDate(DateHelpers.parseDate(dateStr, dateFormat));
  }

  // -----------------------------------------------------------------

  public static boolean areEqual(LocalDate date1, LocalDate date2)
  {
    return DateHelpers.areEqual(toDate(date1), toDate(date2));
  }

  // -----------------------------------------------------------------

  public static LocalDate getFirstOfJan()
  {
    return toLocalDate(DateHelpers.getFirstOfJan());
  }

  public static LocalDate getUnset() throws Exception
  {
    return toLocalDate(DateHelpers.getUnset());
  }

  // -----------------------------------------------------------------
  
  public static Quarter getQuarter(LocalDate date) throws InvalidQuarterException
  {
    return Quarter.monthToQuarter(date.getMonth());
  }
}
