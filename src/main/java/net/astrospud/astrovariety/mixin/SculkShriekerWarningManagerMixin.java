package net.astrospud.astrovariety.mixin;

import net.minecraft.block.entity.SculkShriekerWarningManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.function.Predicate;

@Mixin(SculkShriekerWarningManager.class)
public class SculkShriekerWarningManagerMixin {
    //private static List<ServerPlayerEntity> PLAYERS = new ArrayList<>();

    @Inject(at = @At(value = "TAIL"), method = "warnNearbyPlayers")
    private static void avwarnNearbyPlayers(ServerWorld serverWorld, BlockPos blockPos, ServerPlayerEntity serverPlayerEntity, CallbackInfoReturnable<OptionalInt> cir) {
        List<ServerPlayerEntity> list = getPlayersInRange(serverWorld, blockPos);
        if (!list.contains(serverPlayerEntity)) {
            list.add(serverPlayerEntity);
        }
        /*for (ServerPlayerEntity player : list) {
            if (!PLAYERS.contains(player)) {
                PLAYERS.add(player);
            }
        }*/
        for (ServerPlayerEntity player : list) {
            String string = "astrovariety.warden_spawn_tracker.warning_level." + player.getSculkShriekerWarningManager().getWarningLevel();
            Text text = Text.translatable(string);
            player.sendMessage(text, true);
        }
    }

    /*@Inject(at = @At(value = "TAIL"), method = "setWarningLevel")
    public void avsetWarningLevel(int warningLevel, CallbackInfo cir) {
        for (ServerPlayerEntity player : PLAYERS) {
            String string = "astrovariety.warden_spawn_tracker.warning_level." + player.getSculkShriekerWarningManager().getWarningLevel();
            Text text = Text.translatable(string);
            player.sendMessage(text, true);
            if (player.getSculkShriekerWarningManager().getWarningLevel() <= 0) {
                PLAYERS.remove(player);
            }
        }
    }*/

    private static List<ServerPlayerEntity> getPlayersInRange(ServerWorld world, BlockPos pos) {
        Vec3d vec3d = Vec3d.ofCenter(pos);
        Predicate<ServerPlayerEntity> predicate = (player) -> {
            return player.getPos().isInRange(vec3d, 16.0);
        };
        return world.getPlayers(predicate.and(LivingEntity::isAlive).and(EntityPredicates.EXCEPT_SPECTATOR));
    }
}
