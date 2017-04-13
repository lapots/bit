package com.lapots.vcs.bit;

import com.lapots.vcs.bit.cmd.ArriveCommandProcessor;
import com.lapots.vcs.bit.cmd.FocusCommandProcessor;
import com.lapots.vcs.bit.cmd.ICommandProcessor;
import com.lapots.vcs.bit.cmd.InitCommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class CommandUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandUtils.class);
    private static Map<String, ICommandProcessor> processors;
    static {
        processors = new HashMap<>();
        processors.put("init", new InitCommandProcessor());
        processors.put("focus", new FocusCommandProcessor());
        processors.put("arrive", new ArriveCommandProcessor());
    }

    public static void processCommand(String... args) {
        if (args.length == 0) {
            LOGGER.info("No command specified.");
        } else if (args.length == 1) {
            ICommandProcessor processor = processors.get(args[0]);
            if (null != processor) {
                processor.processCommand(args);
            } else {
                LOGGER.info("No command [{}] found!", args[0]);
            }
        }
    }

}
