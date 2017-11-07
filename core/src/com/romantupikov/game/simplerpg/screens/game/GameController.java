package com.romantupikov.game.simplerpg.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.commands.AttackCommand;
import com.romantupikov.game.simplerpg.entity.commands.Command;
import com.romantupikov.game.simplerpg.entity.commands.HealCommand;
import com.romantupikov.game.simplerpg.factories.CommandFactory;
import com.romantupikov.game.simplerpg.factories.EffectFactory;
import com.romantupikov.game.simplerpg.factories.EntityFactory;

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
    private Array<ParticleEffectPool.PooledEffect> effects = new Array<ParticleEffectPool.PooledEffect>();

    private EntityFactory entityFactory;
    private CommandFactory commandFactory;
    private EffectFactory effectFactory;

    private Unit selectedHero;
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

        entityFactory = new EntityFactory(assetManager);
        effectFactory = new EffectFactory(assetManager);
        commandFactory = new CommandFactory(this);

        // TODO: 06-Nov-17 перекнуть в EntityFactory
        // == UNDER HEAVY CONSTRUCTION ==
        // == player party ==
        Unit dwarf = entityFactory.createUnit(RegionsNames.DWARF_RUNEMASTER, "Cheg");
        dwarf.setPosition(1f, 1f);
        dwarf.setSpeed(2f);
        dwarf.setAttackRange(3f);
        dwarf.setIntelligence(5f);
        dwarf.setUnitClass(Unit.Class.HEALER);
        playerParty.add(dwarf);
        dwarf = entityFactory.createUnit(RegionsNames.DWARF_MACE, "Hvitserk");
        dwarf.setPosition(1f, 3f);
        dwarf.setSpeed(1.7f);
        dwarf.setThreat(20f);
        dwarf.setUnitClass(Unit.Class.WARRIOR);
        selectedHero = dwarf;
        playerParty.add(dwarf);
        dwarf = entityFactory.createUnit(RegionsNames.DWARF_HUNTER, "Urist Izegamal");
        dwarf.setPosition(1f, 6f);
        dwarf.setSpeed(2.2f);
        dwarf.setAttackRange(9f);
        dwarf.setUnitClass(Unit.Class.RANGER);
        playerParty.add(dwarf);

        // == enemy party ==
        Unit goblin = entityFactory.createUnit(RegionsNames.GOBLIN_NINJA, "Lol");
        goblin.setPosition(8f, 3f);
        goblin.setUnitClass(Unit.Class.WARRIOR);
        enemyParty.add(goblin);
    }

    public void update(float delta) {
        if (gameOver) {
            return;
        }

        updateUnits(delta);
        updateStatus(delta);
        simpleAI(delta);
        effectsUpdate(delta);
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
                Command command = unit.getCommand();
                if (command instanceof AttackCommand)
                    if (((AttackCommand) command).getTarget() == target)
                        continue;
                unit.setCommand(commandFactory.createAttackCommand(unit, target));
            }
        }
    }

    private void effectsUpdate(float delta) {
        for (int i = 0; i < effects.size; i++) {
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.update(delta);

            if (effect.isComplete()) {
                effects.removeIndex(i);
                effect.free();
                notifyObservers();
            }
        }
    }

    // == getters/setters ==

    public Unit getSelectedHero() {
        return selectedHero;
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

//        ParticleEffectPool.PooledEffect effect = effectFactory.createHealEffect(worldTouch.x, worldTouch.y);
//        effects.add(effect);

        if (button == 2) {
            Unit newEnemy = entityFactory.createUnit(RegionsNames.GOBLIN_ELITE_AXE, "Goblin");
            newEnemy.setPosition(worldTouch);
            newEnemy.setUnitClass(Unit.Class.WARRIOR);
            enemyParty.add(newEnemy);
            notifyObservers();
        }

        for (int i = 0; i < enemyParty.size; i++) {
            Unit enemy = enemyParty.get(i);
            if (enemy.getBounds().contains(worldTouch)) {
                selectedHero.setCommand(commandFactory.createAttackCommand(selectedHero, enemy));
                notifyObservers();
                return false;
            }
        }
        for (int i = 0; i < playerParty.size; i++) {
            Unit hero = playerParty.get(i);
            if (hero.getBounds().contains(worldTouch)) {
                if (selectedHero.getUnitClass() == Unit.Class.HEALER) {
                    Command command = selectedHero.getCommand();
                    if (command instanceof HealCommand)
                        if (((HealCommand) command).getTarget() == hero) {
                            selectedHero = hero;
                            notifyObservers();
                            return false;
                        }
                    selectedHero.setCommand(commandFactory.createHealCommand(selectedHero, hero));
                }
                if (hero != selectedHero) {
                    Gdx.app.debug("", "Selected Hero\n" + hero.toString());
                    selectedHero = hero;
                }
                notifyObservers();
                return false;
            }
        }

        if (selectedHero != null) {
            selectedHero.setCommand(commandFactory.createMoveCommand(selectedHero, worldTouch));
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
            observer.update();
        }
    }

    public Array<ParticleEffectPool.PooledEffect> getEffects() {
        return effects;
    }

    public void addEffect(ParticleEffectPool.PooledEffect effect) {
        effects.add(effect);
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public EffectFactory getEffectFactory() {
        return effectFactory;
    }
}
