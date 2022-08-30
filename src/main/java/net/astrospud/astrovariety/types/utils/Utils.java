package net.astrospud.astrovariety.types.utils;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.listeners.OnDamageListeners;
import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVStatusEffects;
import net.astrospud.astrovariety.types.magicsupport.item.MagicSupportItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class Utils {
    public static float applyDamageEffects(PlayerEntity player, float amount, DamageSource source) {
        int count = getItemCount(AVItems.TALISMAN_OF_DECAY, player);
        World world = player.getWorld();
        //OnDamageListeners.DamageDecay(player, amount);
        //ClientPlayerEntity clientPlayer = player.getWorld().isClient ? (ClientPlayerEntity) player : null;
        //ServerPlayerEntity serverPlayer = player.getServer().getPlayerManager().getPlayer(player.getUuid());
        if (world.isClient && count > 0 && !player.hasStatusEffect(AVStatusEffects.DECAY)) {
            //DamageDecay(player, source, amount);
            return 0;
        }
        if (!world.isClient && count > 0 && !player.hasStatusEffect(AVStatusEffects.DECAY)) {
            DamageDecay(player, source, amount);
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

    public static void DamageDecay(PlayerEntity player, DamageSource source, float amount) {
        Item item = AVItems.TALISMAN_OF_DECAY;
        int count = Utils.getItemCount(item, player);
        int duration = (int)(amount + 1);
        int amp = 0;
        if (duration > count+2) {
            int remaining = (int)(amount-(count+2));
            duration = 20*(count+2)+19;
            amp = MathHelper.clamp(remaining/(count+2), 0, 255);
        } else {
            duration *= 20;
        }
        player.addStatusEffect(new StatusEffectInstance(AVStatusEffects.DECAY, duration, amp, false, false, true));
    }
}
