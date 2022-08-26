package net.astrospud.astrovariety.types.magicsupport.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import java.util.Map;

public class InkWellItem extends MagicSupportItem {
    public InkWellItem() {
        super(new FabricItemSettings().group(ItemGroup.TOOLS).fireproof().maxCount(1).rarity(Rarity.UNCOMMON));
    }

    /*@Override
    public void specialTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected, int count) {
        //player.getItemCooldownManager().set(stack.getItem(), 50);
    }*/
}
