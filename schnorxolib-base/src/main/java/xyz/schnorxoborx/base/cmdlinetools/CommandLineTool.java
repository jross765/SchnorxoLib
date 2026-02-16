package xyz.schnorxoborx.base.cmdlinetools;

import org.apache.commons.configuration.PropertiesConfiguration;


public abstract class CommandLineTool
{
  public    abstract void execute(String args[]) throws CouldNotExecuteException;

  protected abstract void init() throws Exception;
  protected abstract void getConfigSettings(PropertiesConfiguration cfg) throws Exception;
  protected abstract void printUsage();
  protected abstract void parseCommandLineArgs(String[] args) throws InvalidCommandLineArgsException;
}
