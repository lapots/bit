package com.lapots.vcs.bit.model;

import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Index implements Serializable {
    private Index parent;
    private List<Index> children;

    private String name;

    public Index(Index parent, String name) {
        this.parent = parent;
        children = new LinkedList<>();
        this.name = name;
    }

    public void addChild(Index child) {
        children.add(child);
    }

    public String getName() {
        return name;
    }

    public boolean hasChildren() {
        return CollectionUtils.isNotEmpty(children);
    }

    public List<Index> getChildren() {
        return children;
    }

    public Index getParent() {
        return parent;
    }
}
