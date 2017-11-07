package com.romantupikov.game.simplerpg.entity.skill;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.Effect;
import com.romantupikov.game.simplerpg.factory.EffectFactory;

/**
 * Created by hvitserk on 07-Nov-17.
 */

abstract class SkillBase implements Skill {
    protected final EffectFactory effectFactory;

    protected Unit self;

    protected Effect[] subEffects;

    protected SkillBase(EffectFactory effectFactory,
                        Unit unit,
                        Effect... subEffects) {
        this.effectFactory = effectFactory;
        this.self = unit;
        this.subEffects = subEffects;
    }
}
