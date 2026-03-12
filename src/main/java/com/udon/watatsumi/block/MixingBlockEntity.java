package com.udon.watatsumi.block;

import com.udon.watatsumi.registry.ModBlockEntities;
import com.udon.watatsumi.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import com.udon.watatsumi.menu.MixingMenu;

public class MixingBlockEntity extends BlockEntity implements MenuProvider {

    // スロット0: 土、スロット1: 水バケツ、スロット2: 結果
    private final SimpleContainer inventory = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            MixingBlockEntity.this.setChanged();
            checkRecipe();
        }
    };

    public MixingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MIXING.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.watatsumi.mixing");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new MixingMenu(id, playerInventory, inventory);
    }

    private void checkRecipe() {
        ItemStack slot0 = inventory.getItem(0);
        ItemStack slot1 = inventory.getItem(1);

        ItemStack newResult;
        if (slot0.is(Items.DIRT) && slot1.is(Items.WATER_BUCKET)) {
            newResult = new ItemStack(Items.CLAY_BALL, 4);
        } else {
            newResult = ItemStack.EMPTY;
        }

        // 結果が変わった時だけsetItemを呼ぶ（無限ループ防止）
        ItemStack current = inventory.getItem(2);
        if (!ItemStack.matches(current, newResult)) {
            inventory.setItem(2, newResult);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                tag.put("slot_" + i, stack.save(registries));
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (tag.contains("slot_" + i)) {
                inventory.setItem(i, ItemStack.parse(registries, tag.getCompound("slot_" + i)).orElse(ItemStack.EMPTY));
            }
        }
    }
}