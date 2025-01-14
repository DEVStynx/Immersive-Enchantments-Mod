package de.stynxyxy.betterminecraft.screen;

import de.stynxyxy.betterminecraft.ImmersiveEnchatments;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ImmersiveEnchatments.MODID);

    public static final RegistryObject<MenuType<EnchantmentStationMenu>> ENCHANTMENTSTATIONMENU =
            registerMenuType(EnchantmentStationMenu::new, "enchantment_station_menu");

    public static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }



    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
