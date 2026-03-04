package com.udon.watatsumi.event;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.udon.watatsumi.registry.ModItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.entity.LivingEntity;
import com.udon.watatsumi.registry.ModTags;

import com.mojang.serialization.MapCodec;

public record MarineLootModifier() implements IGlobalLootModifier {

    public static final MapCodec<MarineLootModifier> CODEC =
            MapCodec.unit(new MarineLootModifier());

    @Override
    public ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot,
                                            LootContext context) {

        var entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);

        if (entity instanceof LivingEntity living &&
                living.getType().is(ModTags.EntityTypes.MARINE)) {

            System.out.println("MarineLootModifier fired (tag check)");

            generatedLoot.add(new ItemStack(ModItems.TAMATEBAKO.get()));
        }

        return generatedLoot;
    }
    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}