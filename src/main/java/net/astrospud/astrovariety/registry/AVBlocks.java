package net.astrospud.astrovariety.registry;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.types.AlchemistTableBlock;
import net.astrospud.astrovariety.types.TalismanItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AVBlocks {
    //public static final Block ALCHEMIST_TABLE = registerBlock("alchemist_table",
            //new AlchemistTableBlock(FabricBlockSettings.of(Material.WOOD).strength(20, 0)), new FabricItemSettings().group(ItemGroup.BREWING));

    private static Block registerBlock(String name, Block block, FabricItemSettings settings) {
        registerBlockItem(name, block, settings);
        return Registry.register(Registry.BLOCK, new Identifier(AstroVariety.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, FabricItemSettings settings) {
        return Registry.register(Registry.ITEM, new Identifier(AstroVariety.MOD_ID, name),
                new BlockItem(block, settings));
    }

    public static void registerModBlocks() {
        AstroVariety.LOGGER.info("Registering Mod Blocks for " + AstroVariety.MOD_ID);
    }
}
