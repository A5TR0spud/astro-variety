package net.astrospud.astrovariety.types.utils;

import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.types.magicsupport.item.MagicSupportItem;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Utils {
    public static float applyDamageEffects(PlayerEntity player, float amount, DamageSource source) {
        int count = getItemCount(AVItems.TALISMAN_OF_DECAY, player);
        if (count > 0) {
            return 0;
        }
        return amount;
    }

    public static int getItemCount(Item item, PlayerEntity player) {
        int count = 0;
        for (int i = 0; i < player.getInventory().size(); i++) {
            if (player.getInventory().getStack(i).getItem() == item) {
                count += player.getInventory().getStack(i).getCount();
            }
        }
        return count;
    }
}
