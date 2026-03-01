package com.udon.watatsumi.item;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

public class SeawaterBucketItem extends BucketItem {

    public SeawaterBucketItem(Properties properties) {
        super(Fluids.WATER, properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return new ItemStack(Items.BUCKET);
    }
}
