package xyz.schnorxoborx.base.dateutils;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;
import xyz.schnorxoborx.base.basetypes.Quarter;

public class TestLocalDateHelpers
{
  public static void main(String[] args) throws Exception
  {
    junit.textui.TestRunner.run(suite());
  }

  @SuppressWarnings("exports")
public static junit.framework.Test suite() 
  {
    return new JUnit4TestAdapter(TestLocalDateHelpers.class);  
  }

	@Test
	public void test00() throws Exception 
	{
//		  Locale loc = Locale.US;
//		  DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, loc);
//		  System.err.println(df.format( LocalDate.now() ));
//		  System.err.println(loc.toString());
		  // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
		  // DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
//		Locale loc = Locale.forLanguageTag("fr"); // French locale
		Locale loc = Locale.forLanguageTag("de"); // French locale
//		Locale loc = Locale.US;
//		Locale loc = Locale.GERMAN;
		DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(loc);
		System.err.println(dtf);
		  System.err.println(LocalDate.now().format( dtf ));
		  
		  // https://stackoverflow.com/questions/4594519/how-do-i-get-localized-date-pattern-string
		  DateFormat fmt = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
		  String pattern       = ((SimpleDateFormat)fmt).toPattern();
		  String localPattern  = ((SimpleDateFormat)fmt).toLocalizedPattern();
		  System.err.println(pattern);
		  System.err.println(localPattern);
	}
	
  @Test
  public void test01() throws Exception
  {
    LocalDate date = LocalDateHelpers.parseLocalDate("2020-02-01");
    assertEquals(2020, date.getYear());
    assertEquals(Month.FEBRUARY, date.getMonth());
    assertEquals(1, date.getDayOfMonth());
  }

  @Test
  public void test02() throws Exception
  {
    LocalDate date = LocalDateHelpers.parseLocalDate("2020-02-01");
    assertEquals(new Quarter(1), LocalDateHelpers.getQuarter(date));
    
    date = LocalDateHelpers.parseLocalDate("2020-03-31");
    assertEquals(new Quarter(1), LocalDateHelpers.getQuarter(date));
    
    date = LocalDateHelpers.parseLocalDate("2020-04-01");
    assertEquals(new Quarter(2), LocalDateHelpers.getQuarter(date));
    
    date = LocalDateHelpers.parseLocalDate("2020-04-02");
    assertEquals(new Quarter(2), LocalDateHelpers.getQuarter(date));

    date = LocalDateHelpers.parseLocalDate("2020-09-30");
    assertEquals(new Quarter(3), LocalDateHelpers.getQuarter(date));

    date = LocalDateHelpers.parseLocalDate("2020-10-01");
    assertEquals(new Quarter(4), LocalDateHelpers.getQuarter(date));

    date = LocalDateHelpers.parseLocalDate("2020-10-01");
    assertEquals(new Quarter(4), LocalDateHelpers.getQuarter(date));
  }
}

