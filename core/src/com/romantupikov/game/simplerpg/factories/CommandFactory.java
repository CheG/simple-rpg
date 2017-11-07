package com.romantupikov.game.simplerpg.factories;

import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.commands.AttackCommand;
import com.romantupikov.game.simplerpg.entity.commands.Command;
import com.romantupikov.game.simplerpg.entity.commands.HealCommand;
import com.romantupikov.game.simplerpg.entity.commands.MoveCommand;
import com.romantupikov.game.simplerpg.screens.game.GameController;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class CommandFactory {
    private final GameController controller;

    public CommandFactory(GameController controller) {
        this.controller = controller;
    }

    public Command createMoveCommand(Unit unit, Vector2 moveTo) {
        return new MoveCommand(unit, moveTo, controller);
    }

    public Command createHealCommand(Unit unit, Unit target) {
        return new HealCommand(unit, target, controller);
    }

    public Command createAttackCommand(Unit unit, Unit target) {
        return new AttackCommand(unit, target, controller);
    }
}
