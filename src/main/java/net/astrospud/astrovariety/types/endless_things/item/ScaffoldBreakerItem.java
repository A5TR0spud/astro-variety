package net.astrospud.astrovariety.types.endless_things.item;

import net.astrospud.astrovariety.registry.AVProperties;
import net.astrospud.astrovariety.types.utils.AVScaffoldRemoveData;
import net.astrospud.astrovariety.types.utils.AVScaffoldRemoveMatrix;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScaffoldBreakerItem extends Item {
    static AVScaffoldRemoveData data = new AVScaffoldRemoveData();
    public ScaffoldBreakerItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        removeNextInQueue();
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        removeScaffold(state, pos, world);
        return super.postMine(stack, world, state, pos, miner);
    }

    /*@Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        data.clearData();
        return super.use(world, user, hand);
    }*/

    /*@Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("tooltip.astrovariety.scaffold_breaker").formatted(Formatting.GRAY));
    }*/

    public void removeScaffold(BlockState state, BlockPos pos, World world) {
        Block block = state.getBlock();
        if (block == Blocks.SCAFFOLDING) {
            if (!data.containsPos(pos)) {
                data.add(state, pos, world);
                removeScaffold(pos.up(), world);
            }
            if (world.getBlockState(pos.up()).getBlock() != Blocks.SCAFFOLDING) {
                singleRemove(pos.east(), world);
                singleRemove(pos.west(), world);
                singleRemove(pos.north(), world);
                singleRemove(pos.south(), world);
            }
        }
    }

    public void singleRemove(BlockPos pos, World world) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block == Blocks.SCAFFOLDING) {
            if (!data.containsPos(pos)) {
                data.add(state, pos, world);
                removeScaffold(pos.up(), world);
            }
        }
    }

    public void removeScaffold(BlockPos pos, World world) {
        BlockState state = world.getBlockState(pos);
        removeScaffold(state, pos, world);
    }

    private void removeNextInQueue() {
        AVScaffoldRemoveMatrix matrix = data.getNext();
        if (matrix.valid) {
            BlockState state = matrix.state;
            World world = matrix.world;
            BlockPos pos = matrix.pos;
            if (state == null || world == null || pos == null) {
                data.clearData();
                return;
            }
            FluidState fluid = world.getFluidState(pos);
            if (state.getBlock() == Blocks.SCAFFOLDING) {
                if (state.get(AVProperties.DO_DROPS)) {
                    world.breakBlock(pos, world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS), null);
                } else {
                    world.setBlockState(pos, fluid.getBlockState());
                    world.updateNeighbors(pos, state.getBlock());
                }

                removeScaffold(pos.down(), world);

                removeScaffold(pos.east(), world);
                removeScaffold(pos.west(), world);

                removeScaffold(pos.north(), world);
                removeScaffold(pos.south(), world);
            }
        }
    }
}