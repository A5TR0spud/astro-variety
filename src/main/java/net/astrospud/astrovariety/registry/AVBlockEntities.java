package net.astrospud.astrovariety.registry;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.types.magicsupport.block.entity.AlchemistTableBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AVBlockEntities {
    public static BlockEntityType<AlchemistTableBlockEntity> ALCHEMIST_TABLE;

    public static void registerAllBlockEntities() {
        ALCHEMIST_TABLE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(AstroVariety.MOD_ID, "alchemist_table"),
                FabricBlockEntityTypeBuilder.create(AlchemistTableBlockEntity::new,
                        AVBlocks.ALCHEMIST_TABLE).build(null));
    }
}
