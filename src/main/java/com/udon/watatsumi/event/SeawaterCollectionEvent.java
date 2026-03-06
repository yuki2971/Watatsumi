package com.udon.watatsumi.event;

import com.udon.watatsumi.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class SeawaterCollectionEvent {

    // =========================================================
    // Ocean Bucket : 海水バケツ取得処理
    // ---------------------------------------------------------
    // Ocean Bucket を持った状態で海の水源を右クリックすると
    // Seawater Bucket に変換する。
    //
    // BlockItemではないため RightClickItem イベントで処理する。
    // =========================================================
    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {

        Level level = event.getLevel();

        // クライアント側では処理しない（同期バグ防止）
        if (level.isClientSide()) return;

        // プレイヤーが持っているアイテム
        ItemStack stack = event.getItemStack();

        // Ocean Bucket 以外はこのイベントでは処理しない
        if (!stack.is(ModItems.OCEAN_BUCKET.get())) return;

        Player player = event.getEntity();

        // =====================================================
        // プレイヤーの視線方向のブロックを取得
        // バニラのバケツと同じ RayTrace 処理を使用
        // =====================================================
        BlockHitResult hit = BucketItem.getPlayerPOVHitResult(
                level,
                player,
                ClipContext.Fluid.SOURCE_ONLY
        );

        // ブロックに当たっていない場合は処理しない
        if (hit.getType() != HitResult.Type.BLOCK) return;

        BlockPos pos = hit.getBlockPos();

        // 水源ブロックでない場合は処理しない
        if (!level.getFluidState(pos).isSource()) return;

        // 海バイオームでない場合は通常の水扱い
        if (!level.getBiome(pos).is(BiomeTags.IS_OCEAN)) return;

        // =====================================================
        // バニラの処理をキャンセル
        // これをしないとバケツ処理が二重に走る
        // =====================================================
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        // 水源ブロック削除
        removeWater(level, pos);

        // Ocean Bucket → Seawater Bucket に変換
        player.setItemInHand(
                event.getHand(),
                new ItemStack(ModItems.SEAWATER_BUCKET.get())
        );
    }


    // =========================================================
    // Wooden Tub : 海水入り木桶取得処理
    // ---------------------------------------------------------
    // 木桶は BlockItem のため RightClickItem ではなく
    // RightClickBlock を使う。
    //
    // ここでキャンセルしないと
    // 木桶が先に設置されてしまう。
    // =========================================================
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {

        Level level = event.getLevel();

        // サーバー側のみ処理
        if (level.isClientSide()) return;

        // 手に持っているアイテム
        ItemStack stack = event.getItemStack();

        // 木桶以外は処理しない
        if (!stack.is(ModItems.WOODEN_TUB.get())) return;

        // クリックされたブロック位置
        BlockPos pos = event.getHitVec().getBlockPos();

        // 水源でない場合は処理しない
        if (!level.getFluidState(pos).isSource()) return;

        // 海でない場合は処理しない
        if (!level.getBiome(pos).is(BiomeTags.IS_OCEAN)) return;

        // =====================================================
        // ここでイベントをキャンセル
        // 木桶が設置される処理を止める
        // =====================================================
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        // 水源削除
        removeWater(level, pos);

        Player player = event.getEntity();

        // 木桶 → 海水入り木桶
        player.setItemInHand(
                event.getHand(),
                new ItemStack(ModItems.WOODEN_TUB_FILLED.get())
        );
    }


    // =========================================================
    // 水削除処理（共通メソッド）
    // ---------------------------------------------------------
    // 通常の水ブロックと waterlogged ブロックの両方に対応
    // =========================================================
    private void removeWater(Level level, BlockPos pos) {

        BlockState state = level.getBlockState(pos);

        // waterlogged ブロックの場合（水入り階段など）
        if (state.hasProperty(BlockStateProperties.WATERLOGGED)
                && state.getValue(BlockStateProperties.WATERLOGGED)) {

            level.setBlock(
                    pos,
                    state.setValue(BlockStateProperties.WATERLOGGED, false),
                    11
            );

        } else {

            // 通常の水源ブロック
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
        }
    }
}