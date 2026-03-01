package com.udon.watatsumi.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.udon.watatsumi.Watatsumi;
import com.udon.watatsumi.event.MarineLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModLootModifiers {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Watatsumi.MOD_ID);

    public static final DeferredHolder<
            MapCodec<? extends IGlobalLootModifier>,
            MapCodec<MarineLootModifier>
            > MARINE_LOOT =
            LOOT_MODIFIERS.register("marine_loot", () -> MarineLootModifier.CODEC);
}