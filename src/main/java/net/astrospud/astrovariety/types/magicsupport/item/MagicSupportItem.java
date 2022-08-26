package net.astrospud.astrovariety.types.magicsupport.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MagicSupportItem extends Item {
    public MagicSupportItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity player) {
            int count = getCount(stack, player);

            if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) {

                if (count > 0) {
                    specialTick(stack, world, player, slot, selected, count);
                }
            }
        }
    }

    public static int getCount(ItemStack stack, PlayerEntity player) {
        int count = 0;
        for (int i = 0; i < player.getInventory().size(); i++) {
            if (player.getInventory().getStack(i).getItem() == stack.getItem()) {
                count += player.getInventory().getStack(i).getCount();
            }
        }
        return count;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int count) {
    }
}
