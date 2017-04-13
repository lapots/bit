package com.lapots.vcs.bit.model;

// the only way to operate with index
public class IndexWrapper {

    private static Index index;

    public static Index createRootIndex(String bitRoot) {
        index = new Index(null, bitRoot);
        return index;
    }

    public static Index getIndex() {
        return index;
    }
}
