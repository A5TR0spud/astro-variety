package net.astrospud.astrovariety.mixin;

import net.astrospud.astrovariety.registry.AVItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class AVLivingEntityMixin extends Entity {

    private static final UUID CANE_SPEED_ID = UUID.fromString("4577cf8a-3647-43ce-9128-71a022d5026f");
    private static final UUID GOLEM_HEALTH_ID = UUID.fromString("095f18a8-2a96-4558-9ff7-7680375309d0");

    protected AVLivingEntityMixin(EntityType<? extends Entity> entityType, World world){
        super(entityType, world);
    }

    @Inject(at = @At(value = "HEAD"), method = "damage")
    public void avdamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        Iterable<ItemStack> armorItems = this.getArmorItems();
        ArrayList<ItemStack> armor = new ArrayList<>();
        armorItems.forEach(armor::add);

        //cactus
        if (source.getAttacker() instanceof LivingEntity attacker) {
            int strength = 0;
            if (armor.get(2).getItem() == AVItems.CACTUS_CHESTPLATE) { strength++; }
            if (strength > 0) {
                attacker.damage(DamageSource.CACTUS, strength);
            }
        }
    }

    @ModifyVariable(at = @At("HEAD"), ordinal = 0, method = "damage")
    public float avdamagevar(float finalAmount, DamageSource source, float amount){
        if ((Object) this instanceof LivingEntity liveEntity) {
            Iterable<ItemStack> armorItems = this.getArmorItems();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armorItems.forEach(armor::add);
            //blaze
            float res = 0;
            float resMax = 2;
            if (armor.get(0).getItem() == AVItems.BLAZE_BOOTS) {
                res++;
                if (Objects.equals(source.getName(), "hotFloor")) {
                    return 0;
                }
            }
            if (armor.get(3).getItem() == AVItems.BLAZE_HAT) {
                res++;
            }
            if (res > resMax) {
                res = resMax;
            }
            boolean doRes = this.getWorld().random.nextFloat() > 0.5F * (res / resMax);
            if (res > 0 && source.isFire() && doRes && !liveEntity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
                return 0;
            }
        }
        return finalAmount;
    }

    @Inject(at = @At(value = "HEAD"), method = "tick")
    public void avtick(CallbackInfo cir){
        if ((Object) this instanceof LivingEntity entity) {
            //cane
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if(att != null) {
                float SPEED = 0;
                Iterable<ItemStack> armorItems = entity.getArmorItems();
                ArrayList<ItemStack> armor = new ArrayList<>();
                armorItems.forEach(armor::add);
                if (armor.get(0).getItem() == AVItems.CANE_BOOTS) { SPEED+=0.5; }
                if (armor.get(1).getItem() == AVItems.CANE_LEGGINGS) { SPEED+=0.5; }
                EntityAttributeModifier mod = new EntityAttributeModifier(CANE_SPEED_ID, "ASTROVarietyCaneSpeed",
                        SPEED, EntityAttributeModifier.Operation.MULTIPLY_BASE);
                ReplaceAttributeModifier(att, mod);
            }
            //golem
            att = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if(att != null) {
                float HEALTH = 0;
                Iterable<ItemStack> armorItems = entity.getArmorItems();
                ArrayList<ItemStack> armor = new ArrayList<>();
                armorItems.forEach(armor::add);
                if (armor.get(2).getItem() == AVItems.GOLEM_CHESTPLATE) { HEALTH+=4; }
                if (armor.get(3).getItem() == AVItems.GOLEM_HELMET) { HEALTH+=2; }
                EntityAttributeModifier mod = new EntityAttributeModifier(GOLEM_HEALTH_ID, "ASTROVarietyGolemHealth",
                        HEALTH, EntityAttributeModifier.Operation.ADDITION);
                ReplaceAttributeModifier(att, mod);
            }
        }
    }

    private static void ReplaceAttributeModifier(EntityAttributeInstance att, EntityAttributeModifier mod)
    {
        //removes any existing mod and replaces it with the updated one.
        att.removeModifier(mod);
        att.addPersistentModifier(mod);
    }
}
