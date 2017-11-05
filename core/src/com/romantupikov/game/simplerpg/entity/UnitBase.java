package com.romantupikov.game.simplerpg.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.romantupikov.utils.MaterialColor;
import com.romantupikov.utils.entity.EntityRectBase;

/**
 * Created by hvitserk on 01-Nov-17.
 */

// TODO: 05-Nov-17 слишком сильно разресся энтот класс!
public class UnitBase extends EntityRectBase implements Comparable<UnitBase> {
    private static final float BASE_HIT_CHANCE = 60f;

    protected TextureRegion region;

    protected String name;
    protected float hp;
    protected float maxHp;
    protected float level;
    protected boolean dead;

    // Primary Stats
    protected float strength;
    protected float dexterity;
    protected float endurance;
    protected float spellpower;

    // Secondary Stats
    protected float defence;
    protected float threat;

    protected float attackAction;
    protected float takeDamageAction;

    // == controller fields ==
    protected boolean moved;

    float delta;

    public UnitBase(TextureRegion region, String name, float level) {
        super();
        this.region = region;
        this.name = name;
        this.level = level;
        dead = false;
        moved = false;
    }

    // == public methods ==

    public void render(final SpriteBatch batch) {
        if (takeDamageAction > 0) {
            batch.setColor(1f, 1f - takeDamageAction, 1f - takeDamageAction, 1f);
        }
        if (dead) {
            batch.setColor(MaterialColor.BLUE_GREY);
            deathAnim();
        }

        float dx = (0.2f * (float) Math.sin((1f - attackAction) * 3.14f));
        if (region.isFlipX()) dx *= -1;
        batch.draw(region,
                position.x + dx, position.y,
                origX, origY,
                width, height,
                1f, 1f,
                rotation);
        batch.setColor(1f, 1f, 1f, 1f);
    }

    public void update(float dt) {
        delta = dt;
        if (takeDamageAction > 0) {
            takeDamageAction -= dt;
        }
        if (attackAction > 0) {
            attackAction -= dt;
        }
    }

    public void meleeAttack(UnitBase enemy) {
        float dmg = this.strength - enemy.defence;
        if (dmg < 0) {
            dmg = 0;
        }
        this.attackAction = 0.7f;
        if (MathUtils.random(100) <= BASE_HIT_CHANCE + dexterity * 1.5f) {
            enemy.takeDamage(dmg);
            addThreat(2f);
        }
        moved = true;
    }

    // == private methods

    private void takeDamage(float dmg) {
        this.takeDamageAction = 1.0f;
        hp -= dmg;
        if (hp <= 0f)
            death();
    }

    private void deathAnim() {
        origX = width / 2f;
        origY = height / 2f;
        if (region.isFlipX()) {
            if (rotation >= -60f) {
                rotation -= delta * 350f;
            }
        } else
        if (rotation <= 60f) {
            rotation += delta * 350f;
        }
    }

    private void death() {
        threat = -1f;
        dead = true;
    }

    // == getters/setters ==

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isDead() {
        return dead;
    }

    public void addThreat(float threat) {
        this.threat += threat;
        if (this.threat < 0f)
            this.threat = 0f;
    }

    public float getHp() {
        return hp;
    }

    public float getStrength() {
        return strength;
    }

    public float getDexterity() {
        return dexterity;
    }

    public float getEndurance() {
        return endurance;
    }

    public float getSpellpower() {
        return spellpower;
    }

    public float getDefence() {
        return defence;
    }

    public float getThreat() {
        return threat;
    }

    public float getMaxHp() {
        return maxHp;
    }

    public boolean isMoved() {
        return moved;
    }

    public TextureRegion getRegion() {
        return region;
    }

    // == override methods ==

    @Override
    public int compareTo(UnitBase unitBase) {
        if (this.threat > unitBase.threat)
            return -1;
        if (this.threat < unitBase.threat)
            return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return "=== " + name + " ===" +
                "\n\tlevel: " + level +
                "\n\tthreat: " + threat +
                "\n\thealth: " + hp + "/" + maxHp +
                "\n\thit chance: " + (BASE_HIT_CHANCE + dexterity * 1.5f);
    }
}
