package com.romantupikov.game.simplerpg.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.romantupikov.game.simplerpg.entity.component.Attributes;
import com.romantupikov.game.simplerpg.entity.effect.Effect;
import com.romantupikov.game.simplerpg.entity.skill.Skill;
import com.romantupikov.utils.MaterialColor;
import com.romantupikov.utils.entity.EntityCircleBase;

/**
 * Created by hvitserk on 06-Nov-17.
 */

public class Unit extends EntityCircleBase implements Comparable<Unit> {
    private Unit target;

    private Attributes attributes;

    private TextureRegion region;
    private TextureRegion barRegion;

    private Array<Effect> effects;
    private Array<Skill> skills;

    private Skill activeSkill;

    public Unit(TextureRegion region, TextureRegion barRegion, Attributes attributes) {
        super();
        this.attributes = attributes;
        this.region = region;
        this.barRegion = barRegion;
        effects = new Array<Effect>(10);
        skills = new Array<Skill>(4);
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

        attributes.addThreat(-delta);
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
                attributes.getHp() * (1f / attributes.getMaxHP()), 1,
                rotation);
        batch.setColor(Color.WHITE);

        for (int i = 0; i < effects.size; i++) {
            Effect effect = effects.get(i);
            effect.render(batch);
        }
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

    public Unit getTarget() {
        return target;
    }

    public void setTarget(Unit target) {
        this.target = target;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    @Override
    public int compareTo(Unit unit) {
        if (this.attributes.getThreat() > unit.getAttributes().getThreat())
            return -1;
        else if (this.attributes.getThreat() < unit.getAttributes().getThreat())
            return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return "=== " + attributes.getName() + " ===" +
                "\n\thealth: " + attributes.getHp() + "/" + attributes.getMaxHP();
    }
}
