package com.romantupikov.game.simplerpg.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.ai.EasyAI;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.spell.AggroSpell;
import com.romantupikov.game.simplerpg.entity.spell.MassHealSpell;
import com.romantupikov.game.simplerpg.factory.EffectFactory;
import com.romantupikov.game.simplerpg.factory.EntityFactory;
import com.romantupikov.game.simplerpg.factory.SpellFactory;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;
import com.romantupikov.game.simplerpg.screen.game.input.PlayerInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class GameController implements Observable {
    private final float STATUS_UPDATE = 0.1f;

    private final SimpleRpgGame game;
    private final AssetManager assetManager;
    private final Viewport viewport;

    private List<Observer> observers;

    private EntityFactory entityFactory;

    private SpellFactory spellFactory;
    private EffectFactory effectFactory;

    private InputHandler playerInput;
    private Unit selectedUnit;
    private Unit selectedEnemy;

    private EasyAI aiInput;
    private Unit aiSelectedUnit;
    private Unit aiSelectedEnemy;

    private Array<Unit> enemyParty = new Array<Unit>();
    private Array<Unit> playerParty = new Array<Unit>();
    private Array<Unit> allUnits = new Array<Unit>();
    private Comparator<Unit> positionComparator;

    private boolean gameOver = false;
    private float statusUpdateTimer;

    public GameController(SimpleRpgGame game, Viewport viewport) {
        this.game = game;
        assetManager = game.getAssetManager();
        this.viewport = viewport;
        observers = new ArrayList<Observer>();
        init();
    }

    private void init() {
        aiInput = new EasyAI(this);
        playerInput = new PlayerInput(this, viewport);
        game.addInputProcessor(new GestureDetector((PlayerInput) playerInput));

        effectFactory = new EffectFactory(assetManager);
        spellFactory = new SpellFactory(effectFactory);
        entityFactory = new EntityFactory(this, assetManager, spellFactory, playerInput, aiInput);

        // TODO: 06-Nov-17 перекнуть в EntityFactory
        // == UNDER HEAVY CONSTRUCTION ==
        // == player party ==
        Unit dwarf1 = entityFactory.createDummyUnit(RegionsNames.DWARF_RUNEMASTER, "Dwarf Runemaster", Unit.HeroClass.SUPPORT, playerInput);
        dwarf1.setPosition(1f, 1f);
        dwarf1.getAttributes().setMoveSpeed(1.3f);
        dwarf1.getAttributes().setAttackDelay(1.5f);
        dwarf1.getAttributes().setCastDelay(1.5f);
        dwarf1.getAttributes().setAttackRange(5f);
        dwarf1.getAttributes().setIntelligence(4f);
        dwarf1.getAttributes().setStrength(1f);
        dwarf1.setSupportSpell(new MassHealSpell(dwarf1));
        this.selectedUnit = dwarf1;
        playerParty.add(dwarf1);

        Unit dwarf = entityFactory.createDummyUnit(RegionsNames.DWARF_BASE, "Dwarf Warrior", Unit.HeroClass.WARRIOR, playerInput);
        dwarf.setPosition(6f, 6f);
        dwarf.getAttributes().setMoveSpeed(1.3f);
        dwarf.getAttributes().setAttackDelay(1.5f);
        dwarf.getAttributes().setCastDelay(1.5f);
        dwarf.getAttributes().setAttackRange(1f);
        dwarf.getAttributes().setIntelligence(1f);
        dwarf.getAttributes().setStrength(4f);
        dwarf.setSupportSpell(new AggroSpell(dwarf));
        playerParty.add(dwarf);

        Unit dwarf2 = entityFactory.createDummyUnit(RegionsNames.DWARF_FIRE_ACOLYTE, "Dwarf Fire Acolyte", Unit.HeroClass.MAGE, playerInput);
        dwarf2.setPosition(3f, 3f);
        dwarf2.getAttributes().setMoveSpeed(1f);
        dwarf2.getAttributes().setAttackDelay(1.8f);
        dwarf2.getAttributes().setCastDelay(1.8f);
        dwarf2.getAttributes().setAttackRange(6f);
        dwarf2.getAttributes().setStrength(1f);
        dwarf2.getAttributes().setIntelligence(5f);
        playerParty.add(dwarf2);

        // == enemy party ==
        Unit goblin = entityFactory.createDummyAIUnit(RegionsNames.GOBLIN_NINJA, "Goblin", Unit.HeroClass.WARRIOR);
        goblin.setPosition(8f, 3f);
        goblin.getAttributes().setAttackDelay(3f);
        goblin.getAttributes().setMaxHP(30f);
        this.aiSelectedUnit = goblin;
        enemyParty.add(goblin);

        goblin = entityFactory.createDummyAIUnit(RegionsNames.GOBLIN_BASE, "Goblin Healer", Unit.HeroClass.SUPPORT);
        goblin.setPosition(10f, 1f);
        goblin.getAttributes().setMaxHP(30f);
        goblin.getAttributes().setIntelligence(4f);
        goblin.getAttributes().setAttackRange(5f);
        goblin.getAttributes().setAttackDelay(3f);
        enemyParty.add(goblin);

        // TODO: 15-Nov-17 куда бы это запихнуть
        positionComparator = new Comparator<Unit>() {
            @Override
            public int compare(Unit unit, Unit unit1) {
                if (unit.getPosition().y < unit1.getPosition().y)
                    return 1;
                else if (unit.getPosition().y > unit1.getPosition().y)
                    return -1;
                else
                    return 0;
            }
        };

        allUnits.addAll(enemyParty);
        allUnits.addAll(playerParty);

        allUnits.sort(positionComparator);
    }

    public void update(float delta) {
        if (gameOver) {
            return;
        }

        aiInput.update(delta);
        updateUnits(delta);
        updateStatus(delta);
    }

    private void updateUnits(float delta) {
        selectedUnit.handleInput();
        aiSelectedUnit.handleInput();

        for (int i = 0; i < allUnits.size; i++) {
            Unit unit = allUnits.get(i);
            unit.update(delta);
        }
    }

    private void updateStatus(float delta) {
        statusUpdateTimer += delta;
        if (statusUpdateTimer >= STATUS_UPDATE) {
            statusUpdateTimer = 0f;
            playerParty.sort();
            allUnits.sort(positionComparator);
            notifyObservers();
        }
    }

    // == getters/setters ==

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public Unit getSelectedEnemy() {
        return selectedEnemy;
    }

    public void setSelectedEnemy(Unit selectedEnemy) {
        this.selectedEnemy = selectedEnemy;
    }

    public Array<Unit> getEnemyParty() {
        return enemyParty;
    }

    public Array<Unit> getPlayerParty() {
        return playerParty;
    }

    public Unit getAiSelectedUnit() {
        return aiSelectedUnit;
    }

    public void setAiSelectedUnit(Unit aiSelectedUnit) {
        this.aiSelectedUnit = aiSelectedUnit;
    }

    public Unit getAiSelectedEnemy() {
        return aiSelectedEnemy;
    }

    public void setAiSelectedEnemy(Unit aiSelectedEnemy) {
        this.aiSelectedEnemy = aiSelectedEnemy;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public EffectFactory getEffectFactory() {
        return effectFactory;
    }

    // == override methods ==

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public Array<Unit> getAllUnits() {
        return allUnits;
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.getControllerUpdate();
        }
    }
}
