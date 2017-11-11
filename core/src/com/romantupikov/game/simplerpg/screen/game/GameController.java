package com.romantupikov.game.simplerpg.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.ai.EasyAI;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.factory.EffectFactory;
import com.romantupikov.game.simplerpg.factory.EntityFactory;
import com.romantupikov.game.simplerpg.factory.SkillFactory;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;
import com.romantupikov.game.simplerpg.screen.game.input.PlayerInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class GameController implements Observable {
    private final float STATUS_UPDATE = 1f;
    private final float AI_UPDATE = 1f;

    private final SimpleRpgGame game;
    private final AssetManager assetManager;
    private final Viewport viewport;

    private List<Observer> observers;

    private EntityFactory entityFactory;

    private SkillFactory skillFactory;
    private EffectFactory effectFactory;

    private InputHandler playerInput;
    private Unit selectedUnit;
    private Unit selectedEnemy;

    private EasyAI aiInput;
    private Unit aiSelectedUnit;
    private Unit aiSelectedEnemy;

    private Array<Unit> enemyParty = new Array<Unit>();
    private Array<Unit> playerParty = new Array<Unit>();

    private boolean gameOver = false;
    private float statusUpdateTimer;
    private float aiTimer;

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
        skillFactory = new SkillFactory(effectFactory);
        entityFactory = new EntityFactory(this, assetManager, skillFactory);

        // TODO: 06-Nov-17 перекнуть в EntityFactory
        // == UNDER HEAVY CONSTRUCTION ==
        // == player party ==
        Unit dwarf1 = entityFactory.createDummyUnit(RegionsNames.DWARF_RUNEMASTER, "Dwarf1", Unit.HeroClass.SUPPORT);
        dwarf1.setPosition(1f, 1f);
        dwarf1.getAttributes().setMoveSpeed(1f);
        dwarf1.getAttributes().setAttackDelay(3.5f);
        dwarf1.getAttributes().setCastDelay(1.5f);
        dwarf1.getAttributes().setAttackRange(5f);
        dwarf1.getAttributes().setIntelligence(5f);
        dwarf1.addSkill(skillFactory.createHealSkill(dwarf1));
        this.selectedUnit = dwarf1;
        playerParty.add(dwarf1);

        Unit dwarf = entityFactory.createDummyUnit(RegionsNames.DWARF_BASE, "Dwarf2", Unit.HeroClass.WARRIOR);
        dwarf.setPosition(6f, 6f);
        dwarf.getAttributes().setMoveSpeed(2f);
        dwarf.getAttributes().setAttackDelay(1.5f);
        dwarf.getAttributes().setCastDelay(1.5f);
        dwarf.getAttributes().setAttackRange(5f);
        dwarf.getAttributes().setIntelligence(2f);
        dwarf.getAttributes().setStrength(2f);
        dwarf.addSkill(skillFactory.createHealSkill(dwarf));
        playerParty.add(dwarf);

        Unit dwarf2 = entityFactory.createDummyUnit(RegionsNames.DWARF_MACE, "Dwarf3", Unit.HeroClass.WARRIOR);
        dwarf2.setPosition(3f, 3f);
        dwarf2.getAttributes().setMoveSpeed(2f);
        dwarf2.getAttributes().setAttackDelay(2.5f);
        dwarf2.getAttributes().setCastDelay(3.5f);
        dwarf2.getAttributes().setAttackRange(1f);
        dwarf2.getAttributes().setStrength(5f);
        dwarf2.addSkill(skillFactory.createHealSkill(dwarf2));
        playerParty.add(dwarf2);

        // == enemy party ==
        Unit goblin = entityFactory.createDummyUnit(RegionsNames.GOBLIN_NINJA, "Goblin", Unit.HeroClass.WARRIOR);
        goblin.setPosition(8f, 3f);
        goblin.getAttributes().setAttackDelay(3f);
        this.aiSelectedUnit = goblin;
        enemyParty.add(goblin);

        goblin = entityFactory.createDummyUnit(RegionsNames.GOBLIN_BASE, "Goblin1", Unit.HeroClass.SUPPORT);
        goblin.setPosition(8f, 8f);
        goblin.getAttributes().setIntelligence(8f);
        goblin.getAttributes().setAttackRange(5f);
        goblin.getAttributes().setAttackDelay(3f);
        enemyParty.add(goblin);
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
        selectedUnit.handleInput(playerInput);
        aiSelectedUnit.handleInput(aiInput);

        for (int i = 0; i < playerParty.size; i++) {
            Unit unit = playerParty.get(i);
            unit.update(delta);
        }

        for (int i = 0; i < enemyParty.size; i++) {
            Unit enemy = enemyParty.get(i);
            enemy.update(delta);
        }
    }

    private void updateStatus(float delta) {
        statusUpdateTimer += delta;
        if (statusUpdateTimer >= STATUS_UPDATE) {
            statusUpdateTimer = 0f;
            playerParty.sort();
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

    // == override methods ==

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.getControllerUpdate();
        }
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public EffectFactory getEffectFactory() {
        return effectFactory;
    }
}
