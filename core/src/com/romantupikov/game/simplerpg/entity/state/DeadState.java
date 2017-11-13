package com.romantupikov.game.simplerpg.entity.state;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;

/**
 * Created by hvitserk on 13-Nov-17.
 */

public class DeadState extends StateBase {
    @Override
    public State handleInput(Unit unit, InputHandler input) {

        return null;
    }

    @Override
    public void enter(Unit unit, InputHandler input) {
        super.enter(unit, input);
        unit.setRotation(-80f);
    }

    @Override
    public void update(Unit unit, float delta) {

    }

    @Override
    public void exit(Unit unit, InputHandler input) {
        super.exit(unit, input);
        unit.setRotation(0f);
    }
}
