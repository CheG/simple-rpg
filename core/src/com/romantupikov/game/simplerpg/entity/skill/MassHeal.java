package com.romantupikov.game.simplerpg.entity.skill;

import com.badlogic.gdx.utils.Array;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.Effect;

/**
 * Created by hvitserk on 14-Nov-17.
 */

public class MassHeal extends SkillBase {
    public MassHeal(Unit unit, Effect... subEffects) {
        super(unit, subEffects);
        name = "Mass Heal";
    }

    @Override
    public boolean execute() {
        float healAmount = unit.getAttributes().getIntelligence();
        Array<Unit> allyParty = unit.getController().getPlayerParty();
        for (int i = 0; i < allyParty.size; i++) {
            Unit ally = allyParty.get(i);
            ally.addEffect(unit.getController().getEffectFactory().createHealEffect(ally, healAmount));
        }
        return true;
    }
}
