package com.lapots.vcs.bit.core.command.impl;

import com.lapots.vcs.bit.core.command.api.ICommandProcessor;
import com.lapots.vcs.bit.core.domain.Index;
import com.lapots.vcs.bit.utils.FileUtils;
import com.lapots.vcs.bit.utils.IndexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * => bit status
 */
public class StatusCommandProcessor implements ICommandProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusCommandProcessor.class);

    @Override
    public void processCommand(String... args) {
        LOGGER.info("Attempt to get [status]!");

        // get current filesystem index as set of files
        Set<String> newIndexFiles = listFileIndex(IndexUtils.buildCurrentIndex());
        // get committed index as set of files
        Index oldIndex = IndexUtils.readIndex();
        if (!oldIndex.isCommited()) {
            LOGGER.info("Index is not committed!");
            displayFileIndex("added", listMemoryIndex(oldIndex));
            return;
        }
        Set<String> oldIndexFiles = listMemoryIndex(IndexUtils.readIndex());

        Map<String, Set<String>> statusMap = buildComparison(oldIndexFiles, newIndexFiles);
        Set<String> keys = statusMap.keySet();
        for (String key : keys) {
            displayFileIndex(key, statusMap.get(key));
        }
    }

    private void displayFileIndex(String key, Set<String> files) {
        String currentPath = FileUtils.currentPath();
        System.out.println("[" + key + "] =>");
        // TODO: sort set
        for (String file : files) {
            file = FileUtils.relativize(file, currentPath);
            System.out.println(file);
        }
    }

    private Set<String> listFileIndex(Index index) {
        Set<String> fileList = new HashSet<>();
        File[] files = new File(index.getName()).listFiles();
        FileUtils.listDirectoryResources(files, fileList);
        return fileList;
    }

    private Set<String> listMemoryIndex(Index index) {
        Set<String> fileList = new HashSet<>();
        IndexUtils.listIndexResources(index, fileList);
        return fileList;
    }

    private Map<String, Set<String>> buildComparison(Set<String> oldIndex,
                                                     Set<String> newIndex) {
        Map<String, Set<String>> differenceTypeMap = new HashMap<>();

        // stored index does not have files while new one - does
        if (oldIndex.size() == 0 && oldIndex.size() != 0) {
            differenceTypeMap.put("deleted", oldIndex);
        } else /* stored index has files while new one - does not */ if
                (newIndex.size() != 0 && oldIndex.size() == 0) {
            differenceTypeMap.put("added", newIndex);
        } else {
            // get all files that was deleted
            // deleted file -> no file found in new index
            Set<String> oldIndexDelete = new HashSet<>(oldIndex);
            oldIndexDelete.removeAll(newIndex);
            differenceTypeMap.put("deleted", oldIndexDelete);

            // get all files that were added
            // added file -> no find found in old index
            Set<String> newIndexAdded = new HashSet<>(newIndex);
            newIndexAdded.removeAll(oldIndex);
            differenceTypeMap.put("added", newIndexAdded);
        }

        return differenceTypeMap;
    }
}
