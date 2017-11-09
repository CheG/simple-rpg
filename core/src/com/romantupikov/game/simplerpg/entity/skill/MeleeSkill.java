package com.romantupikov.game.simplerpg.entity.skill;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.Effect;
import com.romantupikov.game.simplerpg.factory.EffectFactory;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class MeleeSkill extends TargetSkillBase {
    private float attackTimer;

    public MeleeSkill(EffectFactory effectFactory,
                      Unit unit,
                      Effect... subEffects) {
        super(effectFactory, unit, subEffects);
    }

    @Override
    public boolean execute(float delta) {
        target = self.getTarget();
        if (target == null) {
            return true;
        }

        attackTimer += delta;

        float distance = target.getPosition().cpy().sub(self.getPosition()).len();
        if (distance > self.getAttributes().getAttackRange()) {
            move(delta);
            return false;
        }

        if (attackTimer >= self.getAttributes().getAttackDelay()) {
            attackTimer = 0f;
            self.getAttributes().addThreat(3f);
            float value = self.getAttributes().getStrength() + self.getAttributes().getLevel() * 1.5f;
            target.addEffect(effectFactory.createMeleeEffect(target, value));
            if (subEffects.length != 0)
                target.addEffects(subEffects);
        }
        return false;
    }
}
