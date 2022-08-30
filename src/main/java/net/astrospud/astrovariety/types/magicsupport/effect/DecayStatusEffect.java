package net.astrospud.astrovariety.types.magicsupport.effect;

import net.astrospud.astrovariety.registry.AVStatusEffects;
import net.astrospud.astrovariety.types.utils.AVStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.DamageModifierStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class DecayStatusEffect extends DamageModifierStatusEffect {
    public DecayStatusEffect(StatusEffectCategory category, int color, double mod) {
        super(category, color, mod);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        StatusEffectInstance effect = entity.getStatusEffect(AVStatusEffects.DECAY);
        if (effect.getDuration() % 20 == 0) {
            entity.setHealth(entity.getHealth() - (effect.getAmplifier()+1));
        }
    }

    /*@Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.getStatusEffect(AVStatusEffects.DECAY);
        super.onRemoved(entity, attributes, amplifier);
    }*/
}
