package net.astrospud.astrovariety.registry;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.types.unique.DynamiteEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AVEntities {
    public static final EntityType<DynamiteEntity> DYNAMITE = registerDynamite("dynamite");

    //register("snowball", EntityType.Builder.create(SnowballEntity::new, SpawnGroup.MISC).setDimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));

    private static EntityType<DynamiteEntity> registerDynamite(final String id) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(AstroVariety.MOD_ID, id),
                FabricEntityTypeBuilder.<DynamiteEntity>create(SpawnGroup.MISC, DynamiteEntity::new)
                        .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                        .trackRangeBlocks(4)
                        .trackedUpdateRate(10)
                        .build());
    }

    public static void registerModEntities() {
        AstroVariety.LOGGER.info("Registering Mod Entities for " + AstroVariety.MOD_ID);
    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderers() {
        EntityRendererRegistry.register(DYNAMITE, FlyingItemEntityRenderer::new);
    }
}
