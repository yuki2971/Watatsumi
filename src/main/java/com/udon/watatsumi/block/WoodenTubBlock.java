package com.udon.watatsumi.block;

import com.udon.watatsumi.block.state.TubState;
import com.udon.watatsumi.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WoodenTubBlock extends Block {

    public static final EnumProperty<TubState> STATE =
            EnumProperty.create("state", TubState.class);

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0, 2, 0, 2, 12, 16),
            Block.box(14, 2, 0, 16, 12, 16),
            Block.box(2, 2, 0, 14, 12, 2),
            Block.box(2, 2, 14, 14, 12, 16)
    );

    public WoodenTubBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(1.5F)
                .sound(SoundType.WOOD)
                .noOcclusion());

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(STATE, TubState.EMPTY)
        );
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
    }

    /*
     * 蒸発完了処理
     */
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        if (state.getValue(STATE) != TubState.FILLED) {
            return;
        }

        if (level.isRainingAt(pos.above())) {
            return;
        }

        // 塩完成音
        level.playSound(
                null,
                pos,
                SoundEvents.AMETHYST_BLOCK_CHIME,
                SoundSource.BLOCKS,
                0.7F,
                1.2F
        );

        level.setBlock(
                pos,
                state.setValue(STATE, TubState.SALT_READY),
                3
        );
    }

    /*
     * 蒸発パーティクル
     */
    @Override
    public void animateTick(BlockState state,
                            Level level,
                            BlockPos pos,
                            RandomSource random) {

        if (state.getValue(STATE) != TubState.FILLED) {
            return;
        }

        if (random.nextInt(5) != 0) {
            return;
        }

        double x = pos.getX() + 0.3 + random.nextDouble() * 0.4;
        double y = pos.getY() + 0.9;
        double z = pos.getZ() + 0.3 + random.nextDouble() * 0.4;

        level.addParticle(
                ParticleTypes.CLOUD,
                x,
                y,
                z,
                0.0,
                0.02,
                0.0
        );
    }

    /*
     * FILLEDになったら蒸発タイマー開始
     */
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {

        if (!level.isClientSide && state.getValue(STATE) == TubState.FILLED) {

            level.scheduleTick(
                    pos,
                    this,
                    20 * 30
            );
        }
    }

    /*
     * 塩回収
     */
    @Override
    protected InteractionResult useWithoutItem(BlockState state,
                                               Level level,
                                               BlockPos pos,
                                               Player player,
                                               BlockHitResult hit) {

        if (state.getValue(STATE) != TubState.SALT_READY) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {

            ItemStack salt = new ItemStack(ModItems.SALT.get());

            if (!player.addItem(salt)) {
                player.drop(salt, false);
            }

            level.playSound(
                    null,
                    pos,
                    SoundEvents.ITEM_PICKUP,
                    SoundSource.BLOCKS,
                    0.8F,
                    1.0F
            );

            level.setBlock(
                    pos,
                    state.setValue(STATE, TubState.EMPTY),
                    3
            );
        }

        return InteractionResult.SUCCESS;
    }

    /*
     * 海水投入
     */
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack,
                                              BlockState state,
                                              Level level,
                                              BlockPos pos,
                                              Player player,
                                              InteractionHand hand,
                                              BlockHitResult hit) {

        if (state.getValue(STATE) == TubState.EMPTY &&
                stack.is(ModItems.SEAWATER_BUCKET.get())) {

            if (!level.isClientSide) {

                level.setBlock(
                        pos,
                        state.setValue(STATE, TubState.FILLED),
                        3
                );

                player.setItemInHand(
                        hand,
                        new ItemStack(ModItems.OCEAN_BUCKET.get())
                );
            }

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}