package de.stynxyxy.betterminecraft.recipe;

import de.stynxyxy.betterminecraft.ImmersiveEnchatments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ImmersiveEnchatments.MODID);

    public static final RegistryObject<RecipeSerializer<EnchantmentStationRecipe>> Enchantment_STATION_SERIALIZER =
            SERIALIZERS.register("enchantment_station", ()-> EnchantmentStationRecipe.Serializer.INSTANCE);


    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}
