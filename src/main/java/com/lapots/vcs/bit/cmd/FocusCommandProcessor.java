package com.lapots.vcs.bit.cmd;

import com.lapots.vcs.bit.IndexUtils;
import com.lapots.vcs.bit.model.Index;
import com.lapots.vcs.bit.model.IndexWrapper;

/**
 * => bit focus
 */
public class FocusCommandProcessor implements ICommandProcessor<Void> {
    @Override
    public Void processCommand(String ... args) {
        Index bitIndex = IndexWrapper.getIndex();
        if (null == bitIndex) { return null; }

        IndexUtils.listIndex(bitIndex);
        return null;
    }
}
