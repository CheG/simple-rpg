package com.romantupikov.game.simplerpg.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.entity.Unit;

/**
 * Created by hvitserk on 09-Nov-17.
 */

public class InputHandler implements GestureDetector.GestureListener {
    public enum Action {
        NON,
        MOVE,
        FOLLOW,
        ATTACK,
        CAST
    }

    private final GameController controller;
    private final Viewport viewport;

    Action action;
    private Vector2 movePosition;
    private Unit target;

    public InputHandler(GameController controller, Viewport viewport) {
        this.controller = controller;
        this.viewport = viewport;
        action = Action.NON;
        movePosition = new Vector2();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(x, y));
        Unit selectedUnit = controller.getSelectedUnit();
        Unit selectedEnemy = controller.getSelectedEnemy();

//        if (button == 1) {
//            Unit newEnemy = controller.getEntityFactory().createDummyUnit(RegionsNames.GOBLIN_ELITE_AXE, "Elite Goblin");
//            newEnemy.setPosition(worldTouch);
//            controller.getEnemyParty().add(newEnemy);
//            controller.notifyObservers();
//        }

        for (int i = 0; i < controller.getEnemyParty().size; i++) {
            Unit enemy = controller.getEnemyParty().get(i);
            if (enemy.getBounds().contains(worldTouch)) {
                selectedUnit.setTarget(enemy);
//                selectedUnit.activateSkill(0);
                controller.notifyObservers();
                return false;
            }
        }
        for (int i = 0; i < controller.getPlayerParty().size; i++) {
            Unit hero = controller.getPlayerParty().get(i);
            if (hero.getBounds().contains(worldTouch)) {
                selectedUnit.setTarget(hero);
//                selectedUnit.activateSkill(1);
                if (hero != selectedUnit) {
                    if (button == 1) {
                        action = Action.FOLLOW;
                    } else {
                        controller.setSelectedUnit(hero);
                    }
                    Gdx.app.debug("", "Selected Hero\n" + hero.toString());
                }
                controller.notifyObservers();
                return false;
            }
        }

        if (controller.getSelectedUnit() != null && button == 0) {
            movePosition = worldTouch.cpy();
            selectedUnit.setMoveTo(movePosition);
            action = Action.MOVE;
        }
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
//        Vector2 worldTouch = viewport.unproject(new Vector2(x, y));
//        Unit selectedUnit = controller.getSelectedUnit();
//        Unit selectedEnemy = controller.getSelectedEnemy();
//
//        for (int i = 0; i < controller.getPlayerParty().size; i++) {
//            Unit hero = controller.getPlayerParty().get(i);
//            if (hero.getBounds().contains(worldTouch)) {
//                selectedUnit.setTarget(hero);
////                selectedUnit.activateSkill(1);
//                if (hero != selectedUnit) {
//                    Gdx.app.debug("", "Selected Hero\n" + hero.toString());
//                    controller.setSelectedUnit(hero);
////                    action = Action.FOLLOW;
//                }
//                controller.notifyObservers();
//                return false;
//            }
//        }

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

    public Vector2 getMovePosition() {
        return movePosition;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Unit getTarget() {
        return target;
    }
}
