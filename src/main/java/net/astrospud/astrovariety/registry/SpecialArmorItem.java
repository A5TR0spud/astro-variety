package net.astrospud.astrovariety.registry;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpecialArmorItem extends ArmorItem {

    private int COLOR = -1;
    private String KEY = "";
    private Formatting FORMAT = Formatting.WHITE;

    public SpecialArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings,
                            int color, String tooltipKey, Formatting format) {
        super(material, slot, settings);
        COLOR = color;
        KEY = tooltipKey;
        FORMAT = format;
    }
    public SpecialArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings, int color) {
        super(material, slot, settings);
        COLOR = color;
    }
    public SpecialArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (COLOR >= 0) {
            return COLOR;
        }
        return super.getItemBarColor(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (KEY != "") {
            tooltip.add(Text.translatable(KEY).formatted(FORMAT));
        }
    }
}
