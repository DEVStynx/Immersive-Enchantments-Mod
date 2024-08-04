package de.stynxyxy.betterminecraft;

import com.mojang.logging.LogUtils;
import de.stynxyxy.betterminecraft.block.ModBlocks;
import de.stynxyxy.betterminecraft.block.entity.ModBlockEntities;
import de.stynxyxy.betterminecraft.enchantments.Modenchantments;
import de.stynxyxy.betterminecraft.events.CreativeMenuevents;
import de.stynxyxy.betterminecraft.item.Moditems;
import de.stynxyxy.betterminecraft.recipe.ModRecipes;
import de.stynxyxy.betterminecraft.screen.EnchantmentStationScreen;
import de.stynxyxy.betterminecraft.screen.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import javax.naming.Context;


@Mod(ImmersiveEnchatments.MODID)
public class ImmersiveEnchatments {


    public static final String MODID = "immersive_enchantments";

    public static final Logger LOGGER = LogUtils.getLogger();

    public ImmersiveEnchatments() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //register events
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(CreativeMenuevents::addcreative);


        //register custom content
        Moditems.register(modEventBus);
        ModBlocks.register(modEventBus);
        Modenchantments.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

    }


    public static Logger getLogger() {
        return LOGGER;
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }




    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

        LOGGER.info("HELLO from server starting");
    }


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            MenuScreens.register(ModMenuTypes.ENCHANTMENTSTATIONMENU.get(), EnchantmentStationScreen::new);
        }
    }
}
