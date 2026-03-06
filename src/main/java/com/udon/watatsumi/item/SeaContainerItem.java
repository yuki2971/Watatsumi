package com.udon.watatsumi.item;

import com.udon.watatsumi.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SeaContainerItem extends Item {

    public SeaContainerItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return stack.is(ModItems.SEAWATER_BUCKET.get());
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {

        if (stack.is(ModItems.SEAWATER_BUCKET.get())) {
            return new ItemStack(ModItems.OCEAN_BUCKET.get());
        }

        return ItemStack.EMPTY;
    }
}
