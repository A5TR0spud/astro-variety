package net.astrospud.astrovariety;

import net.astrospud.astrovariety.registry.AVBlocks;
import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVStatusEffects;
import net.fabricmc.api.ModInitializer;
import net.minecraft.state.property.BooleanProperty;
import org.apache.logging.log4j.core.config.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AstroVariety implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "astrovariety";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final BooleanProperty DO_DROPS = BooleanProperty.of("do_drops");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		AVItems.registerModItems();
		AVBlocks.registerModBlocks();
		AVStatusEffects.register();
	}
}
