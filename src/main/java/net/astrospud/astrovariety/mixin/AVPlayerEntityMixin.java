package net.astrospud.astrovariety.mixin;

import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.types.utils.RoseGoldDamageUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(PlayerEntity.class)
public abstract class AVPlayerEntityMixin extends LivingEntity{
    protected AVPlayerEntityMixin(EntityType<? extends PlayerEntity> entityType, World world){
        super(entityType, world);
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
}
