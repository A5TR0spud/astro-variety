package net.astrospud.astrovariety.listeners;

import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVStatusEffects;
import net.astrospud.astrovariety.types.magicsupport.item.MagicSupportItem;
import net.astrospud.astrovariety.types.utils.RoseGoldDamageUtil;
import net.astrospud.astrovariety.types.utils.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;

import java.util.ArrayList;

public class OnDamageListeners {
    public static void register(){
        OnDamageCallback.EVENT.register(OnDamageListeners::DamageBleedingHeart);
        OnDamageCallback.EVENT.register(OnDamageListeners::DamageNanoMachines);
        OnDamageCallback.EVENT.register(OnDamageListeners::DamageInkWell);
        OnDamageCallback.EVENT.register(OnDamageListeners::DamageDecay);
        OnDamageCallback.EVENT.register(OnDamageListeners::CactusArmorEffects);
        OnDamageCallback.EVENT.register(OnDamageListeners::RoseGoldEffects);
    }

    public static void DamageBleedingHeart(LivingEntity entity, DamageSource source, float amount) {
        if (entity instanceof PlayerEntity player) {
            Item item = AVItems.BLEEDING_HEART;
            int count = Utils.getItemCount(item, player);
            if (count > 0) {
                ItemCooldownManager colman = player.getItemCooldownManager();
                int tick = (int) ((float) (40 / count));
                colman.set(item, tick);
            }
        }
    }

    public static void DamageNanoMachines(LivingEntity entity, DamageSource source, float amount) {
        if (entity instanceof PlayerEntity player) {
            Item item = AVItems.NANOMACHINE;
            int count = Utils.getItemCount(item, player);
            if (count > 0) {
                ItemCooldownManager colman = player.getItemCooldownManager();
                int tick = (int) ((float) (40 / count));
                colman.set(item, tick);
            }
        }
    }

    public static void DamageInkWell(LivingEntity entity, DamageSource source, float amount) {
        if (entity instanceof PlayerEntity player) {
            Item item = AVItems.INK_WELL;
            int count = Utils.getItemCount(item, player);
            ItemCooldownManager colman = player.getItemCooldownManager();
            if (!colman.isCoolingDown(item)
                    && source.getAttacker() instanceof LivingEntity entity1 && count > 0) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, count - 1, false, false, true));
                entity1.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 120, count - 1, false, false, true));
                if (!(entity1 instanceof PlayerEntity)) {
                    entity1.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30, count + 1, false, false, true));
                    entity1.addStatusEffect(new StatusEffectInstance(AVStatusEffects.LOWER_FOLLOW, 30, count + 5, false, false, true));
                }
                colman.set(item, 50);
            }
        }
    }

    public static void DamageDecay(LivingEntity entity, DamageSource source, float amount) {
        if (entity instanceof PlayerEntity player) {
            Item item = AVItems.TALISMAN_OF_DECAY;
            int count = Utils.getItemCount(item, player);
            //player.writeNbt()
        }
    }

    private static void CactusArmorEffects(LivingEntity entity, DamageSource source, float amount) {
        Iterable<ItemStack> armorItems = entity.getArmorItems();
        ArrayList<ItemStack> armor = new ArrayList<>();
        armorItems.forEach(armor::add);
        if (source.getAttacker() instanceof LivingEntity attacker) {
            int strength = 0;
            if (armor.get(2).getItem() == AVItems.CACTUS_CHESTPLATE) {
                strength++;
            }
            if (strength > 0) {
                attacker.damage(DamageSource.CACTUS, strength);
            }
        }
    }

    private static void RoseGoldEffects(LivingEntity entity, DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity player) {
            Iterable<ItemStack> armorItems = entity.getArmorItems();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armorItems.forEach(armor::add);
            RoseGoldDamageUtil.boolDamageRoseGold(player, armor.get(2), AVItems.ROSE_GOLD_CHESTPLATE, 3, 3, true, true);
        }
    }
}
