package com.udon.watatsumi.event;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.udon.watatsumi.registry.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import com.mojang.serialization.MapCodec;

public record MarineLootModifier() implements IGlobalLootModifier {

    public static final MapCodec<MarineLootModifier> CODEC =
            MapCodec.unit(new MarineLootModifier());

    @Override
    public ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(ModItems.TAMATEBAKO.get()));
        return generatedLoot;
    }
    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}