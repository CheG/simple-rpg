package com.romantupikov.game.simplerpg.screen.game.input;

import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.GameController;

/**
 * Created by hvitserk on 09-Nov-17.
 */

public abstract class InputHandler {
    public enum Action {
        NON,
        MOVE,
        FOLLOW,
        ATTACK,
        SUPPORT,
        CAST
    }

    private final GameController controller;

    private Action action;
    private Unit selectedUnit;

    public InputHandler(GameController controller) {
        this.controller = controller;
        action = Action.NON;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public GameController getController() {
        return controller;
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }
}
