package com.udon.watatsumi;

import com.udon.watatsumi.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(Watatsumi.MOD_ID)
public class Watatsumi {

    public static final String MOD_ID = "watatsumi";

    public Watatsumi(IEventBus modEventBus, ModContainer modContainer) {

        // アイテム登録
        ModItems.register(modEventBus);
    }
}
