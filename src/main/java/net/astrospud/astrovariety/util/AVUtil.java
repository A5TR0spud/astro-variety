package net.astrospud.astrovariety.util;

import net.astrospud.astrovariety.registry.AVItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AVUtil {
    public static void damageRoseGoldStack(PlayerEntity player, ItemStack stack, Item item, int mode, int damage) {
        if (stack.getItem() == item && stack.getOrCreateNbt().getInt("Mode") == mode
                && stack.getDamage() + damage <= stack.getMaxDamage()
                && !player.isCreative() && !player.isSpectator()) {
            stack.setDamage(stack.getDamage() + damage);
        }
    }

    public static boolean boolDamageRoseGold(PlayerEntity player, ItemStack stack, Item item, int mode, int damage, boolean ignoreCreative, boolean doNormal) {
        boolean creativebool = ignoreCreative || !(player.isCreative() || player.isSpectator());
        if (stack.getItem() == item && stack.getOrCreateNbt().getInt("Mode") == mode
                && stack.getDamage() + damage <= stack.getMaxDamage()
                && creativebool) {
            if (doNormal && !(player.isCreative() || player.isSpectator())) {
                damageRoseGoldStack(player, stack, item, mode, damage);
            }
            return true;
        }
        return false;
    }
}
