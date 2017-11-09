package com.romantupikov.game.simplerpg.entity.skill;

import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.Effect;
import com.romantupikov.game.simplerpg.factory.EffectFactory;

/**
 * Created by hvitserk on 07-Nov-17.
 */

abstract class TargetSkillBase extends SkillBase {
    protected Unit target;

    protected TargetSkillBase(EffectFactory effectFactory,
                              Unit unit,
                              Effect... subEffects) {
        super(effectFactory, unit, subEffects);
        this.target = unit.getTarget();
    }

    protected boolean move(float delta) {
        if (self.getBounds().overlaps(target.getBounds()))
            return true;
        Vector2 dir = target.getPosition().cpy().sub(self.getPosition()).nor();
        self.setPosition(self.getPosition().mulAdd(dir.scl(self.getAttributes().getMoveSpeed()), delta));
        return false;
    }
}
