package com.lapots.vcs.bit.utils;

import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility methods that works with files.
 */
public final class FileUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Deserializes object from the file.
     *
     * @param file file
     * @param <T> expected object type
     * @return deserialized object
     */
    public static <T> T deserialize(String file) {
        try {
            return (T) SerializationUtils.deserialize(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            LOGGER.error("Failed to deserialize from file [{}].", file);
            return null;
        }
    }

    /**
     * Serializes object to file.
     *
     * @param obj object to serialize
     * @param file file that should keep serialized object
     * @param <T>  object type to serialize
     */
    public static <T extends Serializable> void serializeToFile(T obj, String file) {
        try {
            byte[] bytes = SerializationUtils.serialize(obj);
            new FileOutputStream(file).write(bytes);
        } catch (IOException e) {
            LOGGER.error("Failed to serialize to file [{}].", file);
        }
    }

    /**
     * Returns path to the working directory.
     * @return working directory path
     */
    public static String currentPath() {
        Path currentPath = Paths.get("");
        String bitPath = currentPath.toAbsolutePath().toString();
        LOGGER.debug("Current file path: [{}].", bitPath);
        return bitPath;
    }

    /**
     * Forms a set of all files that stored in the current directory.
     * @param directoryResources all top level resource of the current directory
     * @param set set that stores resource listings
     */
    public static void listDirectoryResources(File[] directoryResources, Set<String> set) {
        for (File resource: directoryResources) {
            // TODO: add additional filtering
            if (resource.isDirectory()) {
                listDirectoryResources(resource.listFiles(), set);
            } else {
                set.add(resource.getAbsolutePath());
            }
        }
    }

    /**
     * Relativizes set of paths against parent directory.
     * @param paths paths
     * @param parentPath parent directory
     * @return set of relativized paths
     */
    public static Set<String> relativizeAll(Set<String> paths, String parentPath) {
        return paths.stream()
                .map(path -> relativize(path, parentPath))
                .collect(Collectors.toSet());
    }

    /**
     * Relativizes path against parent path.
     * @param currentPath path to relativize
     * @param parentPath parent path
     * @return list of paths
     */
    public static String relativize(String currentPath, String parentPath) {
        return currentPath.substring(parentPath.length() + 1);
    }
}
