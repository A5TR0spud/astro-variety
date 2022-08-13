package net.astrospud.astrovariety.client;


import net.astrospud.astrovariety.registry.AVEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientSetup implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AVEntities.registerRenderers();
    }
}