package com.lapots.vcs.bit.cmd;

import com.lapots.vcs.bit.BitUtils;
import com.lapots.vcs.bit.IndexUtils;
import com.lapots.vcs.bit.model.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * => bit arrive
 */
public class ArriveCommandProcessor implements ICommandProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArriveCommandProcessor.class);

    @Override
    public void processCommand(String... args) {
        LOGGER.info("Attempt to get new arrivals!");

        Path currentPath = Paths.get("");
        String bitPath = currentPath.toAbsolutePath().toString();
        LOGGER.info("Attempt to build index on: [{}].", bitPath);
        Index currentIndex = new Index(null, bitPath);

        Index freshIndex = BitUtils.buildIndex(currentIndex);
        Index oldIndex = BitUtils.readIndex();
        listDiffererences(freshIndex, oldIndex);
    }

    private void listDiffererences(Index freshIndex, Index oldIndex) {
        // roots are equal by default so no need to check them
        if (!freshIndex.hasChildren() && oldIndex.hasChildren()) {
            // LOGGER.info("Empty index entry.");
        } else if (freshIndex.hasChildren() && !oldIndex.hasChildren()) {
            IndexUtils.listIndex(freshIndex);
        } else {
            // iterate new raw index from file system
            File[] files = new File(freshIndex.getName()).listFiles();
            Set<String> hashSet = new HashSet<>();
            iterateFiles(files, hashSet);

            // iterate old index
            Set<String> oldIndexSet = new HashSet<>();
            iterateIndex(oldIndex, oldIndexSet);

            hashSet.removeAll(oldIndexSet);
            LOGGER.info("Fresh items: [{}].", hashSet);
        }
    }

    private void iterateFiles(File[] files, Set<String> set) {
        for (File file : files) {
            if (file.isDirectory() && !file.getName().startsWith(".")) {
                iterateFiles(file.listFiles(), set);
            } else {
                set.add(file.getName());
            }
        }
    }

    public void iterateIndex(Index root, Set<String> set) {
        if (root.hasChildren()) {
            root.getChildren().forEach(indexChild -> {
                iterateIndex(indexChild, set);
            });
        } else {
            set.add(root.getName());
        }
    }
}
