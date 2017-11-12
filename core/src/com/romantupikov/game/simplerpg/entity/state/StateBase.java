package com.romantupikov.game.simplerpg.entity.state;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;

/**
 * Created by hvitserk on 11-Nov-17.
 */

public abstract class StateBase implements State {
    @Override
    public void enter(Unit unit, InputHandler input) {
        input.setAction(InputHandler.Action.NON);
    }

    @Override
    public State handleInput(Unit unit, InputHandler input) {
        return null;
    }

    @Override
    public void update(Unit unit, float delta) {
    }

    @Override
    public void exit(Unit unit, InputHandler input) {

    }
}
