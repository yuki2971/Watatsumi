package com.udon.watatsumi;

import com.udon.watatsumi.datagen.ItemModelsProvider;
import com.udon.watatsumi.datagen.WatatsumiGlobalLootModifierProvider;
import com.udon.watatsumi.registry.ModBlocks;
import com.udon.watatsumi.registry.ModItems;
import com.udon.watatsumi.registry.ModLootModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import com.udon.watatsumi.event.ModEvents;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.minecraft.data.PackOutput;
import net.minecraft.data.DataGenerator;
import com.udon.watatsumi.datagen.WatatsumiRecipeProvider;

@Mod(Watatsumi.MOD_ID)
public class Watatsumi {

    public static final String MOD_ID = "watatsumi";

    public Watatsumi(IEventBus modEventBus, ModContainer modContainer) {

        // アイテム登録
        ModItems.register(modEventBus);
        // ブロック登録
        ModBlocks.register(modEventBus);

        // Itemのloot管理登録
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);

        // 🌊 イベント登録（これが重要）
        NeoForge.EVENT_BUS.register(new ModEvents());

        // Datagen登録
        modEventBus.addListener(this::gatherData);
    }
    private void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        if (event.includeServer()) {
            generator.addProvider(
                    true,
                    new WatatsumiRecipeProvider(
                            output,
                            event.getLookupProvider()
                    )
            );
        }
        // Client側（item modelなど）
        if (event.includeClient()) {
            generator.addProvider(
                    true,
                    new ItemModelsProvider(
                            output,
                            event.getExistingFileHelper()
                    )
            );
        }
        // loot用
        event.getGenerator().addProvider(
                event.includeServer(),
                new WatatsumiGlobalLootModifierProvider(
                        event.getGenerator().getPackOutput(),
                        event.getLookupProvider()
                )
        );
    }
}
