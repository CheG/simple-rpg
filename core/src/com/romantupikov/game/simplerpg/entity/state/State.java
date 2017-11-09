package com.romantupikov.game.simplerpg.entity.state;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.InputHandler;

/**
 * Created by hvitserk on 09-Nov-17.
 */

public interface State {
    void enter(Unit unit, InputHandler input);
    State handleInput(Unit unit, InputHandler input);
    void update(Unit unit, float delta);
    void exit(Unit unit, InputHandler input);
}
