package com.romantupikov.game.simplerpg.entity.skill;

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
        if (distance > self.getAttackRange()) {
            move(delta);
            return false;
        }

        if (healTimer >= self.getCastSpeed()) {
            healTimer = 0f;
            self.addAggro(4f);
            float value = self.getIntelligence() + self.getLevel() * 1.5f;
            target.addEffect(effectFactory.createHealEffect(target, value));
            if (subEffects.length != 0)
                target.addEffects(subEffects);
        }

        return false;
    }
}
