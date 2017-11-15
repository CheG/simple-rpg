package com.romantupikov.game.simplerpg.entity.skill;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.Effect;

/**
 * Created by hvitserk on 15-Nov-17.
 */

public class AggroSkill extends SkillBase {
    public AggroSkill(Unit unit, Effect... subEffects) {
        super(unit, subEffects);
        name = "Aggro";
    }

    @Override
    public boolean execute() {
        float threatValue = unit.getAttributes().getStrength() + unit.getAttributes().getMaxHP();
        unit.getAttributes().addThreat(threatValue);
        return false;
    }
}
