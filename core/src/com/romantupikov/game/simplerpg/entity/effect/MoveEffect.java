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
        if (Math.abs(position.x - self.getX()) <= 0.05f && Math.abs(position.y - self.getY()) <= 0.05f) {
            if (visualEffect != null) {
                if (visualEffect.isComplete()) {
                    visualEffect.free();
                    return true;
                }
            }
        }

        if (visualEffect != null) {
            visualEffect.setPosition(self.getX(), self.getY());
            visualEffect.update(delta);
        }

        Vector2 dir = position.cpy().sub(self.getPosition()).nor();
        self.setPosition(self.getPosition().mulAdd(dir.scl(self.getSpeed()), delta));
        return false;
    }
}
