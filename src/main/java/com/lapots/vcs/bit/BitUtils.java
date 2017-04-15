package com.lapots.vcs.bit;

import com.lapots.vcs.bit.model.Index;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

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

    public static String getCurrentPath() {
        Path currentPath = Paths.get("");
        String bitPath = currentPath.toAbsolutePath().toString();
        LOGGER.info("Bit VSC path: [{}].", bitPath);
        return bitPath;
    }

    public static void iterateFolder(File[] files, Set<String> set) {
        for (File file : files) {
            if (file.isDirectory() && !file.getName().startsWith(".")) {
                iterateFolder(file.listFiles(), set);
            } else {
                set.add(file.getAbsolutePath());
            }
        }
    }

    public static void printFileSetAgainstLocal(Set<String> paths) {
        String currentPath = getCurrentPath();

        for (String path : paths) {
            String relativized = path.substring(currentPath.length() + 1);
            System.out.println(relativized);
        }
    }

    public static <T> boolean isEmptyArray(T[] array) {
        return array == null || array.length == 0;
    }
}
