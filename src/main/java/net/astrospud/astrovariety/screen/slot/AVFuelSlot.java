package net.astrospud.astrovariety.screen.slot;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class AVFuelSlot extends Slot {
    public AVFuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(stack) || AVFuelSlot.isBucket(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return AVFuelSlot.isBucket(stack) ? 1 : super.getMaxItemCount(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.isOf(Items.BUCKET);
    }
}
