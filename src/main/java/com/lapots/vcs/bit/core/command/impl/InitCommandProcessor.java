package com.lapots.vcs.bit.core.command.impl;

import com.lapots.vcs.bit.core.command.api.ICommandProcessor;
import com.lapots.vcs.bit.utils.IndexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * => bit init
 */
public class InitCommandProcessor implements ICommandProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitCommandProcessor.class);

    @Override
    public void processCommand(String ... args) {
        LOGGER.info("Attempt to [init] index!");
        IndexUtils.instantiateIndex(false);
    }

}
