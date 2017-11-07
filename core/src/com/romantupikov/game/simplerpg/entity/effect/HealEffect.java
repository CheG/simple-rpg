package com.romantupikov.game.simplerpg.entity.effect;

import com.romantupikov.game.simplerpg.entity.Unit;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class HealEffect extends EffectBase {
    private float value;

    public HealEffect(Unit unit, float value) {
        super(unit);
        this.value = value;
    }

    @Override
    public boolean update(float delta) {
        if (visualEffect != null) {
            visualEffect.setPosition(self.getX(), self.getY());
            visualEffect.update(delta);
            if (visualEffect.isComplete()) {
                visualEffect.free();
                self.addHP(value);
                return true;
            }
        }
        return false;
    }
}
