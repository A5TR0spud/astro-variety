package net.astrospud.astrovariety.mixin;

import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.util.AVUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class AVLivingEntityMixin extends Entity {

    private static final UUID CANE_SPEED_ID = UUID.fromString("4577cf8a-3647-43ce-9128-71a022d5026f");
    private static final UUID GOLEM_HEALTH_ID = UUID.fromString("095f18a8-2a96-4558-9ff7-7680375309d0");
    private static final UUID DECAY_STRENGTH_ID = UUID.fromString("3bce0b01-fdaf-4cb8-a725-7540db55c34c");
    private static final UUID ROSE_GOLD_SPEED_ID = UUID.fromString("1fd7c69e-1686-4b16-b85f-220495594263");
    private static final UUID ROSE_GOLD_ATTACK_ID = UUID.fromString("12ce7946-891d-4678-b003-414d77a1a459");

    private static boolean HAS_DECAY = false;


    private BlockPos oldPos = this.getBlockPos();

    private static int roseGoldLegTick = 0;

    protected AVLivingEntityMixin(EntityType<? extends Entity> entityType, World world){
        super(entityType, world);
    }

    @Inject(at = @At(value = "HEAD"), method = "damage", cancellable = true)
    public void avDamageMixin(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        boolean shouldDamage = true;
        if ((Object) this instanceof LivingEntity entity) {
            Iterable<ItemStack> armorItems = this.getArmorItems();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armorItems.forEach(armor::add);

            //cactus
            if (source.getAttacker() instanceof LivingEntity attacker) {
                int strength = 0;
                if (armor.get(2).getItem() == AVItems.CACTUS_CHESTPLATE) {
                    strength++;
                }
                if (strength > 0) {
                    attacker.damage(DamageSource.CACTUS, strength);
                }
            }

            armorItems.forEach(armor::add);
            //blaze
            float res = 0;
            float resMax = 2;
            if (armor.get(0).getItem() == AVItems.BLAZE_BOOTS) {
                res++;
                if (Objects.equals(source.getName(), "hotFloor")) {
                    shouldDamage = false;
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
                shouldDamage = false;
            }

            //talisman of decay
            if (HAS_DECAY && (this.getWorld().random.nextFloat() >= 0.75F || source.getName() == "wither")) {
                shouldDamage = false;
            }
        }

        if (!shouldDamage) {
            shouldDamage = true;
            cir.setReturnValue(false);
        }
    }


    @ModifyVariable(at = @At("HEAD"), ordinal = 0, method = "damage")
    public float avVarDamageMixin(float finalAmount, DamageSource source, float amount) {
        if ((Object) this instanceof PlayerEntity entity && HAS_DECAY) {
            finalAmount = (float)Math.ceil(finalAmount * 0.75);
        }
        return finalAmount;
    }

    @Inject(at = @At(value = "HEAD"), method = "tick")
    public void avtick(CallbackInfo cir){
        if ((Object) this instanceof LivingEntity entity) {
            Iterable<ItemStack> armorItems = entity.getArmorItems();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armorItems.forEach(armor::add);

            if (entity instanceof PlayerEntity player) {
                //rose gold
                EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                if(att != null) {
                    double rose_gold_speed = 0;
                    if (AVUtil.boolDamageRoseGold(player, armor.get(1), AVItems.ROSE_GOLD_LEGGINGS, 1, 1, true, false)){
                        rose_gold_speed+=1;
                    }
                    EntityAttributeModifier mod = new EntityAttributeModifier(ROSE_GOLD_SPEED_ID, "ASTROVarietyRoseGoldSpeed",
                            rose_gold_speed, EntityAttributeModifier.Operation.MULTIPLY_BASE);
                    ReplaceAttributeModifier(att, mod);
                }
                /*if (AVUtil.boolDamageRoseGold(player, armor.get(2), AVItems.ROSE_GOLD_CHESTPLATE, 1, 2, true, false)
                        && !entity.hasStatusEffect(StatusEffects.ABSORPTION)) {
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 150, 0, true, false, true));
                    entity.heal(1);
                    AVUtil.damageRoseGoldStack(player, armor.get(2), AVItems.ROSE_GOLD_CHESTPLATE, 1, 2);
                }*/

                att = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
                if (att != null) {
                    double rose_gold_attack = 0;
                    if (AVUtil.boolDamageRoseGold(player, armor.get(2), AVItems.ROSE_GOLD_CHESTPLATE, 3, 2, true, true)){
                        rose_gold_attack = 0.25;
                    }
                    EntityAttributeModifier mod = new EntityAttributeModifier(ROSE_GOLD_ATTACK_ID, "ASTROVarietyRoseGoldAttack",
                            rose_gold_attack, EntityAttributeModifier.Operation.MULTIPLY_BASE);
                    ReplaceAttributeModifier(att, mod);
                }

                if (AVUtil.boolDamageRoseGold(player, armor.get(1), AVItems.ROSE_GOLD_LEGGINGS, 3, 1, true, false)){
                    player.stepHeight = 1.5f;
                }
                else {
                    player.stepHeight = 0.6F;
                }
            }

            //cane
            EntityAttributeInstance att = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if(att != null) {
                float SPEED = 0;
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
                if (armor.get(2).getItem() == AVItems.GOLEM_CHESTPLATE) { HEALTH+=4; }
                if (armor.get(3).getItem() == AVItems.GOLEM_HELMET) { HEALTH+=2; }
                EntityAttributeModifier mod = new EntityAttributeModifier(GOLEM_HEALTH_ID, "ASTROVarietyGolemHealth",
                        HEALTH, EntityAttributeModifier.Operation.ADDITION);
                ReplaceAttributeModifier(att, mod);
            }

            if (entity instanceof PlayerEntity player) {
                //everseer
                //List<Entity> list = world.getOtherEntities(player, new Box(player.getX()-50, player.getY()-50, player.getZ()-50, player.getX()+50, player.getY()+50, player.getZ()+50));
                //for(int v = 0; v < list.size(); ++v) {
                    //Entity e = (Entity) list.get(v);
                if (armor.get(3).getItem() == AVItems.SEER_HELMET) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 2, 0, true, false, false));
                }

                //talisman of decay
                for (int i = 0; i < player.getInventory().size(); i++) {
                    if (player.getInventory().getStack(i).getItem() == AVItems.TALISMAN_OF_DECAY) {
                        HAS_DECAY = true;
                        break;
                    }
                    else {
                        HAS_DECAY = false;
                    }
                }
                float DMG = 0;
                if (HAS_DECAY) {
                    DMG = -32768;
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 2, 0, true, false, false));
                }
                att = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                if(att != null) {
                    EntityAttributeModifier mod = new EntityAttributeModifier(DECAY_STRENGTH_ID, "ASTROVarietyDecayWeakness",
                            DMG, EntityAttributeModifier.Operation.ADDITION);
                    ReplaceAttributeModifier(att, mod);
                }
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "getJumpVelocity",cancellable = true)
    public void AVLivingEntityJumpVelocityMixin(CallbackInfoReturnable<Float> info){
        if ((Object)this instanceof PlayerEntity entity) {
            Iterable<ItemStack> armorItems = entity.getArmorItems();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armorItems.forEach(armor::add);
            if (AVUtil.boolDamageRoseGold(entity, armor.get(1), AVItems.ROSE_GOLD_LEGGINGS, 2, 3, true, true)){
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
                AVUtil.damageRoseGoldStack(entity, armor.get(1), AVItems.ROSE_GOLD_LEGGINGS, 1, 1);
            }
            roseGoldLegTick++;
        }
        if (this.getBlockPos() != oldPos && (Object)this instanceof PlayerEntity entity) {
            if (roseGoldLegTick > 36) {
                roseGoldLegTick = 0;
                Iterable<ItemStack> armorItems = entity.getArmorItems();
                ArrayList<ItemStack> armor = new ArrayList<>();
                armorItems.forEach(armor::add);
                AVUtil.damageRoseGoldStack(entity, armor.get(1), AVItems.ROSE_GOLD_LEGGINGS, 3, 1);
            }
            roseGoldLegTick++;
        }
        oldPos = this.getBlockPos();
    }

    private static void ReplaceAttributeModifier(EntityAttributeInstance att, EntityAttributeModifier mod)
    {
        //removes any existing mod and replaces it with the updated one.
        att.removeModifier(mod);
        att.addPersistentModifier(mod);
    }
}
