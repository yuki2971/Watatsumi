package com.udon.watatsumi.event;

import net.neoforged.neoforge.common.NeoForge;

public class ModEvents {

    public ModEvents() {

        NeoForge.EVENT_BUS.register(new SeawaterCollectionEvent());

    }

}