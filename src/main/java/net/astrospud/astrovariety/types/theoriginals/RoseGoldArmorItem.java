package net.astrospud.astrovariety.types.theoriginals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.world.World;

public class RoseGoldArmorItem extends AVArmorItem {

    final static int MAX_TICK = 600;
    int tick = MAX_TICK;

    public RoseGoldArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings, 0xff6666);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();
            redstoneRepair(stack, itemStack);
            return true;
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            redstoneRepair(stack, otherStack);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity player && (player.isCreative() || player.isSpectator()))
        {
            return;
        }
        if (entity instanceof LivingEntity && slot == this.slot.getEntitySlotId()
                ) {
            tick++;
            if (tick >= MAX_TICK) {
                tick = 0;
                stack.setDamage(stack.getDamage()+1);
            }
        }
    }

    public void redstoneRepair(ItemStack toRepair, ItemStack consumable) {
        int amount = 0;
        int count = 0;
        int i = 3;
        int amountNeeded = toRepair.getDamage();

        if (consumable.getItem() == Items.REDSTONE) {
            count = i;
        }
        else if (consumable.getItem() == Items.REDSTONE_BLOCK) {
            count = i*9;
        }
        else if (consumable.getItem() == Items.REDSTONE_ORE) {
            count = i+2;
        }
        else if (consumable.getItem() == Items.DEEPSLATE_REDSTONE_ORE) {
            count = i+2;
        }
        else if (consumable.getItem() == Items.REDSTONE_TORCH) {
            count = i;
        }

        amount = count * consumable.getCount();

        if (count > 0 && amount <= amountNeeded && amountNeeded > 0 && amount > 0) {
            toRepair.setDamage(toRepair.getDamage() - amount);
            consumable.decrement(amountNeeded);
        }
        else if (count > 0 && amountNeeded > 0 && amount > 0){
            toRepair.setDamage(0);
            consumable.decrement(amountNeeded / count);
        }
    }
}
