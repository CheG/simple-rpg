package com.romantupikov.game.simplerpg.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.entity.Unit;

/**
 * Created by hvitserk on 09-Nov-17.
 */

public class InputHandler implements GestureDetector.GestureListener {
    private final GameController controller;
    private final Viewport viewport;

    public InputHandler(GameController controller, Viewport viewport) {
        this.controller = controller;
        this.viewport = viewport;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(x, y));

        if (button == 1) {
            Unit newEnemy = controller.getEntityFactory().createDummyUnit(RegionsNames.GOBLIN_ELITE_AXE, "Elite Goblin");
            newEnemy.setPosition(worldTouch);
            controller.getEnemyParty().add(newEnemy);
            controller.notifyObservers();
        }

        for (int i = 0; i < controller.getEnemyParty().size; i++) {
            Unit enemy = controller.getEnemyParty().get(i);
            if (enemy.getBounds().contains(worldTouch)) {
                controller.getSelectedUnit().setTarget(enemy);
                controller.getSelectedUnit().activateSkill(0);
                controller.notifyObservers();
                return false;
            }
        }
        for (int i = 0; i < controller.getPlayerParty().size; i++) {
            Unit hero = controller.getPlayerParty().get(i);
            if (hero.getBounds().contains(worldTouch)) {
                controller.getSelectedUnit().setTarget(hero);
                controller.getSelectedUnit().activateSkill(1);
                if (hero != controller.getSelectedUnit()) {
                    Gdx.app.debug("", "Selected Hero\n" + hero.toString());
                    controller.setSelectedUnit(hero);
                }
                controller.notifyObservers();
                return false;
            }
        }

        if (controller.getSelectedUnit() != null) {
            // перемещение
        }
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
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
