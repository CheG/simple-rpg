package com.romantupikov.game.simplerpg.entity.state;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.InputHandler;

/**
 * Created by hvitserk on 09-Nov-17.
 */

public interface UnitState {
    void handleInput(Unit unit, InputHandler input);
    void update(Unit unit);
}
