package net.astrospud.astrovariety.types.unique;

import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CorrupterItem extends Item {
    private int tickInt = 0;
    private final SculkSpreadManager spreadManager;

    public CorrupterItem(Settings settings) {
        super(settings);
        this.spreadManager = SculkSpreadManager.create();
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (spreadManager.getCursors().size() > 3) {
            spreadManager.clearCursors();
        }
        spreadManager.tick(world, entity.getBlockPos(), world.getRandom(), true);
        if (tickInt > 10) {
            tickInt = 0;
            spreadManager.clearCursors();
        }
        tickInt++;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!Objects.requireNonNull(context.getPlayer()).getItemCooldownManager().isCoolingDown(this)) {
            context.getPlayer().getItemCooldownManager().set(this, 10);

            spreadManager.clearCursors();

            BlockPos pos = context.getBlockPos();
            World world = context.getWorld();
            pos = pos.up();
            Block block = world.getBlockState(context.getBlockPos()).getBlock();

            if (world.getRegistryKey() == World.END) {
                if (block == Blocks.END_STONE) {
                    world.setBlockState(context.getBlockPos(), Blocks.SCULK.getDefaultState());
                }
                spreadManager.spread(context.getBlockPos().add(context.getSide().getVector()), 3);
                spreadManager.tick(world, context.getBlockPos(), world.random, true);
            }

            change(pos, world);
            chargeChange(pos.up(), world, 2, new Vec3i(0, 1, 0));
            chargeChange(pos.down(), world, 5, new Vec3i(0, -1, 0));
            Vec3i vec = new Vec3i(-1, 0, 0);
            chargeChange(pos.add(vec), world, 3, vec);
            vec = new Vec3i(1, 0, 0);
            chargeChange(pos.add(vec), world, 3, vec);
            vec = new Vec3i(0, 0, -1);
            chargeChange(pos.add(vec), world, 3, vec);
            vec = new Vec3i(0, 0, 1);
            chargeChange(pos.add(vec), world, 3, vec);
        }
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
        BlockPos pos1 = pos.up();
        BlockState state1 = world.getBlockState(pos1);
        FluidState fluid1 = world.getFluidState(pos1);
        Block block1 = state1.getBlock();

        if (world.getRegistryKey() == World.END) {
            if (state == Blocks.SCULK_SHRIEKER.getDefaultState().with(Properties.CAN_SUMMON, false)) {
                world.setBlockState(pos, state.with(Properties.CAN_SUMMON, true));
            } else if (block == Blocks.END_STONE) {
                if (block1 == Blocks.SCULK_VEIN) {
                    if (Math.random() > 0.999) {
                        world.setBlockState(pos1, Blocks.SCULK_SENSOR.getDefaultState());
                        world.setBlockState(pos, Blocks.SCULK.getDefaultState());
                    } else if (Math.random() > 0.9999) {
                        world.setBlockState(pos1, Blocks.SCULK_SHRIEKER.getDefaultState().with(Properties.CAN_SUMMON, true));
                        world.setBlockState(pos, Blocks.SCULK.getDefaultState());
                    } else if (Math.random() > 0.9999) {
                        world.setBlockState(pos1, Blocks.SCULK_CATALYST.getDefaultState());
                        world.setBlockState(pos, Blocks.SCULK.getDefaultState());
                    }
                }
            } else if (block == Blocks.OBSIDIAN) {
                world.setBlockState(pos, Blocks.BONE_BLOCK.getDefaultState());
                if (block1 == Blocks.AIR) {
                    change(pos.east(), world);
                    change(pos.west(), world);
                    change(pos.north(), world);
                    change(pos.south(), world);
                }
                change(pos.up(), world);
                change(pos.down(), world);
            }
        }

        if (world.getRegistryKey() == World.OVERWORLD) {
            if (block1 == Blocks.GRASS) {
                world.setBlockState(pos1, Blocks.WARPED_ROOTS.getDefaultState());
            } else if (block1 == Blocks.TALL_GRASS) {
                world.setBlockState(pos1, Blocks.FIRE.getDefaultState());
            } else if (block1 instanceof FlowerBlock) {
                if (Math.random() >= 0.3) {
                    world.setBlockState(pos1, Blocks.WARPED_FUNGUS.getDefaultState());
                } else {
                    world.setBlockState(pos1, Blocks.CRIMSON_FUNGUS.getDefaultState());
                }
            }

            if (block == Blocks.GRASS) {
                world.setBlockState(pos, Blocks.WARPED_ROOTS.getDefaultState());
                pos = pos.down();
                state = world.getBlockState(pos);
                fluid = world.getFluidState(pos);
                block = state.getBlock();
            } else if (block == Blocks.TALL_GRASS) {
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
                pos = pos.down();
                state = world.getBlockState(pos);
                fluid = world.getFluidState(pos);
                block = state.getBlock();
            } else if (block1 instanceof FlowerBlock) {
                if (Math.random() >= 0.3) {
                    world.setBlockState(pos1, Blocks.WARPED_FUNGUS.getDefaultState());
                } else {
                    world.setBlockState(pos1, Blocks.CRIMSON_FUNGUS.getDefaultState());
                }
                pos = pos.down();
                state = world.getBlockState(pos);
                fluid = world.getFluidState(pos);
                block = state.getBlock();
            }

            if (block == Blocks.GRASS_BLOCK) {
                world.setBlockState(pos, Blocks.WARPED_NYLIUM.getDefaultState());
            } else if (block == Blocks.POINTED_DRIPSTONE) {
                if (state.get(Properties.VERTICAL_DIRECTION) == Direction.DOWN) {
                    world.setBlockState(pos, Blocks.WEEPING_VINES.getDefaultState());
                } else {
                    world.setBlockState(pos, Blocks.BASALT.getDefaultState());
                }
                change(pos.up(), world);
                change(pos.down(), world);
                world.updateNeighbor(pos.down(), block, pos);
                world.updateNeighbor(pos.up(), block, pos);
            } else if (
                    block == Blocks.STONE
                            || block == Blocks.DIORITE
                            || block == Blocks.ANDESITE
                            || block == Blocks.GRANITE
                            || block == Blocks.DEEPSLATE
                            || block == Blocks.DRIPSTONE_BLOCK
                            || (block instanceof OreBlock && block != Blocks.NETHER_QUARTZ_ORE && block != Blocks.NETHER_GOLD_ORE)
            ) {
                if (Math.random() >= 0.05 && !(block instanceof OreBlock)) {
                    world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
                } else {
                    if (Math.random() >= 0.6) {
                        world.setBlockState(pos, Blocks.NETHER_GOLD_ORE.getDefaultState());
                    } else {
                        world.setBlockState(pos, Blocks.NETHER_QUARTZ_ORE.getDefaultState());
                    }
                }
            } else if (block == Blocks.DIRT) {
                world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
            } else if (block == Blocks.OBSIDIAN) {
                world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                change(pos.down(), world);
            } else if (block == Blocks.SAND) {
                world.setBlockState(pos, Blocks.GRAVEL.getDefaultState());
            } else if (block == Blocks.GRAVEL && Math.random() > 0.99) {
                world.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState());
            } else if (block == Blocks.STONE_BRICKS) {
                world.setBlockState(pos, Blocks.NETHER_BRICKS.getDefaultState());
            } else if (
                    block == Blocks.COBBLESTONE
                            || block == Blocks.COBBLED_DEEPSLATE
            ) {
                if (Math.random() >= 0.2) {
                    world.setBlockState(pos, Blocks.BASALT.getDefaultState());
                } else {
                    world.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState());
                }
            } else if (
                    block == Blocks.ACACIA_LEAVES
                            || block == Blocks.BIRCH_LEAVES
                            || block == Blocks.OAK_LEAVES
                            || block == Blocks.DARK_OAK_LEAVES
                            || block == Blocks.JUNGLE_LEAVES
                            || block == Blocks.MANGROVE_LEAVES
                            || block == Blocks.SPRUCE_LEAVES
            ) {
                world.setBlockState(pos, Blocks.WARPED_WART_BLOCK.getDefaultState());
                Vec3i vec = new Vec3i(-1, 0, 0);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(1, 0, 0);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, 0, -1);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, 0, 1);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, 1, 0);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, -1, 0);
                chargeChange(pos.add(vec), world, 2, vec);
            } else if (
                    block == Blocks.ACACIA_LOG
                            || block == Blocks.BIRCH_LOG
                            || block == Blocks.OAK_LOG
                            || block == Blocks.DARK_OAK_LOG
                            || block == Blocks.JUNGLE_LOG
                            || block == Blocks.MANGROVE_LOG
                            || block == Blocks.SPRUCE_LOG
            ) {
                world.setBlockState(pos, Blocks.WARPED_HYPHAE.getDefaultState());
                Vec3i vec = new Vec3i(-1, 0, 0);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(1, 0, 0);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, 0, -1);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, 0, 1);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, 1, 0);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, -1, 0);
                chargeChange(pos.add(vec), world, 2, vec);
            } else if (
                    block == Blocks.STRIPPED_ACACIA_LOG
                            || block == Blocks.STRIPPED_BIRCH_LOG
                            || block == Blocks.STRIPPED_OAK_LOG
                            || block == Blocks.STRIPPED_DARK_OAK_LOG
                            || block == Blocks.STRIPPED_JUNGLE_LOG
                            || block == Blocks.STRIPPED_MANGROVE_LOG
                            || block == Blocks.STRIPPED_SPRUCE_LOG
            ) {
                world.setBlockState(pos, Blocks.STRIPPED_WARPED_HYPHAE.getDefaultState());
                Vec3i vec = new Vec3i(-1, 0, 0);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(1, 0, 0);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, 0, -1);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, 0, 1);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, 1, 0);
                chargeChange(pos.add(vec), world, 2, vec);
                vec = new Vec3i(0, -1, 0);
                chargeChange(pos.add(vec), world, 2, vec);
            } else if (block == Blocks.WATER) {
                world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                change(pos.down(), world);
            } else if (fluid.getFluid() == Fluids.WATER) {
                world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                change(pos.down(), world);
            }
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

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
