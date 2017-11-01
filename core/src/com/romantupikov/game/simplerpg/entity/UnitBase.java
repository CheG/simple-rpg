package com.romantupikov.game.simplerpg.entity;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.romantupikov.utils.entity.EntityRectBase;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class UnitBase extends EntityRectBase {
    protected TextureRegion region;
    protected String name;
    protected int hp;
    protected int maxHp;

    protected int level;

    // Primary Stats
    protected int strength;
    protected int dexterity;
    protected int endurance;
    protected int spellpower;

    // Secondary Stats
    protected int defence;

    protected float attackAction;
    protected float takeDamageAction;

    public UnitBase(TextureRegion region, String name, int level) {
        super();
        this.region = region;
        this.name = name;
        this.level = level;
    }

    public void takeDamage(int dmg) {
        this.takeDamageAction = 1.0f;
        hp -= dmg;
    }

    public void render(final SpriteBatch batch) {
        if (takeDamageAction > 0) {
            batch.setColor(1f, 1f - takeDamageAction, 1f - takeDamageAction, 1f);
        }
//
//        container = new NinePatch(containerRegion, 5, 5, 2, 2);
//        //Offset it by the dynamic bar, let's say the gradient is 4 high.
//        container.draw(batch, 5, 8, totalBarWidth + 10, 8);
//        health.draw(batch, 10, 10, width, 4);

        float dx = (0.2f * (float) Math.sin((1f - attackAction) * 3.14f));
        if (region.isFlipX()) dx *= -1;
        batch.draw(region,
                position.x + dx, position.y,
                0, 0,
                width, height,
                1, 1,
                0);
        batch.setColor(1f, 1f, 1f, 1f);
    }

    public void update(float dt) {
        if (takeDamageAction > 0) {
            takeDamageAction -= dt;
        }
        if (attackAction > 0) {
            attackAction -= dt;
        }
    }

    public void meleeAttack(UnitBase enemy) {
        int dmg = this.strength - enemy.defence;
        if (dmg < 0) {
            dmg = 0;
        }
        this.attackAction = 1.0f;
        enemy.takeDamage(dmg);
    }

    public int getHp() {
        return hp;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getSpellpower() {
        return spellpower;
    }

    public int getDefence() {
        return defence;
    }

    public TextureRegion getRegion() {
        return region;
    }
}
