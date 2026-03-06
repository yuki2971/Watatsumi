package com.udon.watatsumi.registry;

import com.udon.watatsumi.Watatsumi;
import com.udon.watatsumi.block.WoodenTubBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, Watatsumi.MOD_ID);

    public static final DeferredHolder<Block, Block> WOODEN_TUB =
            BLOCKS.register("wooden_tub",
                    WoodenTubBlock::new);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}