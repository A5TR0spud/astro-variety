package net.astrospud.astrovariety.registry;

import net.astrospud.astrovariety.AstroVariety;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AVBlocks {

    //public static final Block NO_DROP_SCAFFOLD = registerBlock("no_drop_scaffold",
            //new WrenchScaffoldBlock());

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(AstroVariety.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, FabricItemSettings settings) {
        return Registry.register(Registry.ITEM, new Identifier(AstroVariety.MOD_ID, name),
                new BlockItem(block, settings));
    }

    public static void registerModBlocks() {
        AstroVariety.LOGGER.info("Registering ModBlocks for " + AstroVariety.MOD_ID);
    }
}
