package de.stynxyxy.betterminecraft.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        oreSmelting(consumer, List.of(Items.ANDESITE), RecipeCategory.BUILDING_BLOCKS, Items.DIAMOND,100.0f, 200, "diamond_andesite");

         ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.BARRIER)
                 .define('B', Items.DIRT)
                .pattern("BBB")
                .pattern("BBB")
                 .pattern("BBB")
                 .unlockedBy("has_diamond_crafted", inventoryTrigger(ItemPredicate.Builder.item()
                         .of(Items.DIAMOND).build()))
                 .save(consumer);
    }
}
