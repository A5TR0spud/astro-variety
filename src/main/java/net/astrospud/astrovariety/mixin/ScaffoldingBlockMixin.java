package net.astrospud.astrovariety.mixin;

import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScaffoldingBlock.class)
public abstract class ScaffoldingBlockMixin extends Block {
    private static BooleanProperty DO_DROPS;
    private static BlockState state = null;

    public ScaffoldingBlockMixin(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AVProperties.DO_DROPS, true));
        state = this.getDefaultState();
    }

    @Inject(at = @At("RETURN"), method = "appendProperties")
    public void avappendPropertiesMixin(StateManager.Builder<Block, BlockState> builder, CallbackInfo cir) {
        builder.add(new Property[]{DO_DROPS});
    }

    @Inject(at = @At("HEAD"), method = "getOutlineShape", cancellable = true)
    public void avgetOutlineShapeMixin(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (context.isHolding(AVItems.SCAFFOLD_WRENCH) || context.isHolding(AVItems.SCAFFOLD_BREAKER)) {
            cir.setReturnValue(VoxelShapes.fullCube());
        }
    }

    @Inject(at = @At("HEAD"), method = "canReplace", cancellable = true)
    public void avcanReplaceMixin(BlockState state, ItemPlacementContext context, CallbackInfoReturnable<Boolean> cir) {
        if (context.getStack().isOf(AVItems.SCAFFOLD_WRENCH)) {
            cir.setReturnValue(true);
        }
    }

    static {
        DO_DROPS = AVProperties.DO_DROPS;
    }
}
