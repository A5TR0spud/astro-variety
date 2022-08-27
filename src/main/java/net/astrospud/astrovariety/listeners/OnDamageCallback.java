package net.astrospud.astrovariety.listeners;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public interface OnDamageCallback {
    Event<OnDamageCallback> EVENT = EventFactory.createArrayBacked(OnDamageCallback.class,
            (listeners) -> (entity, source, amount) -> {
                for (OnDamageCallback listener : listeners) {
                    listener.onDamageTick(entity, source, amount);
                }
            });

    void onDamageTick(LivingEntity entity, DamageSource source, float amount);
}