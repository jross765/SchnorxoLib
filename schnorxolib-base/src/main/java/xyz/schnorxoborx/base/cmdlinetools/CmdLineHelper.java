package xyz.schnorxoborx.base.cmdlinetools;

import java.time.LocalDate;

import org.apache.commons.cli.CommandLine;

import xyz.schnorxoborx.base.dateutils.DateHelpers;
import xyz.schnorxoborx.base.dateutils.LocalDateHelpers;

public class CmdLineHelper
{

	public static Helper.DateFormat getDateFormat(CommandLine cmdLine, String argName)
			throws InvalidCommandLineArgsException
	{
		Helper.DateFormat dateFormat;

		if ( cmdLine.hasOption( argName ) )
		{
			try
			{
				dateFormat = Helper.DateFormat.valueOf( cmdLine.getOptionValue( argName ) );
			}
			catch ( Exception exc )
			{
				System.err.println( "Error: Could not parse <" + argName + ">" );
				throw new InvalidCommandLineArgsException();
			}
		}
		else
		{
			dateFormat = Helper.DateFormat.ISO_8601;
		}

		return dateFormat;
	}

	public static Helper.DateFormat getDateFormat(String arg, String argName) throws InvalidCommandLineArgsException
	{
		Helper.DateFormat dateFormat;

		if ( arg != null )
		{
			try
			{
				dateFormat = Helper.DateFormat.valueOf( arg );
			}
			catch ( Exception exc )
			{
				System.err.println( "Error: Could not parse <" + argName + ">" );
				throw new InvalidCommandLineArgsException();
			}
		}
		else
		{
			dateFormat = Helper.DateFormat.ISO_8601;
		}

		return dateFormat;
	}

	// ------------------------------

	public static LocalDate getDate(CommandLine cmdLine, String argName, Helper.DateFormat dateFormat)
			throws InvalidCommandLineArgsException
	{
		LocalDate datum = LocalDate.now();

		try
		{
			if ( dateFormat == Helper.DateFormat.ISO_8601 )
			{
				datum = LocalDateHelpers.parseLocalDate( cmdLine.getOptionValue( argName ), DateHelpers.DATE_FORMAT_ISO );
			}
			else if ( dateFormat == Helper.DateFormat.DE )
			{
				datum = LocalDateHelpers.parseLocalDate( cmdLine.getOptionValue( argName ) );
			}
		}
		catch ( Exception exc )
		{
			System.err.println( "Error: Could not parse <" + argName + ">" );
			throw new InvalidCommandLineArgsException();
		}

		return datum;
	}

	public static LocalDate getDate(String arg, String argName, Helper.DateFormat dateFormat)
			throws InvalidCommandLineArgsException
	{
		LocalDate datum = LocalDate.now();

		try
		{
			if ( dateFormat == Helper.DateFormat.ISO_8601 )
				datum = LocalDateHelpers.parseLocalDate( arg, DateHelpers.DATE_FORMAT_ISO );
			else if ( dateFormat == Helper.DateFormat.DE )
				datum = LocalDateHelpers.parseLocalDate( arg );
		}
		catch ( Exception exc )
		{
			System.err.println( "Error: Could not parse <" + argName + ">" );
			throw new InvalidCommandLineArgsException();
		}

		return datum;
	}

}
