package net.astrospud.astrovariety.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class AVFoodComponents {
    public static final FoodComponent GOLD_BERRIES;

    public AVFoodComponents() {
    }

    static {
        //GOLDEN_APPLE = (new FoodComponent.Builder()).hunger(4).saturationModifier(1.2F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1.0F).alwaysEdible().build();
        //GOLDEN_CARROT = (new FoodComponent.Builder()).hunger(6).saturationModifier(1.2F).build();
        //SWEET_BERRIES = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.1F).build();
        //CARROT = (new FoodComponent.Builder()).hunger(3).saturationModifier(0.6F).build();
        //GLOW_BERRIES = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.1F).build();
        GOLD_BERRIES = (new FoodComponent.Builder())
                .hunger(4)
                .saturationModifier(0.2F)
                .alwaysEdible()
                //GLOW
                .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0), 1.000F)
                //LUCK
                .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 1200*5, 0), 0.125F)
                .build();
    }
}
