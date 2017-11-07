package com.romantupikov.game.simplerpg.entity.effect;

import com.romantupikov.game.simplerpg.entity.Unit;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class MeleeEffect extends EffectBase {
    private float value;

    public MeleeEffect(Unit unit, float value) {
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
                float damage = value - self.getDefence();
                if (damage < 1f)
                    damage = 1f;
                self.subHP(damage);
                return true;
            }
        }
        return false;
    }
}
