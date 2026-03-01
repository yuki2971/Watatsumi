package com.udon.watatsumi.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import com.udon.watatsumi.registry.ModItems;

public class WatatsumiRecipeProvider extends RecipeProvider {

    public WatatsumiRecipeProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries
    ) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(
            RecipeOutput recipeOutput,
            HolderLookup.Provider holderLookup
    ) {
        ShapelessRecipeBuilder.shapeless(
                        RecipeCategory.MISC,
                        ModItems.SALT.get()
                )
                .requires(ModItems.SEAWATER_BUCKET.get())
                .unlockedBy("has_seawater_bucket", has(ModItems.SEAWATER_BUCKET.get()))
                .save(recipeOutput);
    }
}

