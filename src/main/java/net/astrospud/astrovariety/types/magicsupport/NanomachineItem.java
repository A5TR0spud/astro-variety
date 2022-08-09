package net.astrospud.astrovariety.types.magicsupport;

import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVStatusEffects;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NanomachineItem extends ToggleItem {
    public NanomachineItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public void specialTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && player.getHealth() < player.getMaxHealth()
        && !player.getItemCooldownManager().isCoolingDown(stack.getItem())
        && toggle && !player.hasStatusEffect(AVStatusEffects.NANOMACHINES)) {
            int count = -1;
            for (int i = 0; i < player.getInventory().size(); i++) {
                if (player.getInventory().getStack(i).getItem() == stack.getItem()) {
                    count ++;
                }
            }
            if (count < 0) {count = 0;}

            player.getItemCooldownManager().set(stack.getItem(), 20);
            player.addStatusEffect(new StatusEffectInstance(AVStatusEffects.NANOMACHINES,20, count, false, false, true));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.astrovariety.nanomachine_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.astrovariety.nanomachine_2").formatted(Formatting.GRAY));
    }
}
