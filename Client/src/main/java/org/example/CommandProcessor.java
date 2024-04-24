package org.example;

import commands.CommandInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandProcessor {
    private static final Logger rootLogger= LogManager.getRootLogger();
    private final CommandInvoker commandInvoker;
    public CommandProcessor(CommandInvoker commandInvoker){
        this.commandInvoker=commandInvoker;
    }
    public boolean executeCommand(String firstCommandLine) {

        if (!commandInvoker.executeClient(firstCommandLine, System.out)) {
            rootLogger.warn("Команда не была исполнена");
            return false;
        } else {
            return !commandInvoker.getLastCommandContainer().getName().equals("help");
        }
    }

}