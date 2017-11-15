package com.romantupikov.game.simplerpg.entity.skill;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.Effect;

/**
 * Created by hvitserk on 07-Nov-17.
 */

abstract class TargetSkillBase extends SkillBase {
    protected Unit target;

    TargetSkillBase(Unit unit,
                              Effect... subEffects) {
        super(unit, subEffects);
        this.target = unit.getTarget();
    }
}
