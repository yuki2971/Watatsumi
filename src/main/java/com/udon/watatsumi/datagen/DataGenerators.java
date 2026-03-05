package com.udon.watatsumi.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        var generator = event.getGenerator();
        var output = generator.getPackOutput();

        // Recipe
        if (event.includeServer()) {
            generator.addProvider(
                    true,
                    new WatatsumiRecipeProvider(
                            output,
                            event.getLookupProvider()
                    )
            );
        }

        // Item models
        if (event.includeClient()) {
            generator.addProvider(
                    true,
                    new ItemModelsProvider(
                            output,
                            event.getExistingFileHelper()
                    )
            );
        }

        // Global loot modifiers
        if (event.includeServer()) {
            generator.addProvider(
                    true,
                    new WatatsumiGlobalLootModifierProvider(
                            output,
                            event.getLookupProvider()
                    )
            );
        }

        // Block loot tables
        if (event.includeServer()) {
            generator.addProvider(
                    true,
                    new ModLootTableProvider(
                            output,
                            event.getLookupProvider()
                    )
            );
        }
    }
}