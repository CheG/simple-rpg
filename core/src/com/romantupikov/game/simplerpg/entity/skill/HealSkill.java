package com.romantupikov.game.simplerpg.entity.skill;

import com.badlogic.gdx.Gdx;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.Effect;
import com.romantupikov.game.simplerpg.factory.EffectFactory;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class HealSkill extends TargetSkillBase {
    private float healTimer;

    public HealSkill(EffectFactory effectFactory,
                     Unit unit,
                     Effect... effects) {
        super(effectFactory, unit, effects);
    }

    @Override
    public boolean execute(float delta) {
        target = self.getTarget();
        if (target == null) {
            return true;
        }

        healTimer += delta;

        float distance = target.getPosition().cpy().sub(self.getPosition()).len();
        if (distance > self.getAttributes().getAttackRange()) {
            move(delta);
            return false;
        }

        if (healTimer >= self.getAttributes().getCastDelay()) {
            healTimer = 0f;
            self.getAttributes().addThreat(4f);
            float value = self.getAttributes().getIntelligence() + self.getAttributes().getLevel() * 1.5f;
            target.addEffect(effectFactory.createHealEffect(target, value));
            Gdx.app.debug("", "[" + self.getAttributes().getName() + "] use heal skill.");
            if (subEffects.length != 0)
                target.addEffects(subEffects);
        }

        return false;
    }
}
