package com.romantupikov.game.simplerpg.screen.game.input;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.GameController;

/**
 * Created by hvitserk on 11-Nov-17.
 */

public class PlayerInput extends InputHandler implements GestureDetector.GestureListener {
    private final Viewport viewport;

    public PlayerInput(GameController controller, Viewport viewport) {
        super(controller);
        this.viewport = viewport;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(x, y));
        for (int i = 0; i < getController().getPlayerParty().size; i++) {
            Unit ally = getController().getPlayerParty().get(i);
            if (ally.getBounds().contains(worldTouch)) {
                setSelectedUnit(ally);
                getController().setSelectedUnit(ally);
                getController().notifyObservers();
                return false;
            }
        }

        setSelectedUnit(null);

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(x, y));

        Unit ally;

        for (int i = 0; i < getController().getPlayerParty().size; i++) {
            ally = getController().getPlayerParty().get(i);
            if (ally.getBounds().contains(worldTouch)) {
                getController().setSelectedUnit(ally);
                getController().notifyObservers();
                return false;
            }
        }

        ally = getController().getSelectedUnit();

        if (ally != null) {
            ally.setMoveTo(worldTouch);
            setAction(Action.MOVE);
            return false;
        }


        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        Vector2 worldTouch = viewport.unproject(new Vector2(x, y));

        Unit unit = getController().getEntityFactory().createDummyAIUnit(RegionsNames.GOBLIN_NINJA, "Goblin", Unit.HeroClass.WARRIOR);
        unit.setPosition(worldTouch);
        getController().getEnemyParty().add(unit);
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        if (getSelectedUnit() != null) {
            Vector2 worldTouch = viewport.unproject(new Vector2(x, y));
            for (int i = 0; i < getController().getEnemyParty().size; i++) {
                Unit enemy = getController().getEnemyParty().get(i);
                if (enemy.getBounds().contains(worldTouch)) {
                    getSelectedUnit().setTarget(enemy);
                    getController().setSelectedEnemy(enemy);
                    setAction(Action.ATTACK);
                    getController().notifyObservers();
                    return false;
                }
            }

            for (int i = 0; i < getController().getPlayerParty().size; i++) {
                Unit ally = getController().getPlayerParty().get(i);
                if (ally.getBounds().contains(worldTouch)) {
                    getSelectedUnit().setTarget(ally);
                    setAction(Action.SUPPORT);
                    getController().notifyObservers();
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
