package com.udon.watatsumi.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;

import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class WoodenTubBlock extends Block {

    // 桶の形状（JSONモデルと同じサイズ）
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(0,0,0,16,2,16),      // 底
            Block.box(0,2,0,2,12,16),      // 西壁
            Block.box(14,2,0,16,12,16),    // 東壁
            Block.box(2,2,0,14,12,2),      // 北壁
            Block.box(2,2,14,14,12,16)     // 南壁
    );

    public WoodenTubBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(1.5F)
                .sound(SoundType.WOOD)
                .noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}