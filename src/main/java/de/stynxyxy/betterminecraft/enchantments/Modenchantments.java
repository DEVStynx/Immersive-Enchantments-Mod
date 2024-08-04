package de.stynxyxy.betterminecraft.enchantments;

import de.stynxyxy.betterminecraft.ImmersiveEnchatments;
import de.stynxyxy.betterminecraft.enchantments.custom.Testenchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Modenchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ImmersiveEnchatments.MODID);

    public static final RegistryObject<Enchantment> TESTENCHANTMENT =
            ENCHANTMENTS.register("testenchantment",() -> new Testenchantment(Enchantment.Rarity.UNCOMMON,EnchantmentCategory.WEAPON,EquipmentSlot.MAINHAND));

    public static void register(IEventBus bus) {
        ENCHANTMENTS.register(bus);
    }
}