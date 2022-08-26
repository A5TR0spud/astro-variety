package net.astrospud.astrovariety.listeners;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public interface OnDamageCallback {
    Event<OnDamageCallback> EVENT = EventFactory.createArrayBacked(OnDamageCallback.class,
            (listeners) -> (player, source) -> {
                for (OnDamageCallback listener : listeners) {
                    listener.onOrganTick(player, source);
                }
            });

    void onOrganTick(PlayerEntity player, DamageSource source);
}