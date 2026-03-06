package com.udon.watatsumi.item;

import com.udon.watatsumi.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.ClipContext;

public class OceanBucketItem extends Item {

    public OceanBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        HitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (!(hit instanceof BlockHitResult blockHit)) {
            return InteractionResultHolder.pass(stack);
        }

        BlockPos pos = blockHit.getBlockPos();
        FluidState fluid = level.getFluidState(pos);

        if (!fluid.is(FluidTags.WATER) || !fluid.isSource()) {
            return InteractionResultHolder.pass(stack);
        }

        if (!level.getBiome(pos).is(BiomeTags.IS_OCEAN)) {
            return InteractionResultHolder.pass(stack);
        }

        if (!level.isClientSide) {

            BlockState state = level.getBlockState(pos);

            // waterloggedブロック対応
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

            // 音再生
            level.playSound(
                    null,
                    pos,
                    SoundEvents.BUCKET_FILL,
                    SoundSource.BLOCKS,
                    1.0F,
                    1.0F
            );

            ItemStack result = new ItemStack(ModItems.SEAWATER_BUCKET.get());

            return InteractionResultHolder.sidedSuccess(
                    ItemUtils.createFilledResult(stack, player, result),
                    level.isClientSide
            );
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}