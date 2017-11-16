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
    public State handleInput(Unit unit, InputHandler input) {
        if (input.getAction() == InputHandler.Action.FOLLOW) {
            unit.getStates().removeFirst();
            return new FollowState();
        }
        if (input.getAction() == InputHandler.Action.MOVE) {
            unit.getStates().removeFirst();
            return new MovingState();
        }
        if (input.getAction() == InputHandler.Action.SUPPORT) {
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
    public void enter(Unit unit, InputHandler input) {
        super.enter(unit, input);
    }

    @Override
    public void update(Unit unit, float delta) {
        if (unit.getTarget() == null) {
            unit.getStates().removeFirst();
            return;
        }
        if (unit.getTarget().getAttributes().isDead()) {
            unit.setTarget(null);
            unit.getStates().removeFirst();
            return;
        }

        attackTimer += delta;

        if (unit.getPosition().cpy().sub(unit.getTarget().getPosition()).len() > unit.getAttributes().getAttackRange()) {
            State state = new FollowState();
            unit.addState(state);
        }

        if (attackTimer >= unit.getAttributes().getAttackDelay()) {
            attackTimer = 0f;
            Unit target = unit.getTarget();

            switch (unit.getHeroClass()) {
                case ARCHER:
                case WARRIOR:
                    warriorAttack(unit, target);
                    break;
                case MAGE:
                    mageAttack(unit, target);
                    break;
                case SUPPORT:
                    break;
                default:
                    warriorAttack(unit, target);
                    break;
            }
            Gdx.app.debug("", "[" + unit.getAttributes().getName() + "] attacking [" + target.getAttributes().getName() + "]");
        }
    }

    private void warriorAttack(Unit unit, Unit target) {
        unit.getAttributes().addThreat(5f);
        float damage = unit.getAttributes().getStrength();
        target.addEffect(unit.getController().getEffectFactory().createMeleeEffect(target, damage));
    }

    private void mageAttack(Unit unit, Unit target) {
        unit.getAttributes().addThreat(6f);
        float damage = unit.getAttributes().getIntelligence() + 1f;
        target.addEffect(unit.getController().getEffectFactory().createFireEffect(target, damage));
    }

    @Override
    public void exit(Unit unit, InputHandler input) {
        super.exit(unit, input);
    }
}
