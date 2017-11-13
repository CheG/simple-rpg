package com.romantupikov.game.simplerpg.entity.state;

import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;

/**
 * Created by hvitserk on 09-Nov-17.
 */

public class MovingState extends StateBase {
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
    public void enter(Unit unit, InputHandler input) {
        super.enter(unit, input);
    }

    @Override
    public void update(Unit unit, float delta) {
        if (unit.getMoveTo().cpy().sub(unit.getPosition()).len() <= 0.1f) {
            unit.getStates().removeFirst();
        }

        Vector2 dir = unit.getMoveTo().cpy().sub(unit.getPosition()).nor();
        unit.setPosition(unit.getPosition().mulAdd(dir.scl(unit.getAttributes().getMoveSpeed()), delta));
    }

    @Override
    public void exit(Unit unit, InputHandler input) {
        super.exit(unit, input);
    }
}
