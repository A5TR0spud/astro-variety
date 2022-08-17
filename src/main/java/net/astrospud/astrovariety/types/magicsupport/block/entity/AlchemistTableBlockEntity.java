package net.astrospud.astrovariety.types.magicsupport.block.entity;

import net.astrospud.astrovariety.registry.AVBlockEntities;
import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.screen.AlchemistTableScreenHandler;
import net.astrospud.astrovariety.types.utils.inventory.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AlchemistTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(4, ItemStack.EMPTY);

    public AlchemistTableBlockEntity(BlockPos pos, BlockState state) {
        super(AVBlockEntities.ALCHEMIST_TABLE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.astrovariety.alchemist_table");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new AlchemistTableScreenHandler(syncId, inv, this);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
    }

    public static void tick(World world, BlockPos pos, BlockState state, AlchemistTableBlockEntity entity) {
        if(hasRecipe(entity) && hasNotReachedStackLimit(entity)) {
            craftItem(entity);
        }
    }

    private static void craftItem(AlchemistTableBlockEntity entity) {
        entity.removeStack(0, 1);
        entity.removeStack(1, 1);
        entity.removeStack(2, 1);

        entity.setStack(3, new ItemStack(AVItems.ALCHEMY_CATALYST,
                entity.getStack(3).getCount() + 1));
    }

    private static boolean hasRecipe(AlchemistTableBlockEntity entity) {
        boolean hasItemInFirstSlot = entity.getStack(0).getItem() == Items.BLAZE_POWDER;
        boolean hasItemInSecondSlot = entity.getStack(1).getItem() == Items.NETHER_WART;
        boolean hasItemInThirdSlot = entity.getStack(2).getItem() == Items.NETHER_WART;

        return hasItemInFirstSlot && hasItemInSecondSlot && hasItemInThirdSlot;
    }

    private static boolean hasNotReachedStackLimit(AlchemistTableBlockEntity entity) {
        return entity.getStack(3).getCount() < entity.getStack(3).getMaxCount();
    }
}
