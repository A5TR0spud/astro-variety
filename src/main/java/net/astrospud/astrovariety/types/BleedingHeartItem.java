package net.astrospud.astrovariety.types;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BleedingHeartItem extends ToggleItem {
    public BleedingHeartItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public void specialTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && player.getHealth() < player.getMaxHealth()
        && !player.getItemCooldownManager().isCoolingDown(stack.getItem())
        && toggle) {
            player.getItemCooldownManager().set(stack.getItem(), 20);
            player.heal(1);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,20, 0, false, false, true));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.astrovariety.bleeding_heart_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.astrovariety.bleeding_heart_2").formatted(Formatting.GRAY));
    }
}
