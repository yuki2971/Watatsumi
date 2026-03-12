package com.udon.watatsumi;

import com.udon.watatsumi.client.ClientSetup;
import com.udon.watatsumi.registry.ModBlockEntities;
import com.udon.watatsumi.registry.ModBlocks;
import com.udon.watatsumi.registry.ModItems;
import com.udon.watatsumi.registry.ModLootModifiers;
import com.udon.watatsumi.registry.ModMenus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import com.udon.watatsumi.event.ModEvents;

@Mod(Watatsumi.MOD_ID)
public class Watatsumi {

    public static final String MOD_ID = "watatsumi";

    public Watatsumi(IEventBus modEventBus, ModContainer modContainer) {

        // アイテム登録
        ModItems.register(modEventBus);
        // ブロック登録
        ModBlocks.register(modEventBus);
        // BlockEntity登録
        ModBlockEntities.register(modEventBus);
        // Menu登録
        ModMenus.register(modEventBus);

        // Itemのloot管理登録
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);

        // イベント登録
        new ModEvents();

        // Clientの仕様を初期化
        modEventBus.addListener(ClientSetup::onClientSetup);
    }
}