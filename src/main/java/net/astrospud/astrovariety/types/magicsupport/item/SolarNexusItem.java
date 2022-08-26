package net.astrospud.astrovariety.types.magicsupport.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

public class SolarNexusItem extends MagicSupportItem{
    public SolarNexusItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int count) {
        if (world.isDay() && !player.hasStatusEffect(StatusEffects.REGENERATION)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, count-1, false, false, true));
        } else if (world.isNight() && !player.hasStatusEffect(StatusEffects.WEAKNESS)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200, count-1, false, false, true));
        }
    }
}
