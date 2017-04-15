package com.lapots.vcs.bit;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class IgnoreUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IgnoreUtils.class);

    private static final String BITIGNORE_FILE = ".bitignore";

    public static Set<String> getFileFilter() {
        File file = new File(BITIGNORE_FILE);
        if (file.exists()) {
            try (Stream<String> lines = Files.lines(file.toPath())) {
                return lines.filter(line -> line.startsWith("."))
                        .map(line -> line.substring(line.indexOf(".")))
                        .collect(Collectors.toSet());
            } catch (IOException e) {
                LOGGER.error("Failed to process .bitignore file.", e);
            }
        }
        return Collections.emptySet();
    }

    public static Set<String> getFolderFilter() {
        File file = new File(BITIGNORE_FILE);
        if (file.exists()) {
            try (Stream<String> lines = Files.lines(file.toPath())) {
                return lines.map(line -> FilenameUtils.separatorsToSystem(line))
                        .filter(line -> line.startsWith(String.valueOf(File.separatorChar)))
                        .collect(Collectors.toSet());
            } catch (IOException e) {
                LOGGER.error("Failed to process .bitignore file.", e);
            }
        }
        return Collections.emptySet();
    }
}
