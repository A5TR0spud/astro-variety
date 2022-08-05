package net.astrospud.astrovariety.registry;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.types.AVStatusEffect;
import net.astrospud.astrovariety.types.BleedingStatusEffect;
import net.astrospud.astrovariety.types.NanomachinesStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.DamageModifierStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class AVStatusEffects {
    private static final String BLEEDING_ID = "d126e566-5543-4add-946b-798aca10cd02";
    private static final String NANOMACHINES_ID = "42d97171-d3eb-4f89-835c-569773993bcb";

    public static final StatusEffect BLEEDING = new BleedingStatusEffect(StatusEffectCategory.NEUTRAL,0xfd0122, -4).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, BLEEDING_ID, 0.0,EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect NANOMACHINES = new NanomachinesStatusEffect(StatusEffectCategory.NEUTRAL,0x2201aa).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, NANOMACHINES_ID, -0.15000000596046448, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    //SLOWNESS = register(2, "slowness", (new StatusEffect(StatusEffectCategory.HARMFUL, 5926017)).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448,EntityAttributeModifier.Operation.MULTIPLY_TOTAL));

    public static void register(){
        Registry.register(Registry.STATUS_EFFECT, new Identifier(AstroVariety.MOD_ID, "bleeding"), BLEEDING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(AstroVariety.MOD_ID, "nanomachines"), NANOMACHINES);
    }
}