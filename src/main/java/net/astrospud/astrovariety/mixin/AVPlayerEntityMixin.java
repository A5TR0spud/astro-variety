package net.astrospud.astrovariety.mixin;

import net.astrospud.astrovariety.listeners.OnDamageCallback;
import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.types.utils.RoseGoldDamageUtil;
import net.astrospud.astrovariety.types.utils.Utils;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(PlayerEntity.class)
public abstract class AVPlayerEntityMixin extends LivingEntity{
    protected AVPlayerEntityMixin(EntityType<? extends PlayerEntity> entityType, World world){
        super(entityType, world);
    }

    @ModifyVariable(at = @At(value = "CONSTANT", args = "floatValue=0.0F"), method = "applyDamage", name = "amount")
    public float avPlayerApplyDamageMixin(float amount, DamageSource source) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        return Utils.applyDamageEffects(player, amount, source);
    }

    @Inject(at = @At("RETURN"), method = "getBlockBreakingSpeed", cancellable = true)
    void AVgetBlockBreakingSpeedMixin(BlockState block, CallbackInfoReturnable<Float> cir) {
        float f = 1;
        if ((Object)this instanceof PlayerEntity entity) {
            Iterable<ItemStack> armorItems = entity.getArmorItems();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armorItems.forEach(armor::add);
            if (RoseGoldDamageUtil.boolDamageRoseGold(entity, armor.get(2), AVItems.ROSE_GOLD_CHESTPLATE, 2, 1, true, true)) {
                f = 4f;
            }
        }
        cir.setReturnValue(cir.getReturnValue()*f);
    }

    @Inject(at = @At("HEAD"), method = "eatFood")
    public void aveatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (stack.getItem() == Items.GLOW_BERRIES) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0, false, true, true));
        }
    }
}
