package net.astrospud.astrovariety.client;

import net.astrospud.astrovariety.screen.AVScreenHandlers;
import net.astrospud.astrovariety.screen.AlchemistTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class AVClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(AVScreenHandlers.ALCHEMIST_TABLE_SCREEN_HANDLER, AlchemistTableScreen::new);
        AVScreenHandlers.registerScreenHandlers();
    }
}
