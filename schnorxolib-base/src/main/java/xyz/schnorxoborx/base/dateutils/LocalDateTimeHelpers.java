package xyz.schnorxoborx.base.dateutils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeHelpers
{

  // -----------------------------------------------------------------

  public static LocalDateTime toLocalDateTime(LocalDate date) 
  {
    return LocalDateTime.of(date, LocalTime.of(0, 0));
  }
  
  public static LocalDateTime toLocalDateTime(java.util.Date date) 
  {
    LocalDate locDate = LocalDateHelpers.toLocalDate(date);
    LocalTime locTime = LocalTime.of(date.getHours(), date.getMinutes(), date.getSeconds());
    return LocalDateTime.of(locDate, locTime);
  }
  
  // ------------------------------
  
  // https://stackoverflow.com/questions/8992282/convert-localdate-to-localdatetime-or-java-sql-timestamp
  public static LocalDateTime toLocalDateTime(Timestamp ts) 
  {
    return ts.toLocalDateTime(); // sic
  }
  
  // https://stackoverflow.com/questions/8992282/convert-localdate-to-localdatetime-or-java-sql-timestamp
  public static Timestamp toTimestamp(LocalDateTime ldt) 
  {
    return Timestamp.valueOf(ldt); // sic  
  }
  
  // -----------------------------------------------------------------

  public static String getStr(LocalDateTime dateTime, String format) throws Exception
  {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
    return dateTime.format(fmt);
  }

  public static String getStr1(LocalDateTime dateTime) throws Exception
  {
    return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  // -----------------------------------------------------------------

  public static LocalDateTime parseLocalDateTime(String dateTimeStr) throws Exception
  {
    String withoutSuffix = dateTimeStr.replace("+01:00", "");
    withoutSuffix = withoutSuffix.replace("+02:00", "");
    return parseLocalDateTime(withoutSuffix, "yyyy-MM-dd'T'HH:mm:ss");
  }

  public static LocalDateTime parseLocalDateTime(String dateTimeStr, String dateTimeFormat) throws Exception
  {
    DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern(dateTimeFormat);
    // SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat);

    LocalDateTime ldt = LocalDateTime.parse(dateTimeStr, dtf1);
  
    return ldt;
  }
}
