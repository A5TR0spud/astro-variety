package net.astrospud.astrovariety.client;


import net.astrospud.astrovariety.registry.AVEntities;
import net.astrospud.astrovariety.screen.AVScreenHandlers;
import net.astrospud.astrovariety.screen.AlchemistTableScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class ClientSetup implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AVEntities.registerRenderers();
    }
}