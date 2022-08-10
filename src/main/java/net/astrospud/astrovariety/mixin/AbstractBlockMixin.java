package net.astrospud.astrovariety.mixin;

import com.google.common.collect.ImmutableList;
import net.astrospud.astrovariety.AstroVariety;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.Block.dropStack;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {
    protected AbstractBlockMixin() {
        super();
    }

    /*@Inject(at = @At("HEAD"), method = "getLootTableId", cancellable = true)
    public void avgetLootTableIdMixin(CallbackInfoReturnable<Identifier> cir) {
        if ((Object)this instanceof ScaffoldingBlock block) {
            ImmutableList<BlockState> var = block.getStateManager().getStates();
            var.get(0).
            if (bool) {
                cir.setReturnValue(null);
            }
        }
    }*/
}
