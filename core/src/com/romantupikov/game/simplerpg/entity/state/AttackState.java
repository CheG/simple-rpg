package com.romantupikov.game.simplerpg.entity.state;

import com.badlogic.gdx.Gdx;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;

/**
 * Created by hvitserk on 11-Nov-17.
 */

public class AttackState extends StateBase {
    private float attackTimer;

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
        if (input.getAction() == InputHandler.Action.SUPPORT &&
                unit.getHeroClass() == Unit.HeroClass.SUPPORT) {
            unit.getStates().removeFirst();
            return new SupportState();
        }
        if (input.getAction() == InputHandler.Action.ATTACK) {
            unit.getStates().removeFirst();
            return new AttackState();
        }
        return null;
    }

    @Override
    public void update(Unit unit, float delta) {
        attackTimer += delta;

        if (unit.getPosition().cpy().sub(unit.getTarget().getPosition()).len() >= unit.getAttributes().getAttackRange()) {
            unit.getStates().addFirst(new FollowState());
        }

        if (attackTimer >= unit.getAttributes().getAttackDelay()) {
            attackTimer = 0f;
            Gdx.app.debug("", "HIT!");
            unit.getAttributes().addThreat(5f);
            Unit target = unit.getTarget();
            float damage = unit.getAttributes().getStrength();
            unit.addEffect(unit.getController().getEffectFactory().createMeleeEffect(target, damage));
        }
    }

    @Override
    public void exit(Unit unit, InputHandler input) {
    }
}
