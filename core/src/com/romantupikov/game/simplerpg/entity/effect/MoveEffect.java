package com.romantupikov.game.simplerpg.entity.effect;

import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class MoveEffect extends EffectBase {
    private Vector2 position;

    public MoveEffect(Unit unit, Vector2 position) {
        super(unit);
        this.position = position;
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

        if (Math.abs(position.x - self.getX()) <= 0.05f && Math.abs(position.y - self.getY()) <= 0.05f) {
            complete = true;
        }

        Vector2 dir = position.cpy().sub(self.getPosition()).nor();
        self.setPosition(self.getPosition().mulAdd(dir.scl(self.getAttributes().getMoveSpeed()), delta));
        return false;
    }
}
