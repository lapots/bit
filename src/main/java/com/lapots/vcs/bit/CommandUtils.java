package com.lapots.vcs.bit;

import com.lapots.vcs.bit.cmd.ICommandProcessor;
import com.lapots.vcs.bit.cmd.InitCommandProcessor;

import java.util.HashMap;
import java.util.Map;

public final class CommandUtils {

    private static Map<String, ICommandProcessor<?>> processors;
    static {
        processors = new HashMap<>();
        processors.put("init", new InitCommandProcessor());
    }

    public static void processCommand(String... args) {
        if (args.length == 0) {
            System.out.println("Bit confused? No command provided...");
        } else if (args.length == 1) {
            ICommandProcessor<?> processor = processors.get(args[0]);
            if (null != processor) {
                processor.processCommand(args);
            } else {
                System.out.println("Bit confused? No parameters specified for ==" + args[0] + "== command");
            }
        }
    }

}
