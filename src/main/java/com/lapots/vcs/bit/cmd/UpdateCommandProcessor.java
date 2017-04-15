package com.lapots.vcs.bit.cmd;

import com.lapots.vcs.bit.CommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateCommandProcessor implements ICommandProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCommandProcessor.class);

    @Override
    public void processCommand(String... args) {
        LOGGER.debug("Attempt to [update] index!");
        CommandUtils.processCommand("init");
    }
}
