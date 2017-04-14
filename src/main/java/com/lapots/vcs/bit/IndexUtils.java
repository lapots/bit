package com.lapots.vcs.bit;

import com.lapots.vcs.bit.model.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Set;

public class IndexUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexUtils.class);
    private static final String BIT_FILE = ".bit";
    public static void listIndex(Index root) {
        if (root.hasChildren()) {
            LOGGER.debug("Node [{}] has children to iterate.", root.getName());
            root.getChildren().forEach(IndexUtils::listIndex);
        } else {
            LOGGER.debug("Node [{}].", root.getName());
        }
    }

    public static Set<String> normalizeIndexFromFile(Index index, Set<String> indexFiles) {
        File[] files = new File(index.getName()).listFiles();
        BitUtils.iterateFolder(files, indexFiles);
        return indexFiles;
    }

    public static Set<String> normalizeIndexFromMemory(Index index, Set<String> indexFiles) {
        iterateIndex(index, indexFiles);
        return indexFiles;
    }

    public static Index buildIndex(Index origin) {
        File[] files = new File(origin.getName()).listFiles();
        createIndex(files, origin);
        return origin;
    }

    private static void createIndex(File[] files, Index parent) {
        for (File file : files) {
            if (file.isDirectory() && !file.getName().startsWith(".")) {
                LOGGER.debug("Entering directory: [{}].", file);
                Index index = new Index(parent, file.toString());
                parent.addChild(index);
                createIndex(file.listFiles(), index);
            } else {
                LOGGER.debug("Adding child [{}].", file.toString());
                if (!file.getName().equals(BIT_FILE))
                    parent.addChild(new Index(parent, file.toString()));
            }
        }
    }

    private static void iterateIndex(Index root, Set<String> set) {
        if (root.hasChildren()) {
            root.getChildren().forEach(indexChild -> {
                iterateIndex(indexChild, set);
            });
        } else {
            set.add(root.getName());
        }
    }
}
