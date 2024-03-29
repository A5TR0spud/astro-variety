package net.astrospud.astrovariety.types.endless_things.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.Block.dropStacks;

public class UltraAbsorbentSpongeItem extends Item implements FluidModificationItem {
    private final Fluid fluid = Fluids.EMPTY;

    public UltraAbsorbentSpongeItem(/*Fluid fluid, */Item.Settings settings) {
        super(settings);
        //this.fluid = fluid;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            Direction direction = blockHitResult.getSide();
            BlockPos blockPos2 = blockPos.offset(direction);
            if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos2, direction, itemStack)) {
                BlockState blockState;
                if (this.fluid == Fluids.EMPTY) {
                    blockState = world.getBlockState(blockPos);
                    if (blockState.getBlock() instanceof FluidDrainable && blockState.getBlock() == Blocks.WATER) {
                        FluidDrainable fluidDrainable = (FluidDrainable)blockState.getBlock();
                        ItemStack itemStack2 = fluidDrainable.tryDrainFluid(world, blockPos, blockState);
                        if (!itemStack2.isEmpty()) {
                            user.incrementStat(Stats.USED.getOrCreateStat(this));
                            /*fluidDrainable.getBucketFillSound().ifPresent((sound) -> {
                                user.playSound(sound, 1.0F, 1.0F);
                            });*/
                            user.playSound(SoundEvents.BLOCK_WET_GRASS_PLACE, 1F, 1F);
                            world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                            absorbWater(user, world, blockPos);
                            //ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
                            //if (!world.isClient) {
                            //    Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)user, itemStack2);
                            //}

                            return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
                        }
                    }

                    return TypedActionResult.fail(itemStack);
                } else {
                    blockState = world.getBlockState(blockPos);
                    BlockPos blockPos3 = blockState.getBlock() instanceof FluidFillable && this.fluid == Fluids.WATER ? blockPos : blockPos2;
                    if (this.placeFluid(user, world, blockPos3, blockHitResult)) {
                        this.onEmptied(user, world, itemStack, blockPos3);
                        if (user instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, blockPos3, itemStack);
                        }

                        user.incrementStat(Stats.USED.getOrCreateStat(this));
                        return TypedActionResult.success(getEmptiedStack(itemStack, user), world.isClient());
                    } else {
                        return TypedActionResult.fail(itemStack);
                    }
                }
            } else {
                return TypedActionResult.fail(itemStack);
            }
        }
    }


    public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
        return stack;
    }

    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
    }

    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (!(this.fluid instanceof FlowableFluid)) {
            return false;
        } else {
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            Material material = blockState.getMaterial();
            boolean bl = blockState.canBucketPlace(this.fluid);
            boolean bl2 = blockState.isAir() || bl || block instanceof FluidFillable && ((FluidFillable)block).canFillWithFluid(world, pos, blockState, this.fluid);
            if (!bl2) {
                return hitResult != null && this.placeFluid(player, world, hitResult.getBlockPos().offset(hitResult.getSide()), (BlockHitResult)null);
            } else if (world.getDimension().ultrawarm() && this.fluid.isIn(FluidTags.WATER)) {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

                for(int l = 0; l < 8; ++l) {
                    world.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0, 0.0, 0.0);
                }

                return true;
            } else if (block instanceof FluidFillable && this.fluid == Fluids.WATER) {
                ((FluidFillable)block).tryFillWithFluid(world, pos, blockState, ((FlowableFluid)this.fluid).getStill(false));
                this.playEmptyingSound(player, world, pos);
                return true;
            } else {
                if (!world.isClient && bl && !material.isLiquid()) {
                    world.breakBlock(pos, true);
                }

                if (!world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), 11) && !blockState.getFluidState().isStill()) {
                    return false;
                } else {
                    this.playEmptyingSound(player, world, pos);
                    return true;
                }
            }
        }
    }

    protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        SoundEvent soundEvent = this.fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
        world.playSound(player, pos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
    }

    private void absorbWater(PlayerEntity user, World world, BlockPos blockPos) {
        for (int ew = 0; ew < 3; ew++) {
            for (int ns = 0; ns < 3; ns++) {
                for (int y = 0; y < 3; y++) {
                    BlockPos blockPos1 = blockPos.east().down().south();
                    blockPos1 = blockPos1.west(ew).up(y).north(ns);

                    BlockState blockState1 = world.getBlockState(blockPos1);
                    if (blockState1.getBlock() instanceof FluidDrainable && blockState1.getBlock() == Blocks.WATER) {
                        FluidDrainable fluidDrainable = (FluidDrainable) blockState1.getBlock();
                        fluidDrainable.tryDrainFluid(world, blockPos1, blockState1);

                        world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos1);

                        //world.setBlockState(blockPos1, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
