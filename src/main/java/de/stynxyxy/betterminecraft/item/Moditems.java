package de.stynxyxy.betterminecraft.item;

import de.stynxyxy.betterminecraft.ImmersiveEnchatments;
import de.stynxyxy.betterminecraft.item.custom.PenItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Moditems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ImmersiveEnchatments.MODID);

    public static final RegistryObject<Item> PenItem = ITEMS.register("pen", () -> new PenItem(new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
