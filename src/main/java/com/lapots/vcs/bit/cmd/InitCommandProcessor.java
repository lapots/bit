package com.lapots.vcs.bit.cmd;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InitCommandProcessor implements ICommandProcessor<Void> {
    @Override
    public Void processCommand(String ... args) {
        // no arguments for init command

        Path currentPath = Paths.get("");
        String bitPath = currentPath.toAbsolutePath().toString();
        System.out.println("Bit path: " + bitPath);

        Files.walk()

        return null;
    }
}
