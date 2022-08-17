package net.astrospud.astrovariety.screen;

import net.astrospud.astrovariety.AstroVariety;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AVScreenHandlers {
    public static ScreenHandlerType<AlchemistTableScreenHandler> ALCHEMIST_TABLE_SCREEN_HANDLER =
            Registry.register(Registry.SCREEN_HANDLER, new Identifier(AstroVariety.MOD_ID, "alchemist_table"),
                    AlchemistTableScreenHandler::new);
}
