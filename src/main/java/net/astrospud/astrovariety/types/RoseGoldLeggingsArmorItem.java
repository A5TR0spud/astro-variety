package net.astrospud.astrovariety.types;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class RoseGoldLeggingsArmorItem extends RoseGoldArmorItem {

    String[] modes = new String[]{"speed", "jump", "step"};
    int modeIndex = 0;

    public RoseGoldLeggingsArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings, int color) {
        super(material, slot, settings, color);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && otherStack.isEmpty()) {
            switchMode();
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.astrovariety.mode" + modes[modeIndex]).formatted(Formatting.GOLD));
        tooltip.add(Text.translatable("tooltip.astrovariety.right_click").formatted(Formatting.GRAY));
    }

    public void switchMode() {
        modeIndex = (modeIndex + 1) % modes.length;
    }
}
