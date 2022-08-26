package net.astrospud.astrovariety.types.magicsupport.item;

import net.astrospud.astrovariety.registry.AVStatusEffects;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NanomachineItem extends MagicSupportItem {
    public NanomachineItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int count) {
        if (player.getHealth() < player.getMaxHealth()
        && !player.hasStatusEffect(AVStatusEffects.NANOMACHINES)) {

            player.getItemCooldownManager().set(stack.getItem(), 20);
            player.addStatusEffect(new StatusEffectInstance(AVStatusEffects.NANOMACHINES,20, count-1, false, false, true));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.astrovariety.nanomachine_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.astrovariety.nanomachine_2").formatted(Formatting.GRAY));
    }
}
