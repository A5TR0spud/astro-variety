package net.astrospud.astrovariety.types.unique;

import net.minecraft.block.*;
import net.minecraft.data.server.BlockTagProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class CorruptorItem extends Item {
    public CorruptorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        pos = pos.up();
        change(pos, world);
        chargeChange(pos.up(), world, 2, new Vec3i(0,1,0));
        chargeChange(pos.down(), world, 5, new Vec3i(0,-1,0));
        Vec3i vec = new Vec3i(-1,0,0);
        chargeChange(pos.add(vec), world, 3, vec);
        vec = new Vec3i(1,0,0);
        chargeChange(pos.add(vec), world, 3, vec);
        vec = new Vec3i(0,0,-1);
        chargeChange(pos.add(vec), world, 3, vec);
        vec = new Vec3i(0,0,1);
        chargeChange(pos.add(vec), world, 3, vec);

        return super.useOnBlock(context);
    }

    public void chargeChange(BlockPos pos, World world, int depth, Vec3i direction) {
        int i = 0;
        while (i < depth) {
            change(pos, world);
            Vec3i vec = new Vec3i(0,1,0);
            if (vec != direction) {
                chargeChange(pos.add(vec), world, depth - i - 1, vec);
            }
            vec = new Vec3i(0,-1,0);
            if (vec != direction) {
                chargeChange(pos.add(vec), world, depth - i - 1, vec);
            }
            vec = new Vec3i(1,0,0);
            if (vec != direction) {
                chargeChange(pos.add(vec), world, depth - i - 1, vec);
            }
            vec = new Vec3i(-1,0,0);
            if (vec != direction) {
                chargeChange(pos.add(vec), world, depth - i - 1, vec);
            }
            vec = new Vec3i(0,0,1);
            if (vec != direction) {
                chargeChange(pos.add(vec), world, depth - i - 1, vec);
            }
            vec = new Vec3i(0,0,-1);
            if (vec != direction) {
                chargeChange(pos.add(vec), world, depth - i - 1, vec);
            }
            pos = pos.add(direction);
            i++;
        }
    }

    public void change(BlockPos pos, World world) {
        BlockState state = world.getBlockState(pos);
        FluidState fluid = world.getFluidState(pos);
        Block block = state.getBlock();

        if (block == Blocks.GRASS_BLOCK) {
            world.setBlockState(pos, Blocks.WARPED_NYLIUM.getDefaultState());
        } else if (block == Blocks.GRASS) {
            world.setBlockState(pos, Blocks.WARPED_ROOTS.getDefaultState());
        } else if (block == Blocks.TALL_GRASS) {
            world.setBlockState(pos, Blocks.FIRE.getDefaultState());
        } else if (block == Blocks.STONE) {
            world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
        } else if (block == Blocks.DIRT) {
            world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
        } else if (block == Blocks.OBSIDIAN) {
            world.setBlockState(pos, Blocks.LAVA.getDefaultState());
        } else if (block == Blocks.SAND) {
            world.setBlockState(pos, Blocks.GRAVEL.getDefaultState());
        } else if (block == Blocks.COBBLESTONE) {
            world.setBlockState(pos, Blocks.BASALT.getDefaultState());
        //} else if (BlockTags.OVERWORLD_NATURAL_LOGS) {
            //world.setBlockState(pos, Blocks.WARPED_HYPHAE.getDefaultState());
        } else if (fluid.getFluid() == Fluids.WATER) {
            world.setBlockState(pos, Blocks.LAVA.getDefaultState());
        } else if (block == Blocks.WATER) {
            world.setBlockState(pos, Blocks.LAVA.getDefaultState());
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
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

                blockState = world.getBlockState(blockPos);
                if (blockState.getBlock() instanceof FluidDrainable) {
                    BlockPos pos = blockPos2.up();
                    change(pos, world);
                    chargeChange(pos.up(), world, 2, new Vec3i(0,1,0));
                    chargeChange(pos.down(), world, 5, new Vec3i(0,-1,0));
                    Vec3i vec = new Vec3i(-1,0,0);
                    chargeChange(pos.add(vec), world, 3, vec);
                    vec = new Vec3i(1,0,0);
                    chargeChange(pos.add(vec), world, 3, vec);
                    vec = new Vec3i(0,0,-1);
                    chargeChange(pos.add(vec), world, 3, vec);
                    vec = new Vec3i(0,0,1);
                    chargeChange(pos.add(vec), world, 3, vec);
                }

                return TypedActionResult.fail(itemStack);
            } else {
                return TypedActionResult.fail(itemStack);
            }
        }
    }
}
