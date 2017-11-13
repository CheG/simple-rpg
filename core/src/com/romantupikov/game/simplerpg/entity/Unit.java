package com.romantupikov.game.simplerpg.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.romantupikov.game.simplerpg.entity.component.Attributes;
import com.romantupikov.game.simplerpg.entity.effect.Effect;
import com.romantupikov.game.simplerpg.entity.skill.Skill;
import com.romantupikov.game.simplerpg.entity.state.DeadState;
import com.romantupikov.game.simplerpg.entity.state.IdleState;
import com.romantupikov.game.simplerpg.entity.state.State;
import com.romantupikov.game.simplerpg.screen.game.GameController;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;
import com.romantupikov.utils.MaterialColor;
import com.romantupikov.utils.entity.EntityCircleBase;

/**
 * Created by hvitserk on 06-Nov-17.
 */

public class Unit extends EntityCircleBase implements Comparable<Unit> {
    public enum HeroClass {
        WARRIOR,
        MAGE,
        ARCHER,
        SUPPORT
    }
    private final GameController controller;
    private InputHandler input;
    private Unit target;
    private Vector2 moveTo;

    private Attributes attributes;
    private HeroClass heroClass;

    private TextureRegion region;
    private TextureRegion barRegion;

    private boolean flip;

    private Array<Effect> effects;
    private Array<Skill> skills;

    private Skill activeSkill;
    private Queue<State> states;

    public Unit(GameController controller, InputHandler input, TextureRegion region, TextureRegion barRegion, Attributes attributes, HeroClass heroClass) {
        super();
        this.controller = controller;
        this.input = input;
        this.attributes = attributes;
        this.heroClass = heroClass;
        this.region = region;
        this.barRegion = barRegion;
        this.states = new Queue<State>();
        states.addFirst(new IdleState());
        effects = new Array<Effect>(10);
        skills = new Array<Skill>(4);
        flip = false;
    }

    public void handleInput() {
        State state = states.first().handleInput(this, input);

        if (state != null) {
            addState(state);
        }
    }

    public void update(float delta) {
        if (attributes.isDead() && !(states.first() instanceof DeadState)) {
            target = null;
            states.clear();
            states.addFirst(new IdleState());
            State state = new DeadState();
            addState(state);
        }

        State state = states.first();
        state.update(this, delta);

        for (int i = 0; i < effects.size; i++) {
            Effect effect = effects.get(i);
            if (effect.update(delta)) {
                effects.removeIndex(i);
            }
        }

        attributes.addThreat(-delta);
    }

    public void render(final SpriteBatch batch) {
        turnToTarget();

        batch.draw(region,
                position.x, position.y,
                origX, origY,
                width, height,
                1f, 1f,
                rotation);

        if (!attributes.isDead()) {
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
        }

        for (int i = 0; i < effects.size; i++) {
            Effect effect = effects.get(i);
            effect.render(batch);
        }
    }

    private void turnToTarget() {
        if (target != null) {
            if (getPosition().x - target.getPosition().x < -0.1f) {
                flip = region.isFlipX();
            } else if (getPosition().x - target.getPosition().x > 0.1f) {
                flip = !region.isFlipX();
            }
            region.flip(flip, false);
        }
    }

    public void addState(State state) {
        Gdx.app.debug("", "[" + attributes.getName() + "] add state " + state.getClass().getSimpleName());
        states.first().exit(this, input);
        states.addFirst(state);
        state.enter(this, input);
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

    public Vector2 getMoveTo() {
        return moveTo;
    }

    public void setMoveTo(Vector2 moveTo) {
        this.moveTo = moveTo;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
    }

    public Queue<State> getStates() {
        return states;
    }

    public GameController getController() {
        return controller;
    }

    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
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
