package com.romantupikov.game.simplerpg.entity.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.InputHandler;

/**
 * Created by hvitserk on 09-Nov-17.
 */

public class MovingState implements State {
    public MovingState() {
    }

    @Override
    public void enter(Unit unit, InputHandler input) {
        input.setAction(InputHandler.Action.NON);
    }

    @Override
    public State handleInput(Unit unit, InputHandler input) {
        if (input.getAction() == InputHandler.Action.FOLLOW) {
            Gdx.app.debug("", "return new follow state from moving state");
            return new FollowState();
        }
        if (input.getAction() == InputHandler.Action.MOVE) {
            Gdx.app.debug("", "return new move state from moving state");
            return new MovingState();
        }
        return null;
    }

    @Override
    public void update(Unit unit, float delta) {
        if (Math.abs(unit.getMoveTo().x - unit.getX()) <= 0.05f
                && Math.abs(unit.getMoveTo().y - unit.getY()) <= 0.05f) {
            unit.setState(new IdleState());
        }

        Vector2 dir = unit.getMoveTo().cpy().sub(unit.getPosition()).nor();
        unit.setPosition(unit.getPosition().mulAdd(dir.scl(unit.getAttributes().getMoveSpeed()), delta));
    }

    @Override
    public void exit(Unit unit, InputHandler input) {
//        unit.setMoveTo(null);
    }
}
