package com.udon.watatsumi.item;

import com.udon.watatsumi.registry.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class WoodenTubItem extends BlockItem {

    public WoodenTubItem(net.minecraft.world.level.block.Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (player == null) {
            return super.useOn(context);
        }

        // バケツと同じRayTrace
        BlockHitResult hit = getPlayerPOVHitResult(
                level,
                player,
                ClipContext.Fluid.SOURCE_ONLY
        );

        if (hit.getType() == HitResult.Type.BLOCK) {

            BlockPos waterPos = hit.getBlockPos();
            var fluid = level.getFluidState(waterPos);

            if (fluid.isSource() && fluid.is(FluidTags.WATER)) {

                if (!level.getBiome(waterPos).is(BiomeTags.IS_OCEAN)) {
                    return InteractionResult.FAIL;
                }

                if (!level.isClientSide) {

                    removeWater(level, waterPos);

                    level.playSound(
                            null,
                            waterPos,
                            SoundEvents.BUCKET_FILL,
                            SoundSource.BLOCKS,
                            1.0F,
                            1.0F
                    );

                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    player.setItemInHand(
                            context.getHand(),
                            new ItemStack(ModItems.WOODEN_TUB_FILLED.get())
                    );
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return super.useOn(context);
    }

    private void removeWater(Level level, BlockPos pos) {

        BlockState state = level.getBlockState(pos);

        if (state.hasProperty(BlockStateProperties.WATERLOGGED)
                && state.getValue(BlockStateProperties.WATERLOGGED)) {

            level.setBlock(
                    pos,
                    state.setValue(BlockStateProperties.WATERLOGGED, false),
                    11
            );

        } else {

            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
        }
    }
}