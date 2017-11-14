package com.romantupikov.game.simplerpg.entity.state;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;

/**
 * Created by hvitserk on 13-Nov-17.
 */

public class DeadState extends StateBase {
    private final float ROTATION_SPEED = 210f;
    private final float ROTATION_ANGLE = 85f;
    @Override
    public State handleInput(Unit unit, InputHandler input) {

        return null;
    }

    @Override
    public void enter(Unit unit, InputHandler input) {
        super.enter(unit, input);
    }

    @Override
    public void update(Unit unit, float delta) {
        float rotation = unit.getRotation();
        if (unit.isLookingLeft()) {
            if (rotation < ROTATION_ANGLE) {
                unit.setRotation(rotation + delta * ROTATION_SPEED);
            }
        } else if (unit.isLookingRight()){
            if (rotation > -ROTATION_ANGLE) {
                unit.setRotation(rotation - delta * ROTATION_SPEED);
            }
        }

    }

    @Override
    public void exit(Unit unit, InputHandler input) {
        super.exit(unit, input);
        unit.setRotation(0f);
    }
}
