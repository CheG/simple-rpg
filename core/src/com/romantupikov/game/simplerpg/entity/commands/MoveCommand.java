package com.romantupikov.game.simplerpg.entity.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screens.game.GameController;

/**
 * Created by hvitserk on 06-Nov-17.
 */

public class MoveCommand extends CommandBase {
    private Vector2 moveTo = new Vector2();
    private Unit unit;

    public MoveCommand(Unit unit, Vector2 moveTo, GameController controller) {
        super(controller);
        Gdx.app.debug("MoveCommand", "MoveTo: " + moveTo.toString() + " unit pos: " + unit.getPosition().toString());
        this.moveTo = moveTo;
        this.unit = unit;
    }

    @Override
    public boolean execute(float delta) {
        if (Math.abs(moveTo.x - unit.getX()) <= 0.05f && Math.abs(moveTo.y - unit.getY()) <= 0.05f)
            return true;
        Vector2 dir = moveTo.cpy().sub(unit.getPosition()).nor();
        unit.setPosition(unit.getPosition().mulAdd(dir.scl(unit.getSpeed()), delta));
        return false;
    }

    public Vector2 getMoveTo() {
        return moveTo;
    }
}
