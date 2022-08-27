package net.astrospud.astrovariety.listeners;

import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVStatusEffects;
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

import java.util.ArrayList;
import java.util.UUID;

public class OnTickListeners {
    private static final UUID ROSE_GOLD_SPEED_ID = UUID.fromString("1fd7c69e-1686-4b16-b85f-220495594263");
    private static final UUID ROSE_GOLD_ATTACK_ID = UUID.fromString("12ce7946-891d-4678-b003-414d77a1a459");
    private static final UUID CANE_SPEED_ID = UUID.fromString("4577cf8a-3647-43ce-9128-71a022d5026f");
    private static final UUID GOLEM_HEALTH_ID = UUID.fromString("095f18a8-2a96-4558-9ff7-7680375309d0");


    public static void register(){
        OnTickCallback.EVENT.register(OnTickListeners::TickDecay);
        OnTickCallback.EVENT.register(OnTickListeners::TickWindMobility);
        OnTickCallback.EVENT.register(OnTickListeners::TickArmorEffects);
    }

    public static void TickDecay(LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            if (Utils.getItemCount(AVItems.TALISMAN_OF_DECAY, player) > 0) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 2, 0, true, false, false));
            }
        }
    }
    public static void TickWindMobility(LivingEntity entity) {
        if (entity.hasStatusEffect(AVStatusEffects.WIND_MOBILITTY)) {
            entity.airStrafingSpeed = entity.getMovementSpeed()/5;
        }
    }
    public static void TickArmorEffects(LivingEntity entity) {
        Iterable<ItemStack> armorItems = entity.getArmorItems();
        ArrayList<ItemStack> armor = new ArrayList<>();
        armorItems.forEach(armor::add);
        RoseGoldArmorEffects(entity, armor);
        CaneArmorEffects(entity, armor);
        GolemArmorEffects(entity, armor);
        EverseerHelmEffects(entity, armor);
    }

    private static void RoseGoldArmorEffects(LivingEntity entity, ArrayList<ItemStack> armor) {
        if (entity instanceof PlayerEntity player) {
            //rose gold
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if(att != null) {
                double rose_gold_speed = 0;
                if (RoseGoldDamageUtil.boolDamageRoseGold(player, armor.get(1), AVItems.ROSE_GOLD_LEGGINGS, 1, 1, true, false)){
                    rose_gold_speed+=1;
                }
                EntityAttributeModifier mod = new EntityAttributeModifier(ROSE_GOLD_SPEED_ID, "ASTROVarietyRoseGoldSpeed",
                        rose_gold_speed, EntityAttributeModifier.Operation.MULTIPLY_BASE);
                ReplaceAttributeModifier(att, mod);
            }
            if (RoseGoldDamageUtil.boolDamageRoseGold(player, armor.get(2), AVItems.ROSE_GOLD_CHESTPLATE, 1, 2, true, false)
                    && !entity.hasStatusEffect(StatusEffects.ABSORPTION)) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 150, 0, true, false, true));
                entity.heal(1);
                RoseGoldDamageUtil.damageRoseGoldStack(player, armor.get(2), AVItems.ROSE_GOLD_CHESTPLATE, 1, 2);
            }

            att = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
            if (att != null) {
                double rose_gold_attack = 0;
                if (RoseGoldDamageUtil.boolDamageRoseGold(player, armor.get(2), AVItems.ROSE_GOLD_CHESTPLATE, 3, 3, true, false)){
                    rose_gold_attack = 0.25;
                }
                EntityAttributeModifier mod = new EntityAttributeModifier(ROSE_GOLD_ATTACK_ID, "ASTROVarietyRoseGoldAttack",
                        rose_gold_attack, EntityAttributeModifier.Operation.MULTIPLY_BASE);
                ReplaceAttributeModifier(att, mod);
            }

            if (RoseGoldDamageUtil.boolDamageRoseGold(player, armor.get(1), AVItems.ROSE_GOLD_LEGGINGS, 3, 1, true, false)){
                player.stepHeight = 1.5f;
            }
            else {
                player.stepHeight = 0.6F;
            }
        }
    }

    private static void CaneArmorEffects(LivingEntity entity, ArrayList<ItemStack> armor) {
        EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if(att != null) {
            float SPEED = 0;
            if (armor.get(0).getItem() == AVItems.CANE_BOOTS) { SPEED+=0.5; }
            if (armor.get(1).getItem() == AVItems.CANE_LEGGINGS) { SPEED+=0.5; }
            EntityAttributeModifier mod = new EntityAttributeModifier(CANE_SPEED_ID, "ASTROVarietyCaneSpeed",
                    SPEED, EntityAttributeModifier.Operation.MULTIPLY_BASE);
            ReplaceAttributeModifier(att, mod);
        }
    }

    private static void GolemArmorEffects(LivingEntity entity, ArrayList<ItemStack> armor) {
        EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if(att != null) {
            float HEALTH = 0;
            if (armor.get(2).getItem() == AVItems.GOLEM_CHESTPLATE) { HEALTH+=4; }
            if (armor.get(3).getItem() == AVItems.GOLEM_HELMET) { HEALTH+=2; }
            EntityAttributeModifier mod = new EntityAttributeModifier(GOLEM_HEALTH_ID, "ASTROVarietyGolemHealth",
                    HEALTH, EntityAttributeModifier.Operation.ADDITION);
            ReplaceAttributeModifier(att, mod);
        }
    }

    private static void EverseerHelmEffects(LivingEntity entity, ArrayList<ItemStack> armor) {
        if (entity instanceof PlayerEntity player) {
            //everseer
            //List<Entity> list = world.getOtherEntities(player, new Box(player.getX()-50, player.getY()-50, player.getZ()-50, player.getX()+50, player.getY()+50, player.getZ()+50));
            //for(int v = 0; v < list.size(); ++v) {
            //Entity e = (Entity) list.get(v);
            if (armor.get(3).getItem() == AVItems.SEER_HELMET) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 2, 0, true, false, false));
            }
        }
    }

    private static void ReplaceAttributeModifier(EntityAttributeInstance att, EntityAttributeModifier mod)
    {
        att.removeModifier(mod);
        att.addPersistentModifier(mod);
    }
}
