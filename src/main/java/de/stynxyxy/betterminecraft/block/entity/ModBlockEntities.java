package de.stynxyxy.betterminecraft.block.entity;

import de.stynxyxy.betterminecraft.ImmersiveEnchatments;
import de.stynxyxy.betterminecraft.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ImmersiveEnchatments.MODID);

    public static final RegistryObject<BlockEntityType<EnchantmentStationBlockEntity>> Enchantment_STATION =
            BLOCK_ENTITIES.register("enchantment_station",() ->
                    BlockEntityType.Builder.of(EnchantmentStationBlockEntity::new,
                            ModBlocks.Enchantment_STATION_BLOCK.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
