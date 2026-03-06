package com.udon.watatsumi.block.state;

import net.minecraft.util.StringRepresentable;

public enum TubState implements StringRepresentable {

    EMPTY,
    FILLED,
    SALT_READY;

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }
}