package com.udon.watatsumi.item;

import com.udon.watatsumi.block.WoodenTubBlock;
import com.udon.watatsumi.block.state.TubState;
import com.udon.watatsumi.registry.ModBlocks;
import com.udon.watatsumi.registry.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SeaContainerItem extends Item {

    public SeaContainerItem(Properties properties) {
        super(properties);
    }

    /*
     * =========================================================
     * クラフト残留アイテム
     * ---------------------------------------------------------
     * Seawater Bucket をクラフトに使用した場合、
     * Ocean Bucket を返す。
     * =========================================================
     */

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return stack.is(ModItems.SEAWATER_BUCKET.get());
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {

        if (stack.is(ModItems.SEAWATER_BUCKET.get())) {
            return new ItemStack(ModItems.OCEAN_BUCKET.get());
        }

        return super.getCraftingRemainingItem(stack);
    }



    /*
     * =========================================================
     * Wooden Tub Filled の設置処理
     * ---------------------------------------------------------
     * 海水入り木桶を設置した場合、
     * WoodenTubBlock を FILLED 状態で配置する。
     * =========================================================
     */

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();

        ItemStack stack = context.getItemInHand();

        // Wooden Tub Filled 以外は通常処理
        if (!stack.is(ModItems.WOODEN_TUB_FILLED.get())) {
            return super.useOn(context);
        }

        // クライアント側では処理しない
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());

        if (!level.getBlockState(pos).canBeReplaced()) {
            return InteractionResult.FAIL;
        }

        /*
         * =====================================================
         * WoodenTubBlock を FILLED 状態で配置
         * =====================================================
         */

        BlockState state = ModBlocks.WOODEN_TUB.get()
                .defaultBlockState()
                .setValue(WoodenTubBlock.STATE, TubState.FILLED);

        level.setBlock(pos, state, 11);
        // サウンド再生
        level.playSound(
                null,
                pos,
                net.minecraft.sounds.SoundEvents.WOOD_PLACE,
                net.minecraft.sounds.SoundSource.BLOCKS,
                1.0F,
                1.0F
        );

        /*
         * =====================================================
         * アイテム消費
         * =====================================================
         */

        if (player != null && !player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        // クライアント側の仮レスポンス
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
