package com.romantupikov.game.simplerpg.factory;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.spell.MassHealSpell;
import com.romantupikov.game.simplerpg.entity.spell.Spell;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class SpellFactory {
    private final EffectFactory effectFactory;

    public SpellFactory(EffectFactory effectFactory) {
        this.effectFactory = effectFactory;
    }

    public Spell massHealSpell(Unit unit) {
        MassHealSpell massHealSpell = new MassHealSpell(unit);
        return massHealSpell;
    }
}
