package de.stynxyxy.betterminecraft.block;

import de.stynxyxy.betterminecraft.ImmersiveEnchatments;
import de.stynxyxy.betterminecraft.block.custom.EnchantmentStationBlock;
import de.stynxyxy.betterminecraft.item.Moditems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ImmersiveEnchatments.MODID);
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {

        return Moditems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    //customcontent
    //public static final RegistryObject<Block> kristerine_ore = registerBlock("kristerine_ore",
            //() -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    //.strength(6f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> Enchantment_STATION_BLOCK = registerBlock("enchantment_station",
            () -> new EnchantmentStationBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.5f).noOcclusion()));
}
