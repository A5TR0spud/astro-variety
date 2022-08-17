package net.astrospud.astrovariety.types.magicsupport.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.DamageModifierStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class NanomachinesStatusEffect extends StatusEffect {
    public NanomachinesStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        entity.heal(amplifier+1);
    }
}
