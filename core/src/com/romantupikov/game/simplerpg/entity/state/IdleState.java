package com.romantupikov.game.simplerpg.entity.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.InputHandler;

/**
 * Created by hvitserk on 09-Nov-17.
 */

public class IdleState implements State {

    public IdleState() {

    }

    @Override
    public void enter(Unit unit, InputHandler input) {
        input.setAction(InputHandler.Action.NON);
    }

    @Override
    public State handleInput(Unit unit, InputHandler input) {
        if (input.getAction() == InputHandler.Action.FOLLOW) {
            Gdx.app.debug("", "return new follow state from idle state");
            return new FollowState();
        }
        if (input.getAction() == InputHandler.Action.MOVE) {
            Gdx.app.debug("", "return new move state from idle state");
            return new MovingState();
        }

        return null;
    }

    @Override
    public void update(Unit unit, float delta) {

    }

    @Override
    public void exit(Unit unit, InputHandler input) {

    }
}
