package com.udon.watatsumi.registry;

import com.udon.watatsumi.Watatsumi;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, Watatsumi.MOD_ID);

    // テスト用アイテム
    public static final DeferredHolder<Item, Item> TEST_ITEM =
            ITEMS.register("test_item",
                    () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
