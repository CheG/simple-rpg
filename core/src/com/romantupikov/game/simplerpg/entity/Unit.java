package com.romantupikov.game.simplerpg.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.romantupikov.game.simplerpg.entity.component.Attributes;
import com.romantupikov.game.simplerpg.entity.component.Component;
import com.romantupikov.game.simplerpg.entity.effect.Effect;
import com.romantupikov.game.simplerpg.entity.spell.Spell;
import com.romantupikov.game.simplerpg.entity.state.IdleState;
import com.romantupikov.game.simplerpg.entity.state.State;
import com.romantupikov.game.simplerpg.screen.game.GameController;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;
import com.romantupikov.utils.entity.EntityBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hvitserk on 06-Nov-17.
 */

public class Unit extends EntityBase implements Comparable<Unit> {
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

    private boolean lookingLeft;
    private boolean lookingRight;

    private Array<Effect> effects;

    // TODO: 15-Nov-17 придумать реализацию получше
    private Spell supportSpell;
    private Spell defenceSpell;
    private Spell offenceSpell;

    private Queue<State> states;

    private Map<String, Component> components;

    public Unit(GameController controller, InputHandler input, TextureRegion region, Attributes attributes, HeroClass heroClass) {
        super();
        components = new HashMap<String, Component>();
        this.controller = controller;
        this.input = input;
        this.attributes = attributes;
        this.heroClass = heroClass;
        this.region = region;
        this.states = new Queue<State>();
        states.addFirst(new IdleState());
        effects = new Array<Effect>(10);
        this.lookingRight = true;
        // TODO: 16-Nov-17 лишний код
        supportSpell = null;
        defenceSpell = null;
        offenceSpell = null;
    }

    public void addComponent(String name, Component component) {
        components.put(name, component);
    }

    public void removeComponent(String name) {
        components.remove(name);
    }

    public Component getComponent(String name) {
        return components.get(name);
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


    public Array<Effect> getEffects() {
        return effects;
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

    public boolean isLookingLeft() {
        return lookingLeft;
    }

    public boolean isLookingRight() {
        return lookingRight;
    }

    public void setLookingLeft(boolean lookingLeft) {
        this.lookingLeft = lookingLeft;
    }

    public void setLookingRight(boolean lookingRight) {
        this.lookingRight = lookingRight;
    }

    public Spell getSupportSpell() {
        return supportSpell;
    }

    public void setSupportSpell(Spell supportSpell) {
        this.supportSpell = supportSpell;
    }

    public Spell getDefenceSpell() {
        return defenceSpell;
    }

    public void setDefenceSpell(Spell defenceSpell) {
        this.defenceSpell = defenceSpell;
    }

    public Spell getOffenceSpell() {
        return offenceSpell;
    }

    public void setOffenceSpell(Spell offenceSpell) {
        this.offenceSpell = offenceSpell;
    }

    public InputHandler getInputHandler() {
        return input;
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
