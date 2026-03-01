package com.udon.watatsumi.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModTags {

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> MARINE =
                TagKey.create(
                        Registries.ENTITY_TYPE,
                        ResourceLocation.fromNamespaceAndPath("watatsumi", "marine")
                );
    }
}