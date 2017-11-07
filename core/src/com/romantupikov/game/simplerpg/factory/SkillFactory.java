package com.romantupikov.game.simplerpg.factory;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.skill.HealSkill;
import com.romantupikov.game.simplerpg.entity.skill.MeleeSkill;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class SkillFactory {
    private final EffectFactory effectFactory;

    public SkillFactory(EffectFactory effectFactory) {
        this.effectFactory = effectFactory;
    }

    public HealSkill createHealSkill(Unit unit) {
        HealSkill healSkill = new HealSkill(effectFactory, unit);
        return healSkill;
    }

    public MeleeSkill createMeleeSkill(Unit unit) {
        MeleeSkill meleeSkill = new MeleeSkill(effectFactory, unit);
        return meleeSkill;
    }
}
