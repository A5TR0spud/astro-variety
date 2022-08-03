package net.astrospud.astrovariety.types;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WindPearlsItem extends ToggleItem {
    String[] speeds = new String[]{"slow", "none", "speed"};
    String[] winds = new String[]{"x+", "x-", "z+", "z-", "none"};

    public WindPearlsItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public void specialTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && toggle
                && !player.getItemCooldownManager().isCoolingDown(stack.getItem())) {

            player.getItemCooldownManager().set(stack.getItem(), 100);

            NbtCompound nbtCompound = stack.getOrCreateNbt();

            String speedIndex = stack.getOrCreateNbt().getString("Speed");
            int max = speeds.length;
            nbtCompound.putString("Speed", speeds[(int) (Math.random() * max)]);

            String windIndex = stack.getOrCreateNbt().getString("Wind");
            max = winds.length;
            nbtCompound.putString("Wind", winds[(int) (Math.random() * max)]);

            if (speedIndex == "slow") {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 0, false, false, true));
            }
            if (speedIndex == "speed") {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100, 0, false, false, true));
            }
        }
        if (entity instanceof PlayerEntity player) {
            String windIndex = stack.getOrCreateNbt().getString("Wind");

            if (!(player.getAbilities().flying && (player.isCreative() || player.isSpectator()))) {
                if (windIndex == "x+") {
                    player.addVelocity(0.02, 0, 0);
                }
                if (windIndex == "x-") {
                    player.addVelocity(-0.02, 0, 0);
                }
                if (windIndex == "z+") {
                    player.addVelocity(0, 0, 0.02);
                }
                if (windIndex == "z-") {
                    player.addVelocity(0, 0, -0.02);
                }

                player.addVelocity(0, 0.02, 0);
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
