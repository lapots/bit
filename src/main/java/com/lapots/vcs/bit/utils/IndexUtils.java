package com.lapots.vcs.bit.utils;

import com.lapots.vcs.bit.core.domain.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Set;

/**
 * Utility methods that work with index.
 */
public final class IndexUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexUtils.class);
    private static final String BIT_FILE = ".bit";

    /**
     * Froms index for the current directory and write to file.
     * @param commit is commited index or not
     */
    public static void instantiateIndex(boolean commit) {
        Index currentIndex = IndexUtils.buildCurrentIndex();
        currentIndex.setCommited(commit);

        writeIndex(currentIndex);
    }

    /**
     * Forms index for the current directory.
     * @return index instance
     */
    public static Index buildCurrentIndex() {
        return IndexUtils.buildIndex(new Index(FileUtils.currentPath()));
    }

    /**
     * Reads index from the file.
     * @return read index
     */
    public static Index readIndex() {
        Index index = FileUtils.deserialize(BIT_FILE);
        if (null == index) {
            LOGGER.error("Failed to read index!");
            throw new IllegalStateException("Not a BIT directory!");
        }

        return index;
    }

    /**
     * Write index to file.
     * @param index index to write
     */
    public static void writeIndex(Index index) {
        FileUtils.serializeToFile(index, BIT_FILE);
    }

    private static Index buildIndex(Index origin) {
        File[] files = new File(origin.getName()).listFiles();
        createIndexFromFiles(files, origin);
        return origin;
    }

    /**
     * Forms a set of index files.
     * @param root start index root
     * @param set set that stores resource listings
     */
    public static void listIndexResources(Index root, Set<String> set) {
        if (root.hasChildren()) {
            root.getChildren()
                    .forEach(indexChild -> listIndexResources(indexChild, set));
        } else {
            set.add(root.getName());
        }
    }

    private static void createIndexFromFiles(File[] files, Index parent) {
        for (File file : files) {
            if (file.isDirectory()) {
                LOGGER.debug("Entering directory: [{}].", file);
                Index index = new Index(parent, file.toString());
                parent.addChild(index);
                createIndexFromFiles(file.listFiles(), index);
            } else {
                LOGGER.debug("Adding child [{}].", file.toString());
                parent.addChild(new Index(parent, file.toString()));
            }
        }
    }
}
