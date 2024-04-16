package xyz.schnorxoborx.base.basetypes;

import java.time.LocalDate;
import java.util.Date;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import xyz.schnorxoborx.base.dateutils.Day;

public class Year implements Comparable<Year>
{
  // Logger
  private static final Logger LOGGER = LoggerFactory.getLogger(Year.class);
  
  // ::MAGIC
  public static int MIN_YEAR = -9999; // NOT final
  public static int MAX_YEAR = 9999;  // NOT final
  public static final int UNSET_VALUE = -99999;

  // -----------------------------------------------------------------

  public int year;
  
  // -----------------------------------------------------------------

  public Year()
  {
    init();
  }

  public Year(int value) throws InvalidYearException
  {
    init();
    set(value);
  }

  public Year(java.time.Year value) throws InvalidYearException
  {
    init();
    set(value.getValue());
  }

  public Year(Year value) throws InvalidYearException
  {
    init();
    set(value);
  }

  public Year(Day value) throws InvalidYearException
  {
    init();
    set(value);
  }

  public Year(LocalDate value) throws InvalidYearException
  {
    init();
    set(value);
  }

  @Deprecated
  public Year(Date value) throws InvalidYearException
  {
    init();
    set(value);
  }

  // -----------------------------------------------------------------
  
  protected void init()
  {
    year = UNSET_VALUE;
  }

  public void reset()
  {
    year = UNSET_VALUE;
  }

  // -----------------------------------------------------------------
  
  public boolean isSet()
  {
    if ( year == Year.UNSET_VALUE )
      return false;
    else
      return true;
  }

  public int get()
  {
    return year;
  }
  
  public java.time.Year getStdYear()
  {
    return java.time.Year.of(year);
  }
  
  // -----------------------------------------------------------------
  
  public void set(int value) throws InvalidYearException
  {
    if ( value < MIN_YEAR ||
         value > MAX_YEAR )
    {
      LOGGER.error("Invalid year: " + value);
      throw new InvalidYearException();
    }
      
    year = value;
  }
  
  public void set(java.time.Year value) throws InvalidYearException
  {
    set(value.getValue());
  }
  
  public void set(Year value) throws InvalidYearException
  {
    set(value.get());
  }
  
  public void set(Day value) throws InvalidYearException
  {
    set(value.getYear());
  }
  
  public void set(LocalDate value) throws InvalidYearException
  {
    set(value.getYear());
  }

  @Deprecated
  public void set(Date value) throws InvalidYearException
  {
    set(value.getYear());
  }
  
  // -----------------------------------------------------------------
  
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + year;
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Year other = (Year) obj;
    if (year != other.year)
      return false;
    return true;
  }

  public boolean equals(int value) throws InvalidYearException
  {
    return equals(new Year(value));
  }
  
  public boolean equals(java.time.Year value) throws InvalidYearException
  {
    return equals(new Year(value));
  }
  
  public boolean equals(Day value) throws InvalidYearException
  {
    return equals(value.getYear());
  }
  
  public boolean equals(LocalDate value) throws InvalidYearException
  {
    return equals(value.getYear());
  }
  
  @Deprecated
  public boolean equals(Date value) throws InvalidYearException
  {
    return equals(value.getYear());
  }
  
  @Override
  public int compareTo(Year o)
  {
    return (get() - o.get());
  }

  @Override
  public String toString()
  {
    return "" + year;
  }
}
