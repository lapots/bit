package com.lapots.vcs.bit.cmd;

import com.lapots.vcs.bit.BitUtils;
import com.lapots.vcs.bit.model.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class InitCommandProcessor implements ICommandProcessor<Void> {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitCommandProcessor.class);

    @Override
    public Void processCommand(String ... args) {
        LOGGER.info("Attempt to init index!");

        Path currentPath = Paths.get("");
        String bitPath = currentPath.toAbsolutePath().toString();
        LOGGER.info("Attempt to build index on: [{}].", bitPath);

        Index currentIndex = new Index(null, bitPath); // creating new index
        BitUtils.writeIndex(BitUtils.buildIndex(currentIndex));
        return null;
    }

}
