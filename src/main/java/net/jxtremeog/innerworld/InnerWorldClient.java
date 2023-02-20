package net.jxtremeog.innerworld;

import net.fabricmc.api.ClientModInitializer;
import net.jxtremeog.innerworld.screen.AutoEnchantScreen;
import net.jxtremeog.innerworld.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class InnerWorldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.AUTO_ENCHANT_SCREEN_HANDLER, AutoEnchantScreen::new);
    }
}
