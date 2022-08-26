package net.astrospud.astrovariety.registry;

import net.astrospud.astrovariety.AstroVariety;
import net.astrospud.astrovariety.types.magicsupport.effect.BleedingStatusEffect;
import net.astrospud.astrovariety.types.magicsupport.effect.NanomachinesStatusEffect;
import net.astrospud.astrovariety.types.utils.AVStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AVStatusEffects {
    private static final String BLEEDING_ID = "d126e566-5543-4add-946b-798aca10cd02";
    private static final String NANOMACHINES_ID = "42d97171-d3eb-4f89-835c-569773993bcb";
    private static final String WIND_MOBILITTY_SPEED_ID = "035b3e84-a3c8-4d11-8e67-4236ed091162";
    private static final String WIND_MOBILITTY_WEAKNESS_ID = "c2d75289-e2fb-4f06-9372-65b597a027b4";
    private static final String INK_WELL_ID = "8ec3bdba-7516-4a52-b1bd-b1cddfd3b355";

    public static final StatusEffect BLEEDING = new BleedingStatusEffect(StatusEffectCategory.NEUTRAL,0xfd0122, -4).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, BLEEDING_ID, 0.0,EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect NANOMACHINES = new NanomachinesStatusEffect(StatusEffectCategory.NEUTRAL,0x2201aa).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, NANOMACHINES_ID, -0.15000000596046448, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final StatusEffect WIND_MOBILITTY = new AVStatusEffect(StatusEffectCategory.NEUTRAL,0x66aaff)
            .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, WIND_MOBILITTY_SPEED_ID, 0.20000000298023224, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, WIND_MOBILITTY_WEAKNESS_ID, 0.0,EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect LOWER_FOLLOW = new AVStatusEffect(StatusEffectCategory.HARMFUL, 0x010002)
            .addAttributeModifier(EntityAttributes.GENERIC_FOLLOW_RANGE, INK_WELL_ID, 0, EntityAttributeModifier.Operation.ADDITION);

    public static void register(){
        Registry.register(Registry.STATUS_EFFECT, new Identifier(AstroVariety.MOD_ID, "bleeding"), BLEEDING);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(AstroVariety.MOD_ID, "nanomachines"), NANOMACHINES);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(AstroVariety.MOD_ID, "wind_mobility"), WIND_MOBILITTY);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(AstroVariety.MOD_ID, "lower_follow"), LOWER_FOLLOW);
    }
}