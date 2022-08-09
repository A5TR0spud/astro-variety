package net.astrospud.astrovariety.types.theoriginals;

import net.astrospud.astrovariety.registry.AVArmorMaterial;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class RoseGoldLeggingsArmorItem extends RoseGoldArmorItem {

    String[] modes = new String[]{"empty", "speed", "jump", "step"};
    //int modeIndex = 0;

    public RoseGoldLeggingsArmorItem(Settings settings) {
        super(AVArmorMaterial.CHARGED_ROSE_GOLD, EquipmentSlot.LEGS, settings);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && otherStack.isEmpty()) {
            switchMode(stack);
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        int modeIndex = stack.getOrCreateNbt().getInt("Mode");
        String key = modes[modeIndex];
        Formatting format = key == "empty" ? Formatting.GRAY : Formatting.GOLD;
        tooltip.add(Text.translatable("tooltip.astrovariety.mode." + key).formatted(format));
        tooltip.add(Text.translatable("tooltip.astrovariety.right_click").formatted(Formatting.GRAY));
    }

    public void switchMode(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        int modeIndex = stack.getOrCreateNbt().getInt("Mode");
        nbtCompound.putInt("Mode", (modeIndex + 1) % modes.length);
    }
}
