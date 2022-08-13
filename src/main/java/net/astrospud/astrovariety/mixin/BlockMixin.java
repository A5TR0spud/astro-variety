package net.astrospud.astrovariety.mixin;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVProperties;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
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
    //private static BooleanProperty DO_DROPS = null;

    public BlockMixin(Settings settings) {
        super(settings);
        //this.setDefaultState((BlockState) ((BlockState) ((BlockState) ((BlockState) stateManager.getDefaultState()).with(DO_DROPS, true))));
    }

    /*@Inject(at = @At("HEAD"), method = "getDroppedStacks", cancellable = true)
    private static void avgetDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, CallbackInfoReturnable<List<ItemStack>> cir) {
        //LootContext.Builder builder = (new LootContext.Builder(world)).random(world.random).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity);
        //return state.getDroppedStacks(builder);
        //if (!state.get(AVProperties.DO_DROPS)) {
            cir.setReturnValue(null);
        //}
    }*/

    @Inject(at = @At("HEAD"), method = "dropStack", cancellable = true)
    private static void avdropStack(World world, BlockPos pos, ItemStack stack, CallbackInfo cir) {
        BlockState state = world.getBlockState(pos);
        state.updateNeighbors(world, pos, 6);
        state.getProperties();
        state.getBlock().getDefaultState();

        Boolean contains = state.contains(AVProperties.DO_DROPS);

        if (contains || state.getBlock() == Blocks.SCAFFOLDING) {
            Boolean do_drops = state.get(AVProperties.DO_DROPS);
            if (!do_drops) {
                cir.cancel();
            }
        }
    }

    /*@Inject(at = @At("RETURN"), method = "appendProperties")
    public void avappendPropertiesMixin(StateManager.Builder<Block, BlockState> builder, CallbackInfo cir) {
        builder.add(new Property[]{DO_DROPS});
    }

    static {
        DO_DROPS = AVProperties.DO_DROPS;
    }*/
}
