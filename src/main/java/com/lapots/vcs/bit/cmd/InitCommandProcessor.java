package com.lapots.vcs.bit.cmd;

import com.lapots.vcs.bit.BitUtils;
import com.lapots.vcs.bit.model.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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

        File[] files = new File(bitPath).listFiles();
        iterateFiles(files, currentIndex);

        BitUtils.writeIndex(currentIndex);
        return null;
    }

    private void iterateFiles(File[] files, Index parent) {
        for (File file : files) {
            if (file.isDirectory()) {
                LOGGER.info("Entering directory: [{}].", file);
                Index index = new Index(parent, file.toString());
                parent.addChild(index);
                iterateFiles(file.listFiles(), index);
            } else {
                LOGGER.info("Adding child [{}].", file.toString());
                parent.addChild(new Index(parent, file.toString()));
            }
        }
    }
}
