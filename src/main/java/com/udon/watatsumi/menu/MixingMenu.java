package com.udon.watatsumi.menu;

import com.udon.watatsumi.registry.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.IContainerFactory;

public class MixingMenu extends AbstractContainerMenu {

    private final Container container;

    // サーバー側から開く時
    public MixingMenu(int id, Inventory playerInventory, Container container) {
        super(ModMenus.MIXING.get(), id);
        this.container = container;

        // スロット0: 土
        addSlot(new Slot(container, 0, 56, 35));
        // スロット1: 水バケツ
        addSlot(new Slot(container, 1, 80, 35));
        // スロット2: 結果（取り出しのみ）
        addSlot(new Slot(container, 2, 116, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // プレイヤーインベントリ
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        // ホットバー
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    // クライアント側から開く時
    public MixingMenu(int id, Inventory playerInventory, net.minecraft.network.FriendlyByteBuf buf) {
        this(id, playerInventory, new SimpleContainer(3));
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}