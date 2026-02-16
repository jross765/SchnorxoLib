package xyz.schnorxoborx.base.cmdlinetools;

public class Helper
{

	// ---------------------------------------------------------------

	public enum Mode
	{
		ID,
		NAME
	}

	public enum AcctListMode
	{
		TYPE,
		NAME,
		ALL
	}

	public enum CmdtySecListSelMode
	{
		TYPE,
		ISIN,
		NAME,
		ALL
	}

	public enum CmdtySecSingleSelMode
	{
		ID,
		ISIN,
		NAME
	}

	// ---------------------------------------------------------------

	public enum DateFormat
	{
		// ::MAGIC
		ISO      ( "yyyy-MM-dd" ), // ISO 8601
		US       ( "MM/dd/yyyy" ), // Most common in the US
		EU       ( "dd/MM/yyyy" ), // Most common in official EU context (English documents)
		DIN      ( "dd.MM.yyyy" ), // Most common in Germany, Austria, Switzerland
		                           // as well as in eastern Europe
		ASIA     ( "yyyy/MM/dd" ); // Most common in China, Japan, Korea...
		
		// For all others: Use Locale, such as: Locale.UK, etc.
		// @see DateHelpers.parseDate(String, Locale)
		// @see LocalDateHelpers.parseLocalDate(String, Locale)

		// ---

		private String pattern = "UNSET";

		// ---

		DateFormat(String pattern)
		{
			if ( pattern == null )
				throw new IllegalArgumentException( "argument <pattern> is null" );

			if ( pattern.trim().length() == 0 )
				throw new IllegalArgumentException( "argument <pattern> is empty" );

			this.pattern = pattern;
		}

		// ---

		public String getPattern()
		{
			return pattern;
		}

		// No typo!
		public static DateFormat valueOff(String pattern)
		{
			if ( pattern == null )
			{
				throw new IllegalStateException( "argument <pattern> is null" );
			}

			if ( pattern.trim().length() == 0 )
			{
				throw new IllegalStateException( "argument <pattern> is empty" );
			}

			for ( DateFormat val : values() )
			{
				if ( val.getPattern().equals( pattern.trim() ) )
				{
					return val;
				}
			}

			return null;
		}
	}

	// ---------------------------------------------------------------

	public enum OutputMode
	{
		HUMAN,
		MACHINE
	}

	// ---------------------------------------------------------------

	public enum SyncVariant
	{
		NEWER,
		OLDER,
		BETWEEN_DATES
	}

	public enum SyncFilter
	{
		ALL,
		EVERY_NTH,
		ONCE_IN_PERIOD
	}

}
