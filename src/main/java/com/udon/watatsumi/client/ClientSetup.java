package com.udon.watatsumi.client;

import com.udon.watatsumi.registry.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void onClientSetup(FMLClientSetupEvent event) {

        ItemBlockRenderTypes.setRenderLayer(
                ModBlocks.WOODEN_TUB.get(),
                RenderType.translucent()
        );
    }
}