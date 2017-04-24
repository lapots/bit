package com.lapots.vcs.bit.core.command.api;

/**
 * Interface for Bit command.
 */
public interface ICommandProcessor {

    /**
     * Do command processing
     * @param args command input parameters
     */
    void processCommand(String... args);

}
