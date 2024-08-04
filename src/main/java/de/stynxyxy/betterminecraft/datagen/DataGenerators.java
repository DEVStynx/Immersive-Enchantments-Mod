package de.stynxyxy.betterminecraft.datagen;


import de.stynxyxy.betterminecraft.ImmersiveEnchatments;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImmersiveEnchatments.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherdata(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(true, new ModRecipeProvider(packOutput));
        generator.addProvider(true, new ModItemModelProvider(packOutput,existingFileHelper));
        generator.addProvider(true, new ModBlockStateProvider(packOutput,existingFileHelper));
        generator.addProvider(true, ModLootTableProvider.create(packOutput));


    }
}
