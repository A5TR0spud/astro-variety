package net.astrospud.astrovariety.mixin;

import com.mojang.authlib.GameProfile;
import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.util.AVUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Arm;
import net.minecraft.util.dynamic.DynamicSerializableUuid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class AVPlayerEntityMixin extends LivingEntity{

    protected AVPlayerEntityMixin(EntityType<? extends PlayerEntity> entityType, World world){
        super(entityType, world);
    }

    /*@ModifyVariable(at = @At("RETURN"), ordinal = 0, method = "getBlockBreakingSpeed")
    public float AVgetBlockBreakingSpeedMixin(float f, BlockState block) {
        if ((Object)this instanceof PlayerEntity entity) {
            Iterable<ItemStack> armorItems = entity.getArmorItems();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armorItems.forEach(armor::add);
            if (AVUtil.boolDamageRoseGold(entity, armor.get(2), AVItems.ROSE_GOLD_CHESTPLATE, 2, 1, true, true)) {
                f *= 20f;
            }
        }
        return f;
    }*/

    @Inject(at = @At("RETURN"), method = "getBlockBreakingSpeed", cancellable = true)
    void AVgetBlockBreakingSpeedMixin(BlockState block, CallbackInfoReturnable<Float> cir) {
        float f = 1;
        if ((Object)this instanceof PlayerEntity entity) {
            Iterable<ItemStack> armorItems = entity.getArmorItems();
            ArrayList<ItemStack> armor = new ArrayList<>();
            armorItems.forEach(armor::add);
            if (AVUtil.boolDamageRoseGold(entity, armor.get(2), AVItems.ROSE_GOLD_CHESTPLATE, 2, 1, true, true)) {
                f = 1.35f;
            }
        }
        cir.setReturnValue(cir.getReturnValue()*f);
    }
}
