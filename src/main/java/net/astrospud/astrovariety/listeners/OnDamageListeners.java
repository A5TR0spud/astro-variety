package net.astrospud.astrovariety.listeners;

import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVStatusEffects;
import net.astrospud.astrovariety.types.magicsupport.item.MagicSupportItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class OnDamageListeners {
    public static void register(){
        OnDamageCallback.EVENT.register(OnDamageListeners::DamageBleedingHeart);
        OnDamageCallback.EVENT.register(OnDamageListeners::DamageNanoMachines);
        OnDamageCallback.EVENT.register(OnDamageListeners::DamageInkWell);
    }

    public static void DamageBleedingHeart(PlayerEntity player, DamageSource source){
        ItemStack stack = AVItems.BLEEDING_HEART.getDefaultStack();
        int count = MagicSupportItem.getCount(stack, player);
        if (count > 0) {
            ItemCooldownManager colman = player.getItemCooldownManager();
            int tick = (int) ((float) (40 / count));
            colman.set(stack.getItem(), tick);
        }
    }

    public static void DamageNanoMachines(PlayerEntity player, DamageSource source){
        ItemStack stack = AVItems.NANOMACHINE.getDefaultStack();
        int count = MagicSupportItem.getCount(stack, player);
        if (count > 0) {
            ItemCooldownManager colman = player.getItemCooldownManager();
            int tick = (int) ((float) (40 / count));
            colman.set(stack.getItem(), tick);
        }
    }

    public static void DamageInkWell(PlayerEntity player, DamageSource source){
        ItemStack stack = AVItems.INK_WELL.getDefaultStack();
        int count = MagicSupportItem.getCount(stack, player);
        ItemCooldownManager colman = player.getItemCooldownManager();
        if (!colman.isCoolingDown(stack.getItem())
                && source.getAttacker() instanceof LivingEntity entity && count > 0) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, count-1, false, false, true));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 120, count-1, false, false, true));
            if (!(entity instanceof PlayerEntity)) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30, count+1, false, false, true));
                entity.addStatusEffect(new StatusEffectInstance(AVStatusEffects.LOWER_FOLLOW, 30, count+5, false, false, true));
            }
            colman.set(stack.getItem(), 50);
        }
    }
}
