package net.astrospud.astrovariety.types;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SpecialItem extends Item {

    private boolean GLINT = false;

    public SpecialItem(Settings settings, boolean hasGlint) {
        super(settings);
        GLINT = hasGlint;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return GLINT;
    }
}
