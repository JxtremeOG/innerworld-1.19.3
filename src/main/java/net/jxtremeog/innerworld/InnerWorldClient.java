package net.jxtremeog.innerworld;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.jxtremeog.innerworld.block.entity.ModBlockEntities;
import net.jxtremeog.innerworld.block.entity.client.AutoEnchantBlockEntityRenderer;
import net.jxtremeog.innerworld.screen.AutoEnchantScreen;
import net.jxtremeog.innerworld.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class InnerWorldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.AUTO_ENCHANT_SCREEN_HANDLER, AutoEnchantScreen::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.AUTO_ENCHANT_TABLE,
                AutoEnchantBlockEntityRenderer::new);
    }
}
