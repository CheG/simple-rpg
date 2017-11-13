package com.romantupikov.game.simplerpg.entity.effect;

import com.badlogic.gdx.Gdx;
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
            if (visualEffect.isComplete() && complete) {
                visualEffect.free();
                return true;
            }
        } else if (complete)
            return true;

        if (!complete) {
            float damage = value - self.getAttributes().getDefence();
            if (damage < 1f)
                damage = 1f;
            self.getAttributes().addHP(-damage);
            Gdx.app.debug("", "melee effect on [" + self.getAttributes().getName() + "] complete.");
            complete = true;
        }
        return false;
    }
}
