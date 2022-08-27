package net.astrospud.astrovariety.listeners;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface OnTickCallback {
    Event<OnTickCallback> EVENT = EventFactory.createArrayBacked(OnTickCallback.class,
            (listeners) -> (entity) -> {
                for (OnTickCallback listener : listeners) {
                    listener.onTick(entity);
                }
            });

    void onTick(LivingEntity entity);
}