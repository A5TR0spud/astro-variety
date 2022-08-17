package net.astrospud.astrovariety.types.magicsupport.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoldPillItem extends ToggleItem {
    public GoldPillItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public void specialTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && player.getHungerManager().isNotFull()
        && !player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            player.getItemCooldownManager().set(stack.getItem(), 150);
            int count = 0;
            for (int i = 0; i < player.getInventory().size(); i++) {
                if (player.getInventory().getStack(i).getItem() == stack.getItem()) {
                    count ++;
                }
            }

            player.getHungerManager().add(count, 0.5f*count);

            if (count == 1) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 75, 0, false, false, true));
            }
            else if (count == 2) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 150, 0, false, false, true));
            }
            else if (count == 3) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 75, 0, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 75, 0, false, false, true));
            }
            else if (count == 4) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 75, 0, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 150, 0, false, false, true));
            }
            else if (count == 5) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 75, 0, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 75, 0, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 75, 0, false, false, true));
            }
            else if (count == 6) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 75, 0, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 75, 0, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 150, 0, false, false, true));
            }
            else if (count >= 7) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 75, count-7, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 75, count-7, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 150, count-7, false, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 75, count-7, false, false, true));
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.astrovariety.gold_pill_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.astrovariety.gold_pill_2").formatted(Formatting.GRAY));
    }
}
