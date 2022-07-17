package net.astrospud.astrovariety.registry;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DecayItem extends Item {
    public DecayItem(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.astrovariety.decay_desc_1").setStyle(Style.EMPTY.withColor(0xbbaacc)));
        tooltip.add(Text.translatable("tooltip.astrovariety.decay_desc_2").setStyle(Style.EMPTY.withColor(0xbbaacc)));
        tooltip.add(Text.translatable("tooltip.astrovariety.decay_quote_1").setStyle(Style.EMPTY.withItalic(true).withColor(0xbbaacc)));
        tooltip.add(Text.translatable("tooltip.astrovariety.decay_quote_2").setStyle(Style.EMPTY.withItalic(true).withColor(0xbbaacc)));
    }
}
