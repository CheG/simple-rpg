package com.romantupikov.game.simplerpg.ai;

import com.badlogic.gdx.utils.Array;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.screen.game.GameController;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;

/**
 * Created by hvitserk on 11-Nov-17.
 */

public class EasyAI extends InputHandler {
    private static final float ACTION_DELAY = 1f;
    private float actionTimer;

    private Array<Unit> aiParty;
    private Array<Unit> playerParty;

    private int unitIndex;

    public EasyAI(GameController controller) {
        super(controller);
    }

    public void update(float delta) {
        actionTimer += delta;

        if (actionTimer >= ACTION_DELAY) {
            actionTimer = 0f;
            aiParty = getController().getEnemyParty();
            playerParty = getController().getPlayerParty();

            if (aiParty.size == 0 || playerParty.size == 0) {
                return;
            }

            if (unitIndex >= aiParty.size) {
                unitIndex = 0;
            }

            Unit unit = aiParty.get(unitIndex);
            if (unit.getAttributes().isDead()) {
                actionTimer = ACTION_DELAY;
                unitIndex++;
                return;
            }

            setSelectedUnit(unit);
            getController().setAiSelectedUnit(unit);
            getController().notifyObservers();

            switch (unit.getHeroClass()) {
                case WARRIOR:
                    warriorLogic(unit);
                    break;

                case SUPPORT:
                    supportLogic(unit);
                    break;
            }

            unitIndex++;
        }
    }

    private void warriorLogic(Unit unit) {
        Unit enemy = playerParty.first();
        if (enemy != null && enemy != unit.getTarget()) {
            unit.setTarget(enemy);
            setAction(Action.ATTACK);
        }
    }

    private void supportLogic(Unit unit) {
        Unit minHPUnit = aiParty.first();
        for (int i = 1; i < aiParty.size; i++) {
            Unit ally = aiParty.get(i);
            if (ally.getAttributes().getHp() < minHPUnit.getAttributes().getHp())
                minHPUnit = ally;
        }
        if (minHPUnit != unit.getTarget()) {
            unit.setTarget(minHPUnit);
            setAction(Action.SUPPORT);
        }
    }
}
