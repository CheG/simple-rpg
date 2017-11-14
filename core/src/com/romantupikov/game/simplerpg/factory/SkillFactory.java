package com.romantupikov.game.simplerpg.factory;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.skill.MassHeal;
import com.romantupikov.game.simplerpg.entity.skill.Skill;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class SkillFactory {
    private final EffectFactory effectFactory;

    public SkillFactory(EffectFactory effectFactory) {
        this.effectFactory = effectFactory;
    }

    public Skill massHealSkill(Unit unit) {
        MassHeal massHeal = new MassHeal(unit);
        return massHeal;
    }
}
