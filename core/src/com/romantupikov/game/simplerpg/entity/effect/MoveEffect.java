package com.romantupikov.game.simplerpg.entity.effect;

import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class MoveEffect extends EffectBase {
    public MoveEffect(Unit unit) {
        super(unit);

    }

    @Override
    public boolean update(float delta) {
        if (visualEffect != null) {
            visualEffect.setPosition(self.getX(), self.getY());
            visualEffect.update(delta);
        }

        if (visualEffect != null) {
            visualEffect.allowCompletion();
            if (visualEffect.isComplete() && complete) {
                visualEffect.free();
                return true;
            }
        } else if (complete)
            return true;

        return false;
    }
}
