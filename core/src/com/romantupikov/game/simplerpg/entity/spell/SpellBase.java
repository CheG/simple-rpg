package com.romantupikov.game.simplerpg.entity.spell;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.Effect;

/**
 * Created by hvitserk on 07-Nov-17.
 */

abstract class SpellBase implements Spell {
    protected String name;
    protected Unit unit;
    protected Effect[] subEffects;

    SpellBase(Unit unit,
              Effect... subEffects) {
        this.unit = unit;
        this.subEffects = subEffects;
        name = "Dummy";
    }

    @Override
    public String getName() {
        return name;
    }
}
