package de.stynxyxy.betterminecraft.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import de.stynxyxy.betterminecraft.ImmersiveEnchatments;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;

public class EnchantmentStationRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;


    private final NonNullList<Ingredient> recipeItems;

    public EnchantmentStationRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        return recipeItems.get(0).test(pContainer.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<EnchantmentStationRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "enchantment_station";
    }

    public static class Serializer implements RecipeSerializer<EnchantmentStationRecipe> {
        private final Logger logger = ImmersiveEnchatments.getLogger();
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ImmersiveEnchatments.MODID, "enchantment_station");

        @Override
        public EnchantmentStationRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            boolean enchantmentBook = GsonHelper.getAsBoolean(pSerializedRecipe, "enchantment");
            ItemStack output;

            if (!enchantmentBook) {
                output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            } else {
                output = new ItemStack(Items.DIAMOND_AXE);
                Enchantment enchantment = Enchantment.byId(GsonHelper.getAsInt(pSerializedRecipe, "enchantmentid"));
                if (enchantment != null) {
                    output.enchant(enchantment, 1);
                }
                output.setDamageValue(1);
            }

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            inputs.set(0, Ingredient.fromJson(ingredients.get(0)));

            return new EnchantmentStationRecipe(pRecipeId, output, inputs);
        }

        @Override
        public @Nullable EnchantmentStationRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack output = pBuffer.readItem();
            int enchantmentId = pBuffer.readInt();

            if (enchantmentId != -1) {
                Enchantment enchantment = BuiltInRegistries.ENCHANTMENT.byId(enchantmentId);
                if (enchantment == null) {
                    logger.info("Enchantment is 'null'");
                    logger.debug("Enchantment is 'null'");
                } else {
                    output.enchant(enchantment, 1); // Hier könntest du die Stufe der Verzauberung anpassen
                }
            }


            return new EnchantmentStationRecipe(pRecipeId, output, inputs);
        }


        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, EnchantmentStationRecipe recipe) {
            pBuffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            ItemStack resultItem = recipe.getResultItem(RegistryAccess.EMPTY);
            pBuffer.writeItem(resultItem);

            // Schreibe die Enchantment-ID
            if (resultItem.isEnchanted()) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(resultItem);
                if (!enchantments.isEmpty()) {
                    Enchantment enchantment = enchantments.keySet().iterator().next();
                    int enchantmentId = BuiltInRegistries.ENCHANTMENT.getId(enchantment);
                    pBuffer.writeInt(enchantmentId);
                    logger.info("IMMERSIVE ENCHANTMENTS: wrote enchantment Id to server "+ enchantmentId+ "ENCHANTMENT NAME: "+enchantment.getDescriptionId());
                    logger.debug("IMMERSIVE ENCHANTMENTS: wrote enchantment Id to server "+ enchantmentId+ "ENCHANTMENT NAME: "+enchantment.getDescriptionId());
                } else {
                    pBuffer.writeInt(-1); // Keine Verzauberung
                }
            } else {
                pBuffer.writeInt(-1); // Keine Verzauberung
            }
        }


    }
}
