package net.astrospud.astrovariety.mixin;

import net.astrospud.astrovariety.registry.AVProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock{
    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "dropStack", cancellable = true)
    private static void avdropStack(World world, BlockPos pos, ItemStack stack, CallbackInfo cir) {
        BlockState state = world.getBlockState(pos);
        boolean contains = (Boolean)state.contains(AVProperties.DO_DROPS);

        if (contains || state.getBlock() == Blocks.SCAFFOLDING) {
            boolean do_drops = (Boolean)state.get(AVProperties.DO_DROPS);
            if (!do_drops) {
                state.updateNeighbors(world, pos, 6);
                cir.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "afterBreak", cancellable = true)
    public void avafterBreakMixin(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo cir) {
        if (state.contains(AVProperties.DO_DROPS)) {
            if (!state.get(AVProperties.DO_DROPS)) {
                player.incrementStat(Stats.MINED.getOrCreateStat(this.asBlock()));
                player.addExhaustion(0.005F);
                cir.cancel();
            }
        }
    }

    /*@Inject(at = @At("HEAD"), method = "replace", cancellable = true)
    public static void avreplaceMixin(BlockState state, BlockState newState, WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth, CallbackInfo cir) {
        if (newState != state) {
            if (newState.isAir()) {
                if (!world.isClient()) {
                    world.breakBlock(pos, (flags & 32) == 0, (Entity)null, maxUpdateDepth);
                }
            } else {
                world.setBlockState(pos, newState, flags & -33, maxUpdateDepth);
            }
        }
    }*/
}
