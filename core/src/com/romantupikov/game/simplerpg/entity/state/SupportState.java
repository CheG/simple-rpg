package com.romantupikov.game.simplerpg.entity.state;

import com.badlogic.gdx.Gdx;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;

/**
 * Created by hvitserk on 11-Nov-17.
 */

public class SupportState extends StateBase {
    private float castTimer;

    @Override
    public void enter(Unit unit, InputHandler input) {
        super.enter(unit, input);
    }

    @Override
    public State handleInput(Unit unit, InputHandler input) {
        if (input.getAction() == InputHandler.Action.FOLLOW) {
            unit.getStates().removeFirst();
            return new FollowState();
        }
        if (input.getAction() == InputHandler.Action.MOVE) {
            unit.getStates().removeFirst();
            return new MovingState();
        }
        if (input.getAction() == InputHandler.Action.ATTACK) {
            unit.getStates().removeFirst();
            return new AttackState();
        }
        if (input.getAction() == InputHandler.Action.SUPPORT &&
                unit.getHeroClass() == Unit.HeroClass.SUPPORT) {
            unit.getStates().removeFirst();
            return new SupportState();
        }
        return null;
    }

    @Override
    public void update(Unit unit, float delta) {
        castTimer += delta;

        if (unit.getTarget().getAttributes().isDead()) {
            unit.setTarget(null);
            unit.getStates().removeFirst();
            return;
        }

        if (unit.getPosition().cpy().sub(unit.getTarget().getPosition()).len() >= unit.getAttributes().getAttackRange()) {
            unit.getStates().addFirst(new FollowState());
        }

        if (castTimer >= unit.getAttributes().getCastDelay()) {
            castTimer = 0f;
            Gdx.app.debug("", "CAST!");
            unit.getAttributes().addThreat(4f);
            Unit target = unit.getTarget();
            float value = unit.getAttributes().getIntelligence();
            unit.addEffect(unit.getController().getEffectFactory().createHealEffect(target, value));
        }
    }

    @Override
    public void exit(Unit unit, InputHandler input) {
    }
}
