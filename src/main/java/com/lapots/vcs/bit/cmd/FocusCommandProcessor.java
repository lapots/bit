package com.lapots.vcs.bit.cmd;

import com.lapots.vcs.bit.BitUtils;
import com.lapots.vcs.bit.IndexUtils;
import com.lapots.vcs.bit.model.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * => bit focus
 */
public class FocusCommandProcessor implements ICommandProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FocusCommandProcessor.class);

    @Override
    public void processCommand(String ... args) {
        LOGGER.debug("Attempt to process [focus] command.");
        Index oldIndex = BitUtils.readIndex();
        Index freshIndex = new Index(null, BitUtils.getCurrentPath());
        IndexUtils.buildIndex(freshIndex);

        listDifferences(oldIndex, freshIndex);
    }

    private void listDifferences(Index oldIndex, Index freshIndex) {
        if (freshIndex.hasChildren() && !oldIndex.hasChildren()) {
            IndexUtils.listIndex(freshIndex);
        } else if (freshIndex.hasChildren() && oldIndex.hasChildren()) {
            Set<String> freshIndexFiles = new HashSet<>();
            IndexUtils.normalizeIndexFromFile(freshIndex, freshIndexFiles);

            Set<String> oldIndexFiles = new HashSet<>();
            IndexUtils.normalizeIndexFromMemory(oldIndex, oldIndexFiles);

            freshIndexFiles.removeAll(oldIndexFiles);
            LOGGER.info("New files: {}.", freshIndexFiles);
        } else {
            LOGGER.info("Not a [bit] repository!");
        }
    }
}
