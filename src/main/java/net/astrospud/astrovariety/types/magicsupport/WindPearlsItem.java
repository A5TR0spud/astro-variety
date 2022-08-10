package net.astrospud.astrovariety.types.magicsupport;

import com.ibm.icu.text.MessagePattern;
import net.astrospud.astrovariety.registry.AVStatusEffects;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WindPearlsItem extends ToggleItem {

    public WindPearlsItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public void specialTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && toggle
                && !player.getItemCooldownManager().isCoolingDown(stack.getItem())) {

            player.getItemCooldownManager().set(stack.getItem(), 100);

            int count = 0;
            for (int i = 0; i < player.getInventory().size(); i++) {
                if (player.getInventory().getStack(i).getItem() == stack.getItem()) {
                    count ++;
                }
            }
            player.addStatusEffect(new StatusEffectInstance(AVStatusEffects.WIND_MOBILITTY, 100, count-1, false, false, true));
        }
        if (entity instanceof PlayerEntity player && toggle) {
            if (player.fallDistance > player.getSafeFallDistance()*0.90f) {
                BlockHitResult cast = world.raycast(new RaycastContext(
                        new Vec3d(player.getPos().x, player.getPos().y, player.getPos().z),
                        new Vec3d(player.getPos().x, player.getPos().y-3, player.getPos().z),
                        RaycastContext.ShapeType.COLLIDER,
                        RaycastContext.FluidHandling.NONE,
                        player
                ));
                if (cast.getType() == HitResult.Type.BLOCK &&
                        !(Fluids.WATER == world.getFluidState(cast.getBlockPos().up()).getFluid()
                        || Fluids.FLOWING_WATER == world.getFluidState(cast.getBlockPos().up()).getFluid())) {
                    player.setVelocity(player.getVelocity().x, player.getVelocity().y*0.5, player.getVelocity().z);
                    player.playSound(SoundEvents.BLOCK_WOOL_FALL, 1, 1);

                    /*if (world.isClient()) {
                        ParticleEffect particle = ParticleTypes.FLAME;
                        world.addParticle(particle, player.getX(), player.getY(), player.getZ(), 0, 0, 0);
                    }*/

                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 25, 64, false, false, true));
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.astrovariety.wind_pearls_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.astrovariety.wind_pearls_2").formatted(Formatting.GRAY));
    }
}
