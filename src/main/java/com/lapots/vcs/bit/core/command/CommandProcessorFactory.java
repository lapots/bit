package com.lapots.vcs.bit.core.command;

import com.lapots.vcs.bit.core.command.api.ICommandProcessor;
import com.lapots.vcs.bit.core.command.impl.CommitCommandProcessor;
import com.lapots.vcs.bit.core.command.impl.InitCommandProcessor;
import com.lapots.vcs.bit.core.command.impl.StatusCommandProcessor;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public final class CommandProcessorFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandProcessorFactory.class);

    private static Map<String, ICommandProcessor> processorMap;

    static {
        processorMap = new HashMap<>();
        processorMap.put("init", new InitCommandProcessor());
        processorMap.put("commit", new CommitCommandProcessor());
        processorMap.put("status", new StatusCommandProcessor());
    }

    public static void processCommand(String... args) {
        if (args.length == 0) {
            LOGGER.warn("No command provided!");
            throw new IllegalStateException("No command provided!");
        } else {
            String command = args[0];
            // though so far no parameters supported
            processCommand(command, (String[]) ArrayUtils.subarray(args, 1, args.length));
        }
    }
    public static ICommandProcessor getProcessor(String command, String... args) {
        return processorMap.get(command);
    }

    public static void processCommand(String command, String... args) {
        ICommandProcessor processor = getProcessor(command, args);
        if (null == processor) {
            LOGGER.warn("Command [{}] unsupported!", command);
        } else {
            processor.processCommand(args);
        }
    }
}
