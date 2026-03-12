package com.udon.watatsumi.registry;

import com.udon.watatsumi.Watatsumi;
import com.udon.watatsumi.menu.MixingMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, Watatsumi.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<MixingMenu>> MIXING =
            MENUS.register("mixing",
                    () -> IMenuTypeExtension.create(MixingMenu::new)
            );

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}