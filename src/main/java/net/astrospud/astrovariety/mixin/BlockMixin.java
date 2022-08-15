package net.astrospud.astrovariety.mixin;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVProperties;
import net.astrospud.astrovariety.types.endless_things.ScaffoldBreakerItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Supplier;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock{
    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "dropStack", cancellable = true)
    private static void avdropStack(World world, BlockPos pos, ItemStack stack, CallbackInfo cir) {
        BlockState state = world.getBlockState(pos);
        AstroVariety.LOGGER.info(String.valueOf(state));
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
}
