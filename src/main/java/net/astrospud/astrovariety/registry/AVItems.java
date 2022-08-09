package net.astrospud.astrovariety.registry;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.types.theoriginals.*;
import net.astrospud.astrovariety.types.magicsupport.*;
import net.astrospud.astrovariety.types.unique.BottomlessBucketItem;
import net.astrospud.astrovariety.types.unique.ObsidianSpongeItem;
import net.astrospud.astrovariety.types.unique.RainbowSoakerItem;
import net.astrospud.astrovariety.types.unique.UltraAbsorbentSpongeItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AVItems {
    //rose gold
    public static final Item ROSE_GOLD_HELMET = registerItem("charged_rose_gold_helmet",
            new RoseGoldArmorItem(AVArmorMaterial.CHARGED_ROSE_GOLD, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1)));
    public static final Item ROSE_GOLD_CHESTPLATE = registerItem("charged_rose_gold_chestplate",
            new RoseGoldChestplateArmorItem(
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1)));
    public static final Item ROSE_GOLD_LEGGINGS = registerItem("charged_rose_gold_leggings",
            new RoseGoldLeggingsArmorItem(
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1)));
    public static final Item ROSE_GOLD_BOOTS = registerItem("charged_rose_gold_boots",
            new RoseGoldArmorItem(AVArmorMaterial.CHARGED_ROSE_GOLD, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1)));
    public static final Item ROSE_GOLD_INGOT = registerItem("rose_gold_ingot",
            new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));

    //blaze
    public static final Item EMBER_INGOT = registerItem("ember_ingot",
            new Item(new FabricItemSettings().group(ItemGroup.MATERIALS).fireproof()));
    public static final Item ALCHEMY_CATALYST = registerItem("alchemy_catalyst",
            new Item(new FabricItemSettings().group(ItemGroup.MATERIALS)));

    public static final Item BLAZE_HAT = registerItem("blaze_hat",
            new AVArmorItem(AVArmorMaterial.BLAZE, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).fireproof(),
                    0xffff66, "tooltip.astrovariety.blaze", Formatting.GOLD));
    public static final Item BLAZE_BOOTS = registerItem("blaze_boots",
            new AVArmorItem(AVArmorMaterial.BLAZE, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).fireproof(),
                    0xffff66, "tooltip.astrovariety.blaze", Formatting.GOLD));
    //public static final Item BLAZE_SHIELD = registerItem("blaze_shield",
            //new ShieldItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(336)));

    //cactus
    public static final Item CACTUS_CHESTPLATE = registerItem("cactus_chestplate",
            new AVArmorItem(AVArmorMaterial.CACTUS, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1),
                    0x33ff33, "tooltip.astrovariety.cactus_armor", Formatting.DARK_GREEN));

    //sugar cane
    public static final Item COMPACT_SUGAR_CANE = registerItem("compact_sugar_cane",
            new SpecialItem(new FabricItemSettings().group(ItemGroup.MATERIALS), true));
    public static final Item CANE_BOOTS = registerItem("cane_boots",
            new AVArmorItem(AVArmorMaterial.CANE, EquipmentSlot.FEET,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1),
                    0x66ff66, "tooltip.astrovariety.cane", Formatting.GREEN));
    public static final Item CANE_LEGGINGS = registerItem("cane_leggings",
            new AVArmorItem(AVArmorMaterial.CANE, EquipmentSlot.LEGS,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1),
                    0x66ff66, "tooltip.astrovariety.cane", Formatting.GREEN));

    //golem
    public static final Item GOLEM_HELMET = registerItem("golem_helmet",
            new AVArmorItem(AVArmorMaterial.GOLEM, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1),
                    0xaaaaaa, "tooltip.astrovariety.golem", Formatting.GRAY));
    public static final Item GOLEM_CHESTPLATE = registerItem("golem_chestplate",
            new AVArmorItem(AVArmorMaterial.GOLEM, EquipmentSlot.CHEST,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1),
                    0xaaaaaa, "tooltip.astrovariety.golem", Formatting.GRAY));

    //misc
    public static final Item SEER_HELMET = registerItem("seer_helmet",
            new AVArmorItem(AVArmorMaterial.SEER, EquipmentSlot.HEAD,
                    new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).fireproof(),
                    0x33aaaa, "tooltip.astrovariety.seer", Formatting.DARK_AQUA));
    public static final Item SWORD_OF_LEAPING = registerItem("sword_of_leaping",
            new SwordOfLeapingItem(AVToolMaterial.SEER, new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1)));
    public static final Item ARROW_GATLER = registerItem("arrow_gatler",
            new ArrowGatlerItem(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(649)));
    /*public SeerHelmetArmorItem() {
    super(AVArmorMaterials.SEER, EquipmentSlot.HEAD,
            new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1).fireproof().maxDamage(0),
            0x33aaaa, "tooltip.astrovariety.seer", Formatting.DARK_AQUA);
    }*/

    //talismans
    public static final Item TALISMAN_OF_DECAY = registerItem("talisman_of_decay",
            new TalismanItem("decay")); //decay exists as an extant form of life... you cannot kill me in a way that matters

    //public static final Item TALISMAN_OF_ = registerItem("talisman_of_",
            //new TalismanItem("")); //failure is the opportunity to begin again... don’t die just yet
    public static final Item BLEEDING_HEART = registerItem("bleeding_heart",
            new BleedingHeartItem());
    public static final Item NANOMACHINE = registerItem("nanomachine",
            new NanomachineItem());
    public static final Item GOLD_PILL = registerItem("gold_pill",
            new GoldPillItem());
    public static final Item DWARVEN_PICKAXE = registerItem("dwarven_pickaxe",
            new DwarvenPickaxeItem());
    public static final Item WIND_PEARLS = registerItem("wind_pearls",
            new WindPearlsItem());

    public static final Item ULTRA_ABSORBENT_SPONGE = registerItem("ultra_absorbent_sponge",
            new UltraAbsorbentSpongeItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).fireproof()));
    public static final Item OBSIDIAN_SPONGE = registerItem("obsidian_sponge",
            new ObsidianSpongeItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).fireproof()));
    public static final Item RAINBOW_SOAKER = registerItem("rainbow_soaker",
            new RainbowSoakerItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).fireproof()));
    public static final Item BOTTOMLESS_WATER_BUCKET = registerItem("bottomless_water_bucket",
            new BottomlessBucketItem(Fluids.WATER, new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).fireproof()));
    public static final Item BOTTOMLESS_LAVA_BUCKET = registerItem("bottomless_lava_bucket",
            new BottomlessBucketItem(Fluids.LAVA, new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).fireproof()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(AstroVariety.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AstroVariety.LOGGER.info("Registering Mod Items for " + AstroVariety.MOD_ID);
    }
}
