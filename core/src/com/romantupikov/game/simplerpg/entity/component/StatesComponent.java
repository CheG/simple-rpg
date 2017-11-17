package com.romantupikov.game.simplerpg.entity.component;

import com.badlogic.gdx.utils.Queue;
import com.romantupikov.game.simplerpg.entity.state.IdleState;
import com.romantupikov.game.simplerpg.entity.state.State;

/**
 * Created by hvitserk on 17-Nov-17.
 */

public class StatesComponent implements Component {
    private Queue<State> states;

    public StatesComponent() {
        states = new Queue<State>();
        states.addFirst(new IdleState());
    }
}
