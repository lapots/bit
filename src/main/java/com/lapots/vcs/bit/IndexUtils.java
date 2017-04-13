package com.lapots.vcs.bit;

import com.lapots.vcs.bit.model.Index;

public class IndexUtils {

    public static void listIndex(Index root) {
        System.out.println(root.getName());
        if (root.hasChildren()) {
            root.getChildren().forEach(IndexUtils::listIndex);
        } else {
            System.out.println(root.getName());
        }
    }

}
