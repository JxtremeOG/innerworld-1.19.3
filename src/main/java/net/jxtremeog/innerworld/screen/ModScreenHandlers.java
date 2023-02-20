package net.jxtremeog.innerworld.screen;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<AutoEnchantScreenHandler> AUTO_ENCHANT_SCREEN_HANDLER;

    public static void registerAllScreenHandler() {
        AUTO_ENCHANT_SCREEN_HANDLER = new ScreenHandlerType<>(AutoEnchantScreenHandler::new);
    }
}
