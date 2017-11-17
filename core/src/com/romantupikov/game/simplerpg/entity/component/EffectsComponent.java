package com.romantupikov.game.simplerpg.entity.component;

import com.badlogic.gdx.utils.Array;
import com.romantupikov.game.simplerpg.entity.effect.Effect;

/**
 * Created by hvitserk on 17-Nov-17.
 */

public class EffectsComponent implements Component {
    private Array<Effect> effects;

    public EffectsComponent() {
        effects = new Array<Effect>();
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void addEffects(Effect... effects) {
        this.effects.addAll(effects);
    }


    public Array<Effect> getEffects() {
        return effects;
    }
}
