package xyz.schnorxoborx.base.basetypes;

import java.time.LocalDate;
import java.time.Month;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.schnorxoborx.base.cmdlinetools.CommandLineTool;

public class Quarter implements Comparable<Quarter>
{
  // Logger
  private static final Logger LOGGER = LoggerFactory.getLogger(Quarter.class);
  
  // ::MAGIC
  public static final int MIN_QUARTER = 1; // YES, final (as opposed to year)
  public static final int MAX_QUARTER = 4; // YES, final (as opposed to year)
  public static final int UNSET_VALUE = -99999;

  // -----------------------------------------------------------------

  public int quarter;
  
  // -----------------------------------------------------------------

  public Quarter()
  {
    init();
  }

  public Quarter(int value) throws InvalidQuarterException
  {
    init();
    set(value);
  }

  public Quarter(Quarter value) throws InvalidQuarterException
  {
    init();
    set(value);
  }

  public Quarter(LocalDate value) throws InvalidQuarterException
  {
    init();
    set(value);
  }

  // -----------------------------------------------------------------
  
  protected void init()
  {
    quarter = UNSET_VALUE;
  }

  public void reset()
  {
    quarter = UNSET_VALUE;
  }

  // -----------------------------------------------------------------
  
  public int get()
  {
    return quarter;
  }
  
  // -----------------------------------------------------------------
  
  public void set(int value) throws InvalidQuarterException
  {
    if ( value < MIN_QUARTER ||
         value > MAX_QUARTER )
    {
      LOGGER.error("Invalid quarter: " + value);
      throw new InvalidQuarterException();
    }
      
    quarter = value;
  }
  
  public void set(Quarter value) throws InvalidQuarterException
  {
    set(value.get());
  }
  
  public void set(LocalDate value) throws InvalidQuarterException
  {
    set(monthToQuarter(value.getMonth()).get());
  }
  
  // -----------------------------------------------------------------
  
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + quarter;
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
    Quarter other = (Quarter) obj;
    if (quarter != other.quarter)
      return false;
    return true;
  }

  public boolean equals(int value) throws InvalidQuarterException
  {
    return equals(new Quarter(value));
  }
  
  public boolean equals(LocalDate value) throws InvalidQuarterException
  {
    return equals(monthToQuarter(value.getMonth()));
  }
  
  @Override
  public int compareTo(Quarter o)
  {
    return (get() - o.get());
  }

  @Override
  public String toString()
  {
    return "" + quarter;
  }
  
  // -----------------------------------------------------------------
  
  public static Quarter monthToQuarter(java.time.Month month) throws InvalidQuarterException
  {
    if ( month == Month.JANUARY ||
         month == Month.FEBRUARY ||
         month == Month.MARCH )
    {
      return new Quarter(1);
    }
    else if ( month == Month.APRIL ||
              month == Month.MAY ||
              month == Month.JUNE )
    {
      return new Quarter(2);
    }
    else if ( month == Month.JULY ||
              month == Month.AUGUST ||
              month == Month.SEPTEMBER )
    {
      return new Quarter(3);
    }
    else if ( month == Month.OCTOBER ||
              month == Month.NOVEMBER ||
              month == Month.DECEMBER )
    {
      return new Quarter(4);
    }
    
    return null; // compiler happy
  }
}
