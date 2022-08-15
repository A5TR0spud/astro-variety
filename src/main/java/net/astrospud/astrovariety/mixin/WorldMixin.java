package net.astrospud.astrovariety.mixin;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(World.class)
public class WorldMixin {
    /*@Inject(at = @At("HEAD"), method = "removeBlock")
    public void avremoveBlockMixin(BlockPos pos, boolean move, CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof World world) {
            BlockState state = world.getBlockState(pos);
            ScaffoldBreakerItem.removeScaffold(state, pos, world);
        }
    }*/

    /*@Inject(at = @At("HEAD"), method = "breakBlock")
    public void avbreakBlockMixin(BlockPos pos, boolean drop, @Nullable Entity breakingEntity, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        AstroVariety.LOGGER.info("break block");
    }*/
}
