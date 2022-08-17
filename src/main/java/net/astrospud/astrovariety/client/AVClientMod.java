package net.astrospud.astrovariety.client;

import net.astrospud.astrovariety.screen.AVScreenHandlers;
import net.astrospud.astrovariety.screen.AlchemistTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class AVClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(AVScreenHandlers.ALCHEMIST_TABLE_SCREEN_HANDLER, AlchemistTableScreen::new);
    }
}
