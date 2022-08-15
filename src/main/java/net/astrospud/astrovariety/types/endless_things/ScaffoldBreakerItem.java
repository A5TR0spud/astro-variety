package net.astrospud.astrovariety.types.endless_things;

import net.astrospud.astrovariety.registry.AVProperties;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ScaffoldBreakerItem extends Item {
    public ScaffoldBreakerItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        removeScaffold(state, pos, world);
        return super.postMine(stack, world, state, pos, miner);
    }

    public static void removeScaffold(BlockState state, BlockPos pos, World world) {
        FluidState fluid = world.getFluidState(pos);
        Block block = state.getBlock();

        if (state.contains(AVProperties.DO_DROPS)) {
            if (!(Boolean)state.get(AVProperties.DO_DROPS) && block == Blocks.SCAFFOLDING) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                removeScaffold(pos.up(), world);
                removeScaffold(pos.down(), world);

                removeScaffold(pos.east(), world);
                removeScaffold(pos.west(), world);

                removeScaffold(pos.north(), world);
                removeScaffold(pos.south(), world);
            }
        }
    }

    public static void removeScaffold(BlockPos pos, World world) {
        BlockState state = world.getBlockState(pos);
        FluidState fluid = world.getFluidState(pos);
        Block block = state.getBlock();

        if (state.contains(AVProperties.DO_DROPS)) {
            if (!(Boolean)state.get(AVProperties.DO_DROPS) && block == Blocks.SCAFFOLDING) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                removeScaffold(pos.up(), world);
                removeScaffold(pos.down(), world);

                removeScaffold(pos.east(), world);
                removeScaffold(pos.west(), world);

                removeScaffold(pos.north(), world);
                removeScaffold(pos.south(), world);
            }
        }
    }
}
