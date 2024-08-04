package de.stynxyxy.betterminecraft.utility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.Enchantment;
import org.apache.logging.log4j.core.jmx.Server;

public class Enchantment_recipe_item_handler {
    public static boolean enchant_item_using_recipe(Inventory inv, Recipe<?> recipe,int itemindex, Item itemtype, int Enchantmentid,int level) {
        ItemStack stack = inv.getItem(itemindex);
        stack.enchant(Enchantment.byId(Enchantmentid),level);
        return stack.getEnchantmentLevel(Enchantment.byId(Enchantmentid)) >= level;
    }
}
