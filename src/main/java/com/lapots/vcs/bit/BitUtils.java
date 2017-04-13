package com.lapots.vcs.bit;

import com.lapots.vcs.bit.model.Index;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
}
