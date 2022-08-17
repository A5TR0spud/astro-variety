package net.astrospud.astrovariety.types.endless_things.item;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.registry.AVBlocks;
import net.astrospud.astrovariety.registry.AVProperties;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.UseAction;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ScaffoldWrenchItem extends ScaffoldingItem {
    public ScaffoldWrenchItem(Settings settings) {
        super(Blocks.SCAFFOLDING, settings);
    }

    @Override
    public String getTranslationKey() {
        return "item.astrovariety.scaffold_wrench";
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        if (!context.canPlace()) {
            return ActionResult.FAIL;
        } else {
            ItemPlacementContext itemPlacementContext = this.getPlacementContext(context);
            if (itemPlacementContext == null) {
                return ActionResult.FAIL;
            } else {
                BlockState blockState = this.getPlacementState(itemPlacementContext);
                if (blockState == null) {
                    return ActionResult.FAIL;
                } else if (!this.place(itemPlacementContext, blockState)) {
                    return ActionResult.FAIL;
                } else {
                    BlockPos blockPos = itemPlacementContext.getBlockPos();
                    World world = itemPlacementContext.getWorld();
                    PlayerEntity playerEntity = itemPlacementContext.getPlayer();
                    ItemStack itemStack = itemPlacementContext.getStack();
                    BlockState blockState2 = world.getBlockState(blockPos);
                    if (blockState2.isOf(blockState.getBlock())) {
                        blockState2 = this.placeFromNbt(blockPos, world, itemStack, blockState2);
                        this.postPlacement(blockPos, world, playerEntity, itemStack, blockState2);
                        blockState2.getBlock().onPlaced(world, blockPos, blockState2, playerEntity, itemStack);
                        if (playerEntity instanceof ServerPlayerEntity) {
                            Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
                        }
                    }

                    BlockSoundGroup blockSoundGroup = blockState2.getSoundGroup();
                    world.playSound(playerEntity, blockPos, this.getPlaceSound(blockState2), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);

                    blockState2 = blockState2.with(AVProperties.DO_DROPS, false);
                    world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Emitter.of(playerEntity, blockState2));

                    return ActionResult.success(world.isClient);
                }
            }
        }
    }

    private BlockState placeFromNbt(BlockPos pos, World world, ItemStack stack, BlockState state) {
        BlockState blockState = state;
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null) {
            NbtCompound nbtCompound2 = nbtCompound.getCompound("BlockStateTag");
            StateManager<Block, BlockState> stateManager = state.getBlock().getStateManager();
            Iterator var9 = nbtCompound2.getKeys().iterator();

            while(var9.hasNext()) {
                String string = (String)var9.next();
                Property<?> property = stateManager.getProperty(string);
                if (property != null) {
                    String string2 = nbtCompound2.get(string).asString();
                    blockState = with(blockState, property, string2);
                }
            }
        }

        blockState = blockState.with(AVProperties.DO_DROPS, false);

        if (blockState != state) {
            world.setBlockState(pos, blockState, 2);
        }

        return blockState;
    }

    private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
        return (BlockState)property.parse(name).map((value) -> {
            return (BlockState)state.with(property, value);
        }).orElse(state);
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        //super.appendStacks(group, stacks);
        if (group == ItemGroup.TOOLS || group == ItemGroup.SEARCH) {
            stacks.add(new ItemStack(this.asItem()));
        }
    }

    @Override
    public void appendBlocks(Map<Block, Item> map, Item item) {
        //super.appendBlocks(map, item);
        //Block SCAFFOLDING = register("scaffolding1", new ScaffoldingBlock(AbstractBlock.Settings.of(Material.DECORATION, MapColor.PALE_YELLOW).noCollision().sounds(BlockSoundGroup.SCAFFOLDING).dynamicBounds()));
        //map.put(ScaffoldingBlock.getBlockFromItem(this), this.asItem());
    }

    /*private static Block register(String id, Block block) {
        return (Block) Registry.register(Registry.BLOCK, id, block);
    }*/

    @Override
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = this.getBlock();
        if (!blockState.isOf(block)) {
            return ScaffoldingBlock.calculateDistance(world, blockPos) == 7 ? null : context;
        } else {
            Direction direction;
            if (context.shouldCancelInteraction()) {
                direction = context.hitsInsideBlock() ? context.getSide().getOpposite() : context.getSide();
            } else {
                direction = context.getSide() == Direction.UP ? context.getPlayerFacing() : Direction.UP;
            }

            int i = 0;
            BlockPos.Mutable mutable = blockPos.mutableCopy().move(direction);

            while(i < 7) {
                if (!world.isClient && !world.isInBuildLimit(mutable)) {
                    PlayerEntity playerEntity = context.getPlayer();
                    int j = world.getTopY();
                    if (playerEntity instanceof ServerPlayerEntity && mutable.getY() >= j) {
                        ((ServerPlayerEntity)playerEntity).sendMessageToClient(Text.translatable("build.tooHigh", new Object[]{j - 1}).formatted(Formatting.RED), true);
                    }
                    break;
                }

                blockState = world.getBlockState(mutable);
                if (!blockState.isOf(this.getBlock())) {
                    if (blockState.canReplace(context)) {
                        return ItemPlacementContext.offset(context, mutable, direction);
                    }
                    break;
                }

                mutable.move(direction);
                if (direction.getAxis().isHorizontal()) {
                    ++i;
                }
            }

            return null;
        }
    }

    @Override
    protected boolean checkStatePlacement() {
        return false;
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        PlayerEntity playerEntity = context.getPlayer();
        ShapeContext shapeContext = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
        return (!this.checkStatePlacement() || state.canPlaceAt(context.getWorld(), context.getBlockPos())) && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);
    }

    @Override
    public Block getBlock() {
        return Blocks.SCAFFOLDING;
    }

    @Override
    protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        return super.postPlacement(pos, world, player, stack, state);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        return super.finishUsing(stack, world, user);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return super.useOnBlock(context);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(ItemPlacementContext context) {
        return super.getPlacementState(context);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return super.getUseAction(stack);
    }
}
