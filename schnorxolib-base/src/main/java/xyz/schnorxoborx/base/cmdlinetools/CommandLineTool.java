package xyz.schnorxoborx.base.cmdlinetools;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class CommandLineTool
{
  // Logger
  private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineTool.class);
  
  public    abstract void execute(String args[]) throws CouldNotExecuteException;

  protected abstract void init() throws Exception;
  protected abstract void getConfigSettings(PropertiesConfiguration cfg) throws Exception;
  protected abstract void printUsage();
  protected abstract void parseCommandLineArgs(String[] args) throws InvalidCommandLineArgsException;
}
