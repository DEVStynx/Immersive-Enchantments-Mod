package de.stynxyxy.betterminecraft.events;

import de.stynxyxy.betterminecraft.ImmersiveEnchatments;
import de.stynxyxy.betterminecraft.block.ModBlocks;
import de.stynxyxy.betterminecraft.item.Moditems;
import net.minecraft.world.item.*;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImmersiveEnchatments.MODID)
public class CreativeMenuevents {


    @SubscribeEvent
    public static void addcreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(Moditems.PenItem.get());
            event.accept(ModBlocks.Enchantment_STATION_BLOCK.get());
        }

    }


}
