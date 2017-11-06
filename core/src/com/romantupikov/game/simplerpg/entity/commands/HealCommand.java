package com.romantupikov.game.simplerpg.entity.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;

import java.util.logging.Level;

/**
 * Created by hvitserk on 06-Nov-17.
 */

public class HealCommand implements Command {
    private Unit unit;
    private Unit target;

    private float healTimerTimer;

    public HealCommand(Unit unit, Unit target) {
        this.unit = unit;
        this.target = target;
    }

    @Override
    public boolean execute(float delta) {
        healTimerTimer += delta;
        float distance = target.getPosition().cpy().sub(unit.getPosition()).len();
        if (distance > unit.getAttackRange()) {
            move(delta);
            return false;
        }
        if (healTimerTimer >= unit.getAttackSpeed()) {
            healTimerTimer = 0f;
            target.addHP(unit.getIntelligence() + unit.getLevel());
            unit.addAggro(4f);
            Gdx.app.debug("", "Heal");
            Gdx.app.debug("", "=== Unit ===");
            Gdx.app.debug("", unit.toString());
            Gdx.app.debug("", "heal: " + (unit.getIntelligence() + unit.getLevel()));
            Gdx.app.debug("", "=== Target ===");
            Gdx.app.debug("", target.toString());
            Gdx.app.debug("", "=============");
        }
        return false;
    }

    private boolean move(float delta) {
        if (unit.getBounds().overlaps(target.getBounds()))
            return true;
        Vector2 dir = target.getPosition().cpy().sub(unit.getPosition()).nor();
        unit.setPosition(unit.getPosition().mulAdd(dir.scl(unit.getSpeed()), delta));
        return false;
    }

    public Unit getTarget() {
        return target;
    }
}
