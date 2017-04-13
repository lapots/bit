package com.lapots.vcs.bit;

import com.lapots.vcs.bit.model.Index;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public final class BitUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BitUtils.class);

    private static final String BIT_FILE = ".bit";

    public static Index readIndex() {
        try {
            return (Index) SerializationUtils.deserialize(new FileInputStream(BIT_FILE));
        } catch (FileNotFoundException e) {
            LOGGER.error("Bit confused? Not a [bit] directory!");
            return null;
        }
    }

    public static void writeIndex(Index index) {
        try {
            byte[] bytes = SerializationUtils.serialize(index);
            new FileOutputStream(BIT_FILE).write(bytes);
        } catch (IOException e) {
            LOGGER.error("Critical! Failed to create index!");
        }
    }

    public static Index buildIndex(Index origin) {
        File[] files = new File(origin.getName()).listFiles();
        iterateFiles(files, origin);
        return origin;
    }

    private static void iterateFiles(File[] files, Index parent) {
        for (File file : files) {
            if (file.isDirectory() && !file.getName().startsWith(".")) {
                LOGGER.info("Entering directory: [{}].", file);
                Index index = new Index(parent, file.toString());
                parent.addChild(index);
                iterateFiles(file.listFiles(), index);
            } else {
                LOGGER.info("Adding child [{}].", file.toString());
                if (!file.getName().equals(BIT_FILE))
                    parent.addChild(new Index(parent, file.toString()));
            }
        }
    }
}
