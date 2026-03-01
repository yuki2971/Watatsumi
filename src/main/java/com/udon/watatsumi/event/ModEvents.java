package com.udon.watatsumi.event;

import com.udon.watatsumi.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class ModEvents {

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {

        Level level = event.getLevel();
        if (level.isClientSide()) return;

        // 空バケツのみ対象
        if (!event.getItemStack().is(Items.BUCKET)) return;

        Player player = event.getEntity();

        // バニラと同じRayTrace
        BlockHitResult hit = BucketItem.getPlayerPOVHitResult(
                level,
                player,
                ClipContext.Fluid.SOURCE_ONLY
        );

        if (hit.getType() != HitResult.Type.BLOCK) return;

        BlockPos targetPos = hit.getBlockPos();

        // 水源のみ対象
        if (!level.getFluidState(targetPos).isSource()) return;

        // 海バイオームでなければ通常処理に任せる
        if (!level.getBiome(targetPos).is(BiomeTags.IS_OCEAN)) return;

        // ===== 海水取得処理 =====

        // バニラ処理停止
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        // ===== 水除去処理（安全版） =====

        BlockState state = level.getBlockState(targetPos);

        // waterlogged ブロックの場合は水だけ抜く
        if (state.hasProperty(BlockStateProperties.WATERLOGGED)
                && state.getValue(BlockStateProperties.WATERLOGGED)) {

            level.setBlock(
                    targetPos,
                    state.setValue(BlockStateProperties.WATERLOGGED, false),
                    11
            );

        } else {
            // 純粋な水ブロックのみ削除
            level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 11);
        }

        // 海水バケツ付与
        player.setItemInHand(
                event.getHand(),
                new ItemStack(ModItems.SEAWATER_BUCKET.get())
        );
    }
}