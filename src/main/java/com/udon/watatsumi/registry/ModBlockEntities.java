package com.udon.watatsumi.registry;

import com.udon.watatsumi.Watatsumi;
import com.udon.watatsumi.block.MixingBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Watatsumi.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MixingBlockEntity>> MIXING =
            BLOCK_ENTITIES.register("mixing",
                    () -> BlockEntityType.Builder
                            .of(MixingBlockEntity::new, ModBlocks.MIXING_BLOCK.get())
                            .build(null)
            );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}