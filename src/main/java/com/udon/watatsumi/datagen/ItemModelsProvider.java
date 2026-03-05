package com.udon.watatsumi.datagen;

import com.udon.watatsumi.Watatsumi;
import com.udon.watatsumi.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelsProvider extends ItemModelProvider {

    public ItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Watatsumi.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.TEST_ITEM.get());
        basicItem(ModItems.SALT.get());
        basicItem(ModItems.SEAWATER_BUCKET.get());
        basicItem(ModItems.TAMATEBAKO.get());
        basicItem(ModItems.WOODEN_TUB.get());
    }
}
