package com.udon.watatsumi;

import com.udon.watatsumi.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import com.udon.watatsumi.event.ModEvents;

@Mod(Watatsumi.MOD_ID)
public class Watatsumi {

    public static final String MOD_ID = "watatsumi";

    public Watatsumi(IEventBus modEventBus, ModContainer modContainer) {

        // アイテム登録
        ModItems.register(modEventBus);

        // 🌊 イベント登録（これが重要）
        NeoForge.EVENT_BUS.register(new ModEvents());
    }
}
