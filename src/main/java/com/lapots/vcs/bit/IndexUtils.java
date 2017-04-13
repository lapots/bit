package com.lapots.vcs.bit;

import com.lapots.vcs.bit.model.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexUtils.class);

    public static void listIndex(Index root) {
        if (root.hasChildren()) {
            LOGGER.info("Node [{}] has children to iterate.", root.getName());
            root.getChildren().forEach(IndexUtils::listIndex);
        } else {
            LOGGER.info("Node [{}].", root.getName());
        }
    }

}
