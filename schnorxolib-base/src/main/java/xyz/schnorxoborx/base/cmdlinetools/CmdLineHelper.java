package xyz.schnorxoborx.base.cmdlinetools;

import java.time.LocalDate;

import org.apache.commons.cli.CommandLine;

import xyz.schnorxoborx.base.basetypes.DateFmtDatePair;
import xyz.schnorxoborx.base.dateutils.LocalDateHelpers;

public class CmdLineHelper
{

	public static DateFmtDatePair getDateComplete(CommandLine cmdLine, 
			String fmtArgName, String dateArgName)
			throws InvalidCommandLineArgsException
	{
		DateFmtDatePair result = new DateFmtDatePair();
		
		result.dateFmt = getDateFormat(cmdLine, fmtArgName);
		result.date    = getDate(cmdLine, dateArgName, result.dateFmt);
		
		return result;
	}
	
	public static DateFmtDatePair getDateComplete(String fmtArg, String fmtArgName,
												  String dateArg, String dateArgName) throws InvalidCommandLineArgsException
	{
		DateFmtDatePair result = new DateFmtDatePair();
		
		result.dateFmt = getDateFormat(fmtArg, fmtArgName);
		result.date    = getDate(dateArg, dateArgName, result.dateFmt);
		
		return result;
	}
	
	// ------------------------------

	public static Helper.DateFormat getDateFormat(CommandLine cmdLine, String argName)
			throws InvalidCommandLineArgsException
	{
		Helper.DateFormat dateFmt;

		if ( cmdLine.hasOption( argName ) )
		{
			try
			{
				dateFmt = Helper.DateFormat.valueOf( cmdLine.getOptionValue(argName) );
			}
			catch ( Exception exc )
			{
				System.err.println( "Error: Could not parse <" + argName + ">" );
				throw new InvalidCommandLineArgsException();
			}
		}
		else
		{
			dateFmt = Helper.DateFormat.ISO;
		}

		return dateFmt;
	}

	public static Helper.DateFormat getDateFormat(String arg, String argName) throws InvalidCommandLineArgsException
	{
		Helper.DateFormat dateFormat;

		if ( arg != null )
		{
			try
			{
				dateFormat = Helper.DateFormat.valueOf(argName);
			}
			catch ( Exception exc )
			{
				System.err.println( "Error: Could not parse <" + argName + ">" );
				throw new InvalidCommandLineArgsException();
			}
		}
		else
		{
			dateFormat = Helper.DateFormat.ISO;
		}

		return dateFormat;
	}

	// ------------------------------

	public static LocalDate getDate(CommandLine cmdLine, String argName, Helper.DateFormat dateFmt)
			throws InvalidCommandLineArgsException
	{
		LocalDate datum = LocalDate.now();

		try
		{
			datum = LocalDateHelpers.parseLocalDate( cmdLine.getOptionValue(argName), dateFmt );
		}
		catch ( Exception exc )
		{
			System.err.println( "Error: Could not parse <" + argName + ">" );
			throw new InvalidCommandLineArgsException();
		}

		return datum;
	}

	public static LocalDate getDate(String arg, String argName, Helper.DateFormat dateFmt)
			throws InvalidCommandLineArgsException
	{
		LocalDate datum = LocalDate.now();

		try
		{
			datum = LocalDateHelpers.parseLocalDate(arg, dateFmt);
		}
		catch ( Exception exc )
		{
			System.err.println( "Error: Could not parse <" + argName + ">" );
			throw new InvalidCommandLineArgsException();
		}

		return datum;
	}

}
