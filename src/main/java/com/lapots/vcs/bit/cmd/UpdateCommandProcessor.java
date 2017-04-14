package com.lapots.vcs.bit.cmd;

import com.lapots.vcs.bit.BitUtils;
import com.lapots.vcs.bit.IndexUtils;
import com.lapots.vcs.bit.model.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateCommandProcessor implements ICommandProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCommandProcessor.class);

    @Override
    public void processCommand(String... args) {
        LOGGER.debug("Attempt to [update] index!");
        Index currentIndex = new Index(null, BitUtils.getCurrentPath()); // creating new index
        BitUtils.writeIndex(IndexUtils.buildIndex(currentIndex));
    }
}
