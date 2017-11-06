package com.romantupikov.game.simplerpg.entity.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.entity.Unit;

/**
 * Created by hvitserk on 06-Nov-17.
 */

public class AttackCommand implements Command {
    private Unit unit;
    private Unit target;

    private float attackTimer;

    public AttackCommand(Unit unit, Unit target) {
        this.unit = unit;
        this.target = target;
    }

    @Override
    public boolean execute(float delta) {
        attackTimer += delta;
        float distance = target.getPosition().cpy().sub(unit.getPosition()).len();
        if (distance > unit.getAttackRange()) {
            move(delta);
            return false;
        }
        if (attackTimer >= unit.getAttackSpeed()) {
            attackTimer = 0f;
            unit.attack(target);
            float damage = unit.getStrength() * 2f + unit.getLevel();
            Gdx.app.debug("", "Attack");
            Gdx.app.debug("", "=== Unit ===");
            Gdx.app.debug("", unit.toString());
            Gdx.app.debug("", "damage: " + (damage - (target.getDefence() / 2f)));
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
