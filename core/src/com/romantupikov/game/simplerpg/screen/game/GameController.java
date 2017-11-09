package com.romantupikov.game.simplerpg.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.factory.EffectFactory;
import com.romantupikov.game.simplerpg.factory.EntityFactory;
import com.romantupikov.game.simplerpg.factory.SkillFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class GameController extends InputAdapter implements Observable {
    private final float STATUS_UPDATE = 1f;
    private final float AI_UPDATE = 1f;

    private final SimpleRpgGame game;
    private final AssetManager assetManager;
    private final Viewport viewport;

    private List<Observer> observers;

    private EntityFactory entityFactory;
    private SkillFactory skillFactory;
    private EffectFactory effectFactory;

    private Unit selectedUnit;
    private Unit selectedEnemy;

    private Array<Unit> enemyParty = new Array<Unit>();
    private Array<Unit> playerParty = new Array<Unit>();

    private boolean gameOver = false;
    private float statusUpdateTimer;
    private float aiTimer;

    public GameController(SimpleRpgGame game, Viewport viewport) {
        this.game = game;
        this.viewport = viewport;
        assetManager = game.getAssetManager();
        observers = new ArrayList<Observer>();
        init();
    }

    private void init() {
        game.addInputProcessor(this);

        effectFactory = new EffectFactory(assetManager);
        skillFactory = new SkillFactory(effectFactory);
        entityFactory = new EntityFactory(assetManager, skillFactory);

        // TODO: 06-Nov-17 перекнуть в EntityFactory
        // == UNDER HEAVY CONSTRUCTION ==
        // == player party ==
        Unit dwarf = entityFactory.createDummyUnit(RegionsNames.DWARF_RUNEMASTER);
        dwarf.setPosition(1f, 1f);
        dwarf.getAttributes().setMoveSpeed(2f);
        dwarf.getAttributes().setAttackRange(5f);
        dwarf.getAttributes().setIntelligence(5f);
        dwarf.addSkill(skillFactory.createHealSkill(dwarf));
        playerParty.add(dwarf);

        // == enemy party ==
        Unit goblin = entityFactory.createDummyUnit(RegionsNames.GOBLIN_NINJA);
        goblin.setPosition(8f, 3f);
        enemyParty.add(goblin);
    }

    public void update(float delta) {
        if (gameOver) {
            return;
        }

        updateUnits(delta);
        updateStatus(delta);
        simpleAI(delta);
    }

    private void updateUnits(float delta) {
        for (int i = 0; i < playerParty.size; i++) {
            playerParty.get(i).update(delta);
        }

        for (int i = 0; i < enemyParty.size; i++) {
            enemyParty.get(i).update(delta);
        }
    }

    private void updateStatus(float delta) {
        statusUpdateTimer += delta;
        if (statusUpdateTimer >= STATUS_UPDATE) {
            statusUpdateTimer = 0f;
            playerParty.sort();
        }
    }

    private void simpleAI(float delta) {
        aiTimer += delta;
        if (aiTimer >= AI_UPDATE) {
            aiTimer = 0f;
            for (int i = 0; i < enemyParty.size; i++) {
                Unit unit = enemyParty.get(i);
                Unit target = playerParty.first();
                unit.setTarget(target);
                unit.activateSkill(0);
            }
        }
    }

    // == getters/setters ==

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public Unit getSelectedEnemy() {
        return selectedEnemy;
    }

    public Array<Unit> getEnemyParty() {
        return enemyParty;
    }

    public Array<Unit> getPlayerParty() {
        return playerParty;
    }

    // == override methods ==

    // TODO: 07-Nov-17 этот метод распилить
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if (button == 1) {
            Unit newEnemy = entityFactory.createDummyUnit(RegionsNames.GOBLIN_ELITE_AXE);
            newEnemy.setPosition(worldTouch);
            enemyParty.add(newEnemy);
            notifyObservers();
        }

        for (int i = 0; i < enemyParty.size; i++) {
            Unit enemy = enemyParty.get(i);
            if (enemy.getBounds().contains(worldTouch)) {
                selectedUnit.setTarget(enemy);
                selectedUnit.activateSkill(0);
                notifyObservers();
                return false;
            }
        }
        for (int i = 0; i < playerParty.size; i++) {
            Unit hero = playerParty.get(i);
            if (hero.getBounds().contains(worldTouch)) {
                selectedUnit.setTarget(hero);
                selectedUnit.activateSkill(1);
                if (hero != selectedUnit) {
                    Gdx.app.debug("", "Selected Hero\n" + hero.toString());
                    selectedUnit = hero;
                }
                notifyObservers();
                return false;
            }
        }

        if (selectedUnit != null) {
            // перемещение
        }

        return false;
    }

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
