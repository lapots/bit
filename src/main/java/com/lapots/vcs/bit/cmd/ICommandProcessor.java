package com.lapots.vcs.bit.cmd;

public interface ICommandProcessor<T> {

    T processCommand(String... args);

}
