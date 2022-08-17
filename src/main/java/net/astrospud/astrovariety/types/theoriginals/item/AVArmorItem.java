package net.astrospud.astrovariety.types.theoriginals.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AVArmorItem extends ArmorItem {

    private int COLOR = -1;
    private String KEY = "";
    private Formatting FORMAT = Formatting.WHITE;

    public AVArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings,
                       int color, String tooltipKey, Formatting format) {
        super(material, slot, settings);
        COLOR = color;
        KEY = tooltipKey;
        FORMAT = format;
    }

    public AVArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings,
                       int color) {
        super(material, slot, settings);
        COLOR = color;
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
