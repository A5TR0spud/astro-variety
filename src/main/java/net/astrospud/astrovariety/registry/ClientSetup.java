package net.astrospud.astrovariety.registry;


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