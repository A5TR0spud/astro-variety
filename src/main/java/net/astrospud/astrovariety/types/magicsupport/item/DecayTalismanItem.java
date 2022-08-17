package net.astrospud.astrovariety.types.magicsupport.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DecayTalismanItem extends Item {
    public DecayTalismanItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1));
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.astrovariety.decay_desc_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.astrovariety.decay_desc_2").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.astrovariety.decay_quote_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("tooltip.astrovariety.decay_quote_2").formatted(Formatting.GRAY));
    }
}
