package net.astrospud.astrovariety.registry;

import net.astrospud.astrovariety.AstroVariety;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AVItems {

    //blaze
    public static final Item EMBER_INGOT = registerItem("ember_ingot",
            new Item(new FabricItemSettings().group(ItemGroup.MATERIALS).fireproof()));
    public static final Item BLAZE_HAT = registerItem("blaze_hat",
            new SpecialArmorItem(AVArmorMaterials.BLAZE, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).fireproof(),
                    0xffff66, "tooltip.astrovariety.blaze", Formatting.GOLD));
    public static final Item BLAZE_BOOTS = registerItem("blaze_boots",
            new SpecialArmorItem(AVArmorMaterials.BLAZE, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).fireproof(),
                    0xffff66, "tooltip.astrovariety.blaze", Formatting.GOLD));
    //public static final Item BLAZE_SHIELD = registerItem("blaze_shield",
            //new ShieldItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(336)));

    //cactus
    public static final Item CACTUS_CHESTPLATE = registerItem("cactus_chestplate",
            new SpecialArmorItem(AVArmorMaterials.CACTUS, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1),
                    0x33ff33, "tooltip.astrovariety.cactus_armor", Formatting.DARK_GREEN));

    //sugar cane
    public static final Item COMPACT_SUGAR_CANE = registerItem("compact_sugar_cane",
            new SpecialItem(new FabricItemSettings().group(ItemGroup.MATERIALS), true));
    public static final Item CANE_BOOTS = registerItem("cane_boots",
            new SpecialArmorItem(AVArmorMaterials.CANE, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1),
                    0x66ff66, "tooltip.astrovariety.cane", Formatting.GREEN));
    public static final Item CANE_LEGGINGS = registerItem("cane_leggings",
            new SpecialArmorItem(AVArmorMaterials.CANE, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1),
                    0x66ff66, "tooltip.astrovariety.cane", Formatting.GREEN));

    //golem
    public static final Item GOLEM_HELMET = registerItem("golem_helmet",
            new SpecialArmorItem(AVArmorMaterials.GOLEM, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1),
                    0x666666, "tooltip.astrovariety.golem", Formatting.GRAY));
    public static final Item GOLEM_CHESTPLATE = registerItem("golem_chestplate",
            new SpecialArmorItem(AVArmorMaterials.GOLEM, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1),
                    0x666666, "tooltip.astrovariety.golem", Formatting.GRAY));

    //misc
    public static final Item TALISMAN_OF_DECAY = registerItem("talisman_of_decay",
            new DecayItem(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(AstroVariety.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AstroVariety.LOGGER.info("Registering Mod Items for " + AstroVariety.MOD_ID);
    }
}
