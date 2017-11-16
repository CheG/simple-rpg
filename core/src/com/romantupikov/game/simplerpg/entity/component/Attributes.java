package com.romantupikov.game.simplerpg.entity.component;

/**
 * Created by hvitserk on 08-Nov-17.
 */

public class Attributes {
    private String name;
    private float level;
    private float maxHP;
    private float hp;
    private float strength;
    private float dexterity;
    private float vitality;
    private float intelligence;
    private float defence;
    private float moveSpeed;
    private float attackDelay;
    private float attackRange;
    private float castDelay;
    private float threat;

    private boolean dead;

    public Attributes(String name, float level,
                      float maxHP, float strength, float dexterity, float vitality, float intelligence,
                      float defence, float moveSpeed, float attackDelay, float attackRange, float castDelay) {
        this.name = name;
        this.level = level;
        this.maxHP = maxHP;
        this.strength = strength;
        this.dexterity = dexterity;
        this.vitality = vitality;
        this.intelligence = intelligence;
        this.defence = defence;
        this.moveSpeed = moveSpeed;
        this.attackDelay = attackDelay;
        this.attackRange = attackRange;
        this.castDelay = castDelay;

        this.hp = maxHP;
    }

    public Attributes(String name) {
        this(name, 1f,
                20f, 1f, 1f, 1f, 1f,
                0f, 1f, 2f, 1f, 2f);
    }

    public Attributes() {
        this("Dummy");
    }

    // == Getters / Setters ==

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = level;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
        this.hp = maxHP;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
        if (this.hp > maxHP) {
            this.hp = maxHP;
        }
    }

    public void addHP(float amount) {
        this.hp += amount;
        if (hp >= maxHP)
            hp = maxHP;
        else if (hp <= 0f) {
            hp = 0f;
            dead = true;
        }
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public float getDexterity() {
        return dexterity;
    }

    public void setDexterity(float dexterity) {
        this.dexterity = dexterity;
    }

    public float getVitality() {
        return vitality;
    }

    public void setVitality(float vitality) {
        this.vitality = vitality;
    }

    public float getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(float intelligence) {
        this.intelligence = intelligence;
    }

    public float getDefence() {
        return defence;
    }

    public void setDefence(float defence) {
        this.defence = defence;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public float getAttackDelay() {
        return attackDelay;
    }

    public void setAttackDelay(float attackDelay) {
        this.attackDelay = attackDelay;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public float getCastDelay() {
        return castDelay;
    }

    public void setCastDelay(float castDelay) {
        this.castDelay = castDelay;
    }

    public float getThreat() {
        return threat;
    }

    public void setThreat(float threat) {
        this.threat = threat;
    }

    public void addThreat(float threat) {
        this.threat += threat;
        if (threat <= 0f)
            threat = 0f;
    }

    public boolean isDead() {
        return dead;
    }
}
