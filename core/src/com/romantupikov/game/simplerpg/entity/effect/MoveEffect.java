package com.romantupikov.game.simplerpg.entity.effect;

import com.badlogic.gdx.Gdx;
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
            if (complete) {
                visualEffect.allowCompletion();
                if (visualEffect.isComplete()) {
                    visualEffect.free();
                    Gdx.app.debug("", "move effect on [" + self.getAttributes().getName() + "] complete.");
                    return true;
                }
            }
        } else if (complete)
            return true;

        return false;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
