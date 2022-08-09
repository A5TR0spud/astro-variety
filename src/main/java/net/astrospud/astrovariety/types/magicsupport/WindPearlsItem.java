package net.astrospud.astrovariety.types.magicsupport;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 100, count-1, false, false, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, count-1, false, false, true));
        }
        if (entity instanceof PlayerEntity player && toggle) {
            if (player.fallDistance > player.getSafeFallDistance()*0.90f) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 19, 128, false, false, true));
                //player.addVelocity(0D, 0.5D, 0D);
                //player.setVelocity(new Vec3d(player.getVelocity().x, player.getVelocity().y * 0.1D, player.getVelocity().z));
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
