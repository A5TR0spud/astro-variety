package net.astrospud.astrovariety.registry;

import net.astrospud.astrovariety.AstroVariety;
import net.minecraft.state.property.BooleanProperty;

public class AVProperties {
    public static final BooleanProperty DO_DROPS = BooleanProperty.of("do_drops");

    public static void registerModProperies() {
        AstroVariety.LOGGER.info("Registering Mod Properties for " + AstroVariety.MOD_ID);
    }
}
