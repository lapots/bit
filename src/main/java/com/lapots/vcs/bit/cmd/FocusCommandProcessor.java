package com.lapots.vcs.bit.cmd;

import com.lapots.vcs.bit.BitUtils;
import com.lapots.vcs.bit.IndexUtils;
import com.lapots.vcs.bit.model.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * => bit focus
 */
public class FocusCommandProcessor implements ICommandProcessor<Void> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FocusCommandProcessor.class);

    @Override
    public Void processCommand(String ... args) {
        LOGGER.info("Attempt to process [focus] command.");

        Index bitIndex = BitUtils.readIndex();
        IndexUtils.listIndex(bitIndex);

        return null;
    }
}
