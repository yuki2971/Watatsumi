package com.udon.watatsumi.datagen;

import com.udon.watatsumi.Watatsumi;
import com.udon.watatsumi.event.MarineLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class WatatsumiGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public WatatsumiGlobalLootModifierProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries
    ) {
        super(output, registries, Watatsumi.MOD_ID);
    }

    @Override
    protected void start() {
        add("marine_loot",
                new MarineLootModifier()
        );
    }
}