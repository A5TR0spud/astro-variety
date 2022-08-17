package net.astrospud.astrovariety.types.magicsupport.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class ToggleItem extends Item {
    boolean toggle = true;

    public ToggleItem(Settings settings) {
        super(settings);
    }

    /*@Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && otherStack.isEmpty()) {
            toggle();
            return false;
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }*/

    /*@Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        Formatting format = !toggle ? Formatting.GRAY : Formatting.GOLD;
        tooltip.add(Text.translatable("tooltip.astrovariety.toggle." + toggle).formatted(format));
        tooltip.add(Text.translatable("tooltip.astrovariety.right_click").formatted(Formatting.GRAY));
        tooltip.add(Text.empty());
    }*/

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (toggle) {
            specialTick(stack, world, entity, slot, selected);
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public void toggle() {
        toggle = !toggle;
    }

    public void specialTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    }
}
