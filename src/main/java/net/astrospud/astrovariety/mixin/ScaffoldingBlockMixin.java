package net.astrospud.astrovariety.mixin;

import com.google.common.collect.ImmutableList;
import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.registry.AVItems;
import net.astrospud.astrovariety.registry.AVProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScaffoldingBlock.class)
public abstract class ScaffoldingBlockMixin extends Block {
    private static BooleanProperty DO_DROPS = null;
    private static BlockState state = null;

    public ScaffoldingBlockMixin(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(DO_DROPS, true))));
        state = this.getDefaultState();
    }

    @Inject(at = @At("RETURN"), method = "appendProperties")
    public void avappendPropertiesMixin(StateManager.Builder<Block, BlockState> builder, CallbackInfo cir) {
        builder.add(new Property[]{DO_DROPS});
    }




    static {
        DO_DROPS = AVProperties.DO_DROPS;
    }

    /*@Overwrite
    public final Identifier getLootTableId() {
        boolean bool = !getDoDrops();
        if (bool) {
            return null;
        }
        return super.getLootTableId();
    }*/

    public boolean getDoDrops() {
        return (Boolean)state.get(DO_DROPS);
    }
}
