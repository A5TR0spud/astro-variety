package net.astrospud.astrovariety.types.magicsupport.item;

import net.astrospud.astrovariety.types.utils.Utils;
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
            int count = Utils.getItemCount(stack.getItem(), player);

            if (!player.getItemCooldownManager().isCoolingDown(stack.getItem())) {

                if (count > 0) {
                    specialTick(stack, world, player, slot, selected, count);
                }
            }
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int count) {
    }
}
