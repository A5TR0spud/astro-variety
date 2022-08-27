package net.astrospud.astrovariety.mixin;

import net.astrospud.astrovariety.listeners.OnDamageCallback;
import net.astrospud.astrovariety.listeners.OnTickCallback;
import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVStatusEffects;
import net.astrospud.astrovariety.types.magicsupport.item.MagicSupportItem;
import net.astrospud.astrovariety.types.utils.RoseGoldDamageUtil;
import net.astrospud.astrovariety.types.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
    private BlockPos oldPos = this.getBlockPos();

    private static int roseGoldLegTick = 0;

    protected AVLivingEntityMixin(EntityType<? extends Entity> entityType, World world){
        super(entityType, world);
    }

    @Inject(at = @At(value = "RETURN"), method = "getJumpBoostVelocityModifier", cancellable = true)
    public void avgetJumpBoostVelocityModifierMixin(CallbackInfoReturnable<Double> cir) {
        if ((Object) this instanceof LivingEntity entity) {
            double ffg = entity.hasStatusEffect(AVStatusEffects.WIND_MOBILITTY) ? (double) (0.1F * (float) (entity.getStatusEffect(AVStatusEffects.WIND_MOBILITTY).getAmplifier() + 1)) : 0.0;
            cir.setReturnValue(cir.getReturnValue()+ffg);
        }
    }

    /*@Inject(at = @At(value = "RETURN"), method = "onAttacking")
    public void avOnAttackingMixin(Entity target, CallbackInfo cir) {
        if ((Object) this instanceof PlayerEntity player) {
            OnDamageCallback.EVENT.invoker().onOrganTick(cc.owner, cc);
        }
    }*/

    @Inject(at = @At(value = "RETURN"), method = "computeFallDamage", cancellable = true)
    protected void avcomputeFallDamageMixin(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Integer> cir) {
        if ((Object) this instanceof LivingEntity entity) {
            StatusEffectInstance statusEffectInstance = entity.getStatusEffect(AVStatusEffects.WIND_MOBILITTY);
            float f = statusEffectInstance == null ? 0.0F : (float) (statusEffectInstance.getAmplifier() + 1);
            cir.setReturnValue(MathHelper.ceil(((((float)cir.getReturnValue()-0.5f)/damageMultiplier) - f) * damageMultiplier));
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "damage", cancellable = true)
    public void avDamageMixin(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        OnDamageCallback.EVENT.invoker().onDamageTick((LivingEntity)(Object)this, source, amount);

        if ((Object)this instanceof TameableEntity tame && tame.getOwner() == source.getAttacker() && tame.getOwner() != null) {
            cir.setReturnValue(false);
            cir.cancel();
        }

        if ((Object) this instanceof LivingEntity entity) {
            Iterable<ItemStack> armorItems = this.getArmorItems();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armorItems.forEach(armor::add);

            //blaze
            float res = 0;
            float resMax = 2;
            if (armor.get(0).getItem() == AVItems.BLAZE_BOOTS) {
                res++;
                if (Objects.equals(source.getName(), "hotFloor")) {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            }
            if (armor.get(3).getItem() == AVItems.BLAZE_HAT) {
                res++;
            }
            if (res > resMax) {
                res = resMax;
            }
            boolean doRes = this.getWorld().random.nextFloat() >= 0.375F / (res / resMax);
            if (res > 0 && source.isFire() && doRes) {
                cir.setReturnValue(false);
                cir.cancel();
            }

            //talisman of decay
            if (entity instanceof PlayerEntity player && Utils.getItemCount(AVItems.TALISMAN_OF_DECAY, player) > 0 && (source.getName() == "wither")) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "tick")
    public void avtick(CallbackInfo cir){
        LivingEntity entity = (LivingEntity)(Object)this;
        OnTickCallback.EVENT.invoker().onTick(entity);
    }

    @Inject(at = @At("RETURN"), method = "getJumpVelocity",cancellable = true)
    public void AVLivingEntityJumpVelocityMixin(CallbackInfoReturnable<Float> info){
        if ((Object)this instanceof PlayerEntity entity) {
            Iterable<ItemStack> armorItems = entity.getArmorItems();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armorItems.forEach(armor::add);
            if (RoseGoldDamageUtil.boolDamageRoseGold(entity, armor.get(1), AVItems.ROSE_GOLD_LEGGINGS, 2, 3, true, true)){
                info.setReturnValue(info.getReturnValueF()*1.5f);
            }
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "tickMovement")
    public void AVTickMovementMixin(CallbackInfo info) {
        if (this.getBlockPos() != oldPos && (Object)this instanceof PlayerEntity entity) {
            if (roseGoldLegTick > 9) {
                roseGoldLegTick = 0;
                Iterable<ItemStack> armorItems = entity.getArmorItems();
                ArrayList<ItemStack> armor = new ArrayList<>();
                armorItems.forEach(armor::add);
                RoseGoldDamageUtil.damageRoseGoldStack(entity, armor.get(1), AVItems.ROSE_GOLD_LEGGINGS, 1, 1);
            }
            roseGoldLegTick++;
        }
        if (this.getBlockPos() != oldPos && (Object)this instanceof PlayerEntity entity) {
            if (roseGoldLegTick > 36) {
                roseGoldLegTick = 0;
                Iterable<ItemStack> armorItems = entity.getArmorItems();
                ArrayList<ItemStack> armor = new ArrayList<>();
                armorItems.forEach(armor::add);
                RoseGoldDamageUtil.damageRoseGoldStack(entity, armor.get(1), AVItems.ROSE_GOLD_LEGGINGS, 3, 1);
            }
            roseGoldLegTick++;
        }
        oldPos = this.getBlockPos();
    }
}
