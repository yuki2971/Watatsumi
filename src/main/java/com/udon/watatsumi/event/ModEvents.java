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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class ModEvents {

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {

        Level level = event.getLevel();
        if (level.isClientSide()) return;

        // 空バケツのみ対象
        if (!event.getItemStack().is(Items.BUCKET)) return;

        Player player = event.getEntity();

        // バニラと同じRayTraceを使う
        BlockHitResult hit = BucketItem.getPlayerPOVHitResult(
                level,
                player,
                ClipContext.Fluid.SOURCE_ONLY
        );

        if (hit.getType() != HitResult.Type.BLOCK) return;

        BlockPos targetPos = hit.getBlockPos();

        // 水でなければ終了
        if (!level.getFluidState(targetPos).is(Fluids.WATER)) return;

        // 海バイオームでなければ終了
        if (!level.getBiome(targetPos).is(BiomeTags.IS_OCEAN)) return;

        // バニラ処理を止める
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);

        // 海水バケツを渡す
        player.setItemInHand(
                event.getHand(),
                new ItemStack(ModItems.SEAWATER_BUCKET.get())
        );

        // ===== ここから水除去処理 =====

        BlockState state = level.getBlockState(targetPos);

        // waterlogged ブロックの場合
        if (state.hasProperty(BlockStateProperties.WATERLOGGED)
                && state.getValue(BlockStateProperties.WATERLOGGED)) {

            level.setBlock(
                    targetPos,
                    state.setValue(BlockStateProperties.WATERLOGGED, false),
                    11
            );

        } else if (level.getFluidState(targetPos).isSource()) {

            level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 11);
        }
    }
}
