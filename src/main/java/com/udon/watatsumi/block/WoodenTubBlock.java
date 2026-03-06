package com.udon.watatsumi.block;

import com.udon.watatsumi.block.state.TubState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WoodenTubBlock extends Block {

    /*
     * BlockState Property
     * -------------------
     * 木桶の状態を管理する。
     *
     * EMPTY      : 空
     * FILLED     : 海水入り
     * SALT_READY : 蒸発して塩が生成された状態
     *
     * EnumProperty にすることで
     * blockstates JSON と連動する。
     */
    public static final EnumProperty<TubState> STATE =
            EnumProperty.create("state", TubState.class);

    /*
     * VoxelShape
     * ----------
     * 木桶の当たり判定。
     * JSONモデルと一致するように作っている。
     *
     * 16x16x16 がブロック1個の座標空間。
     */
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 2, 16),     // 底
            Block.box(0, 2, 0, 2, 12, 16),     // 西側の壁
            Block.box(14, 2, 0, 16, 12, 16),   // 東側の壁
            Block.box(2, 2, 0, 14, 12, 2),     // 北側の壁
            Block.box(2, 2, 14, 14, 12, 16)    // 南側の壁
    );

    public WoodenTubBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(1.5F)      // 木材と同程度の硬さ
                .sound(SoundType.WOOD)
                .noOcclusion());     // 中空モデルのため

        /*
         * デフォルト状態
         * ---------------
         * ブロック設置時の初期状態。
         * 基本は EMPTY。
         */
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(STATE, TubState.EMPTY)
        );
    }

    /*
     * ブロックの見た目の形状
     * -----------------------
     * 当たり判定と選択判定に使用される。
     */
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    /*
     * BlockState 定義
     * ----------------
     * このブロックが持つ Property を登録する場所。
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
    }
}