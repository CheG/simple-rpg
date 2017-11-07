package com.romantupikov.game.simplerpg.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.romantupikov.game.simplerpg.entity.commands.Command;
import com.romantupikov.utils.MaterialColor;
import com.romantupikov.utils.entity.EntityCircleBase;

/**
 * Created by hvitserk on 06-Nov-17.
 */

public class Unit extends EntityCircleBase implements Comparable<Unit> {
    public enum Class {
        HEALER,
        WARRIOR,
        RANGER,
        WIZZARD
    }

    private Class unitClass;

    private TextureRegion region;
    private TextureRegion barRegion;

    private String name;
    private float level;

    private float maxHP;
    private float hp;

    private float strength;
    private float dexterity;
    private float vitality;
    private float intelligence;

    private float defence;
    private float speed = 1f;
    private float attackSpeed = 1.5f;
    private float attackRange = 1.0f;
    private float threat;

    private Command command;

    public Unit(TextureRegion region, TextureRegion barRegion, String name, float level) {
        super();
        this.region = region;
        this.barRegion = barRegion;
        this.name = name;
        this.level = level;
        this.maxHP = level * 100f;
        this.hp = maxHP;
        this.strength = level;
        this.dexterity = level;
        this.vitality = level;
        this.intelligence = level;
        this.defence = level / 2f;
        this.threat = level * 2f;
    }

    public void update(float delta) {
        if (command != null && command.execute(delta)) {
            Gdx.app.debug("", "Command return true");
            command = null;
        }

        // TODO: 06-Nov-17 сделать отдельный метод для обновления статусов
        threat -= delta;
        if (threat <= 0f)
            threat = 0f;
    }

    public void render(final SpriteBatch batch) {
        batch.draw(region,
                position.x, position.y,
                origX, origY,
                width, height,
                1f, 1f,
                rotation);

        batch.setColor(MaterialColor.RED_900);
        batch.draw(barRegion,
                position.x, position.y + height + 0.1f,
                0f, 0f,
                width, height / 8f,
                1f, 1f,
                rotation);
        batch.setColor(MaterialColor.LIGHT_GREEN);
        batch.draw(barRegion,
                position.x, position.y + height + 0.1f,
                0f, 0f,
                width, height / 8f,
                hp * (1f / maxHP), 1,
                rotation);
        batch.setColor(Color.WHITE);
    }

    public void setThreat(float threat) {
        this.threat = threat;
    }

    public float getThreat() {
        return threat;
    }

    public void attack(Unit target) {
        float damage = strength * 2f + level;
        threat += 2f;
        target.takeDamage(damage, this);
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    private void takeDamage(float damage, Unit attacker) {
        float absorbedDmg = damage - (defence / 2f);
        hp -= absorbedDmg;
        if (hp <= 0f)
            hp = 0f;
//        if (command == null)
//            setCommand(new AttackCommand(this, attacker));
    }

    public void addHP(float amount) {
        this.hp += amount;
        if (hp >= maxHP)
            hp = maxHP;
    }

    public void addAggro(float aggro) {
        threat += aggro;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public String getName() {
        return name;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public float getHp() {
        return hp;
    }

    public float getStrength() {
        return strength;
    }

    public float getDefence() {
        return defence;
    }

    public float getDexterity() {
        return dexterity;
    }

    public float getVitality() {
        return vitality;
    }

    public float getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(float intelligence) {
        this.intelligence = intelligence;
    }

    public float getLevel() {
        return level;
    }

    public Class getUnitClass() {
        return unitClass;
    }

    public void setUnitClass(Class unitClass) {
        this.unitClass = unitClass;
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public int compareTo(Unit unit) {
        if (this.threat > unit.getThreat())
            return -1;
        else if (this.threat < unit.getThreat())
            return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return "=== " + name + " ===" +
                "\n\thealth: " + hp + "/" + maxHP;
    }
}
