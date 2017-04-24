package com.lapots.vcs.bit.core.command.impl;

import com.lapots.vcs.bit.core.command.api.ICommandProcessor;
import com.lapots.vcs.bit.utils.IndexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * => bit commit
 */
public class CommitCommandProcessor implements ICommandProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommitCommandProcessor.class);

    @Override
    public void processCommand(String... args) {
        LOGGER.info("Attempt to process [commit] command!");
        // TODO: add writing to history
        IndexUtils.instantiateIndex(true);
    }
}
