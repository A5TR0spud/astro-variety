package net.astrospud.astrovariety.types;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TalismanItem extends Item {
    private static String TALISMAN = "";
    public TalismanItem(String type) {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1));
        TALISMAN = type;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (TALISMAN.toLowerCase() == "decay") {
            tooltip.add(Text.translatable("tooltip.astrovariety.decay_desc_1").setStyle(Style.EMPTY.withColor(0xbbaacc)));
            tooltip.add(Text.translatable("tooltip.astrovariety.decay_desc_2").setStyle(Style.EMPTY.withColor(0xbbaacc)));
            tooltip.add(Text.translatable("tooltip.astrovariety.decay_quote_1").setStyle(Style.EMPTY.withItalic(true).withColor(0xbbaacc)));
            tooltip.add(Text.translatable("tooltip.astrovariety.decay_quote_2").setStyle(Style.EMPTY.withItalic(true).withColor(0xbbaacc)));
        }
    }
}
