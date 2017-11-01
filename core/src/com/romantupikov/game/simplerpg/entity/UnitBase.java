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

public class UnitBase extends EntityRectBase {
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

    protected float attackAction;
    protected float takeDamageAction;

    float delta;

    public UnitBase(TextureRegion region, String name, int level) {
        super();
        this.region = region;
        this.name = name;
        this.level = level;
        dead = false;
    }

    public void takeDamage(float dmg) {
        this.takeDamageAction = 1.0f;
        hp -= dmg;
        if (hp <= 0f)
            death();
    }

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
        }
    }

    private void death() {
        dead = true;
//        setPosition(position.x, position.y + height - 0.1f);
    }

    public boolean isDead() {
        return dead;
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

    public float getMaxHp() {
        return maxHp;
    }

    public TextureRegion getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "=== " + name + " ===" +
                "\nlevel: " + level +
                "\nhealth: " + hp + "/" + maxHp +
                "\nhit chance: " + (BASE_HIT_CHANCE + dexterity * 1.5f);
    }
}
