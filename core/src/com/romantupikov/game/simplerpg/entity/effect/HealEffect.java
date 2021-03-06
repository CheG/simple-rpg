package com.romantupikov.game.simplerpg.entity.effect;

import com.badlogic.gdx.Gdx;
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
            if (visualEffect.isComplete() && complete) {
                visualEffect.free();
                return true;
            }
        } else if (complete)
            return true;

        if (!complete) {
            self.getAttributes().addHP(value);
            Gdx.app.debug("", "[" + self.getAttributes().getName() + "] heal effect complete.");
            complete = true;
        }
        return false;
    }
}
