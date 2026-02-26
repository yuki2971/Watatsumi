package com.udon.watatsumi.event;

import com.udon.watatsumi.registry.ModItems;

import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;

import net.neoforged.bus.api.SubscribeEvent;

public class ModEvents {

    @SubscribeEvent
    public void onPlayerTick(EntityTickEvent.Post event) {

        if (!(event.getEntity() instanceof Player player)) return;

        Level level = player.level();
        if (level.isClientSide()) return;

        ItemStack main = player.getMainHandItem();

        if (!main.isEmpty() && main.getItem() == Items.WATER_BUCKET) {

            if (level.getBiome(player.blockPosition()).is(BiomeTags.IS_OCEAN)) {

                player.setItemInHand(
                        InteractionHand.MAIN_HAND,
                        new ItemStack(ModItems.SEAWATER_BUCKET.get())
                );
            }
        }
    }
}