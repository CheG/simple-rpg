package com.romantupikov.game.simplerpg.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.romantupikov.game.simplerpg.entity.effect.Effect;
import com.romantupikov.game.simplerpg.entity.skill.MeleeSkill;
import com.romantupikov.game.simplerpg.entity.skill.Skill;
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

    private Unit target;

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
    private float castSpeed = 1.5f;
    private float threat;

    private Array<Effect> effects;
    private Array<Skill> skills;

    private Skill activeSkill;

    public Unit(TextureRegion region, TextureRegion barRegion, String name, float level) {
        super();
        this.region = region;
        this.barRegion = barRegion;
        effects = new Array<Effect>(10);
        skills = new Array<Skill>(4);
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
        if (activeSkill != null && activeSkill.execute(delta)) {
            activeSkill = null;
        }

        for (int i = 0; i < effects.size; i++) {
            Effect effect = effects.get(i);
            if (effect.update(delta)) {
                effects.removeIndex(i);
            }
        }

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

        for (int i = 0; i < effects.size; i++) {
            Effect effect = effects.get(i);
            effect.render(batch);
        }
    }

    public void setThreat(float threat) {
        this.threat = threat;
    }

    public float getThreat() {
        return threat;
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void addEffects(Effect... effects) {
        this.effects.addAll(effects);
    }

    // TODO: 07-Nov-17 Выбор скилла по названию, енаму или еще как, но не по индексу
    public void activateSkill(int index) {
        activeSkill = skills.get(index);
    }

    public Array<Effect> getEffects() {
        return effects;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public Array<Skill> getSkills() {
        return skills;
    }

    public void addHP(float amount) {
        this.hp += amount;
        if (hp >= maxHP)
            hp = maxHP;
    }

    public void subHP(float amount) {
        hp -= amount;
        if (hp <= 0f)
            hp = 0;
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

    public float getCastSpeed() {
        return castSpeed;
    }

    public void setCastSpeed(float castSpeed) {
        this.castSpeed = castSpeed;
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

    public Unit getTarget() {
        return target;
    }

    public void setTarget(Unit target) {
        this.target = target;
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
