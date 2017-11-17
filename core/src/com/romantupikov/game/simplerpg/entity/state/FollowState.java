package com.romantupikov.game.simplerpg.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.MoveEffect;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;

/**
 * Created by hvitserk on 09-Nov-17.
 */

public class FollowState extends StateBase {
    private MoveEffect moveEffect;

    @Override
    public State handleInput(Unit unit, InputHandler input) {
        if (input.getAction() == InputHandler.Action.FOLLOW) {
            exit(unit, input);
            unit.getStates().removeFirst();

            return new FollowState();
        }
        if (input.getAction() == InputHandler.Action.MOVE) {
            exit(unit, input);
            unit.getStates().removeFirst();

            return new MovingState();
        }
        if (input.getAction() == InputHandler.Action.ATTACK) {
            exit(unit, input);
            unit.getStates().removeFirst();

            return new AttackState();
        }
        if (input.getAction() == InputHandler.Action.SUPPORT) {
            exit(unit, input);
            unit.getStates().removeFirst();

            return new SupportState();
        }
        return null;
    }

    @Override
    public void enter(Unit unit, InputHandler input) {
        super.enter(unit, input);
        moveEffect = unit.getController().getEffectFactory().createMoveEffect(unit);
        unit.addEffect(moveEffect);
    }

    @Override
    public void update(Unit unit, float delta) {
        if (unit.getTarget() == null) {
            moveEffect.setComplete(true);
            unit.getStates().removeFirst();
            return;
        }

        if (unit.getTarget().getAttributes().isDead()) {
            unit.setTarget(null);
            unit.getStates().removeFirst();
            return;
        }

        Vector2 targetPos = unit.getTarget().getPosition();

        if (unit.getPosition().cpy().sub(targetPos).len() < unit.getAttributes().getAttackRange() - 0.1f) {
            moveEffect.setComplete(true);
            unit.getStates().removeFirst();
        }

        Vector2 dir = targetPos.cpy().sub(unit.getPosition()).nor();
        unit.setPosition(unit.getPosition().mulAdd(dir.scl(unit.getAttributes().getMoveSpeed()), delta));
    }

    @Override
    public void exit(Unit unit, InputHandler input) {
        super.exit(unit, input);
        moveEffect.setComplete(true);
    }
}
