package net.astrospud.astrovariety.types.magicsupport.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.DamageModifierStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class DecayStatusEffect extends DamageModifierStatusEffect {
    public DecayStatusEffect(StatusEffectCategory category, int color, double modifier) {
        super(category, color, modifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        entity.heal(amplifier+1);
    }
}
