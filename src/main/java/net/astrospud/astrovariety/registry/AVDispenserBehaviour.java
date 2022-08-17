package net.astrospud.astrovariety.registry;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.types.unique.entity.DynamiteEntity;
import net.astrospud.astrovariety.types.unique.entity.SlowBallEntity;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class AVDispenserBehaviour {

    public static void registerModDispensorProps() {
        AstroVariety.LOGGER.info("Registering Dispenser Behaviours for " + AstroVariety.MOD_ID);

        DispenserBehavior proj = new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return new DynamiteEntity(AVEntities.DYNAMITE, position.getX(), position.getY(), position.getZ(), world);
            }

            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                BlockPos pos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
                World world = pointer.getWorld();
                final DynamiteEntity entity = new DynamiteEntity(AVEntities.DYNAMITE, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, world);
                entity.setItem(stack);
                Direction dir = pointer.getBlockState().get(DispenserBlock.FACING);
                entity.setVelocity(dir.getOffsetX(), dir.getOffsetY(), dir.getOffsetZ(), 0.75F, 1.0F);
                world.spawnEntity(entity);
                stack.decrement(1);
                return stack;
            }
        };
        DispenserBlock.registerBehavior(AVItems.DYNAMITE, proj);
        proj = new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return new DynamiteEntity(AVEntities.SLOW_BALL, position.getX(), position.getY(), position.getZ(), world);
            }

            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                BlockPos pos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
                World world = pointer.getWorld();
                final SlowBallEntity entity = new SlowBallEntity(AVEntities.SLOW_BALL, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, world);
                entity.setItem(stack);
                Direction dir = pointer.getBlockState().get(DispenserBlock.FACING);
                entity.setVelocity(dir.getOffsetX(), dir.getOffsetY(), dir.getOffsetZ(), 0.75F, 1.0F);
                world.spawnEntity(entity);
                stack.decrement(1);
                return stack;
            }
        };
        DispenserBlock.registerBehavior(AVItems.SLOW_BALL, proj);


        DispenserBehavior dispenserBehavior = new ItemDispenserBehavior() {
            private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                FluidModificationItem fluidModificationItem = (FluidModificationItem)stack.getItem();
                BlockPos blockPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
                World world = pointer.getWorld();
                if (fluidModificationItem.placeFluid((PlayerEntity)null, world, blockPos, (BlockHitResult)null)) {
                    fluidModificationItem.onEmptied((PlayerEntity)null, world, stack, blockPos);
                    return stack;
                } else {
                    return this.fallbackBehavior.dispense(pointer, stack);
                }
            }
        };
        DispenserBlock.registerBehavior(AVItems.BOTTOMLESS_LAVA_BUCKET, dispenserBehavior);
        DispenserBlock.registerBehavior(AVItems.BOTTOMLESS_WATER_BUCKET, dispenserBehavior);
        DispenserBlock.registerBehavior(AVItems.RAINBOW_SOAKER, new ItemDispenserBehavior() {
            private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                WorldAccess worldAccess = pointer.getWorld();
                BlockPos blockPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
                BlockState blockState = worldAccess.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (block instanceof FluidDrainable) {
                    ItemStack itemStack = ((FluidDrainable)block).tryDrainFluid(worldAccess, blockPos, blockState);
                    if (itemStack.isEmpty()) {
                        return super.dispenseSilently(pointer, stack);
                    } else {
                        worldAccess.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, blockPos);
                        return stack;
                    }
                } else {
                    return super.dispenseSilently(pointer, stack);
                }
            }
        });
        DispenserBlock.registerBehavior(AVItems.ULTRA_ABSORBENT_SPONGE, new ItemDispenserBehavior() {
            private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                WorldAccess worldAccess = pointer.getWorld();
                BlockPos blockPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
                BlockState blockState = worldAccess.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (block instanceof FluidDrainable && blockState.getFluidState().getFluid() == Fluids.WATER) {
                    ItemStack itemStack = ((FluidDrainable)block).tryDrainFluid(worldAccess, blockPos, blockState);
                    if (itemStack.isEmpty()) {
                        return super.dispenseSilently(pointer, stack);
                    } else {
                        worldAccess.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, blockPos);
                        return stack;
                    }
                } else {
                    return super.dispenseSilently(pointer, stack);
                }
            }
        });
        DispenserBlock.registerBehavior(AVItems.OBSIDIAN_SPONGE, new ItemDispenserBehavior() {
            private final ItemDispenserBehavior fallbackBehavior = new ItemDispenserBehavior();

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                WorldAccess worldAccess = pointer.getWorld();
                BlockPos blockPos = pointer.getPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
                BlockState blockState = worldAccess.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (block instanceof FluidDrainable && blockState.getFluidState().getFluid() == Fluids.LAVA) {
                    ItemStack itemStack = ((FluidDrainable)block).tryDrainFluid(worldAccess, blockPos, blockState);
                    if (itemStack.isEmpty()) {
                        return super.dispenseSilently(pointer, stack);
                    } else {
                        worldAccess.emitGameEvent((Entity)null, GameEvent.FLUID_PICKUP, blockPos);
                        return stack;
                    }
                } else {
                    return super.dispenseSilently(pointer, stack);
                }
            }
        });
    }
}
