package com.udon.watatsumi.registry;

import com.udon.watatsumi.Watatsumi;
import com.udon.watatsumi.item.SeawaterBucketItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import com.udon.watatsumi.registry.ModBlocks;
import net.minecraft.world.item.BlockItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, Watatsumi.MOD_ID);

    // テスト用アイテム
    public static final DeferredHolder<Item, Item> TEST_ITEM =
            ITEMS.register("test_item",
                    () -> new Item(new Item.Properties()));
    //　海水バケツを追加
    public static final DeferredHolder<Item, Item> SEAWATER_BUCKET =
            ITEMS.register("seawater_bucket",
                    () -> new SeawaterBucketItem(
                            new Item.Properties().stacksTo(1)
                    ));
    // 塩
    public static final DeferredHolder<Item, Item> SALT =
            ITEMS.register("salt",
                    () -> new Item(new Item.Properties()));
    // 玉手箱
    public static final DeferredHolder<Item, Item> TAMATEBAKO =
            ITEMS.register("tamatebako",
                    ()  -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    //ブロックアイテム登録
    public static final DeferredHolder<Item, Item> WOODEN_TUB =
            ITEMS.register("wooden_tub",
                    () -> new BlockItem(ModBlocks.WOODEN_TUB.get(),
                            new Item.Properties()));
}
