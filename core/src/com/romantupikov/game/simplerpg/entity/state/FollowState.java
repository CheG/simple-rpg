package com.romantupikov.game.simplerpg.entity.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.InputHandler;

import jdk.nashorn.internal.ir.IfNode;

/**
 * Created by hvitserk on 09-Nov-17.
 */

public class FollowState implements State {
    @Override
    public void enter(Unit unit, InputHandler input) {
        input.setAction(InputHandler.Action.NON);
    }

    @Override
    public State handleInput(Unit unit, InputHandler input) {
        if (input.getAction() == InputHandler.Action.FOLLOW) {
            Gdx.app.debug("", "return new follow state from follow state");
            return new FollowState();
        }
        if (input.getAction() == InputHandler.Action.MOVE) {
            Gdx.app.debug("", "return new move state from follow state");
            return new MovingState();
        }
        return null;
    }

    @Override
    public void update(Unit unit, float delta) {
        if (unit.getBounds().overlaps(unit.getTarget().getBounds())) {
            return;
        }

        Vector2 targetPos = unit.getTarget().getPosition();

        Vector2 dir = targetPos.cpy().sub(unit.getPosition()).nor();
        unit.setPosition(unit.getPosition().mulAdd(dir.scl(unit.getAttributes().getMoveSpeed()), delta));
    }

    @Override
    public void exit(Unit unit, InputHandler input) {

    }
}
