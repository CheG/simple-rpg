package com.romantupikov.game.simplerpg.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.common.GameManager;
import com.romantupikov.game.simplerpg.configs.GameConfig;
import com.romantupikov.game.simplerpg.entity.EntityFactory;
import com.romantupikov.game.simplerpg.entity.UnitBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class GameController extends InputAdapter implements Observable {
    private static final float TURN_DELAY = 0.8f;

    private final SimpleRpgGame game;
    private final AssetManager assetManager;
    private final Viewport viewport;

    private List<Observer> observers;

    private EntityFactory factory;

    private UnitBase selectedHero;
    private UnitBase selectedEnemy;
    private Array<UnitBase> enemyParty = new Array<UnitBase>();
    private Array<UnitBase> playerParty = new Array<UnitBase>();

    private boolean playerTurn;
    private float turnDelayTimer;
    private int unitIndex;

    public GameController(SimpleRpgGame game, Viewport viewport) {
        this.game = game;
        this.viewport = viewport;
        assetManager = game.getAssetManager();
        observers = new ArrayList<Observer>();
        init();
    }

    private void init() {
        GameManager.getInstance().addInputProcessor(this);

        factory = new EntityFactory(assetManager);

        // == init player party ==
        UnitBase dwarf = factory.createHero(RegionsNames.DWARF_HUNTER, false, "Archer", 1f,
                3f, 8f, 1f, 1f, 1f);
        dwarf.setPosition(4f, GameConfig.WORLD_HEIGHT / 2f - 3f);
        playerParty.add(dwarf);
        selectedHero = dwarf;

        dwarf = factory.createHero(RegionsNames.DWARF_MACE, false, "Vasya", 1f,
                6f, 4f, 4f, 0f, 4f);
        dwarf.setPosition(5f, GameConfig.WORLD_HEIGHT / 2f - 1f);
        playerParty.add(dwarf);

        dwarf = factory.createHero(RegionsNames.DWARF_RUNEMASTER, false, "Hvitserk", 1f,
                2f, 3f, 1f, 9f, 1f);
        dwarf.setPosition(4f, GameConfig.WORLD_HEIGHT / 2f + 1f);
        playerParty.add(dwarf);


        // == init enemy party ==
        UnitBase goblin = factory.createGoblin(RegionsNames.GOBLIN_ELITE_AXE, true, "Kek", 1);
        goblin.setPosition(GameConfig.WORLD_WIDTH - 6f, GameConfig.WORLD_HEIGHT / 2f - 1f);
        enemyParty.add(goblin);

        goblin = factory.createGoblin(RegionsNames.GOBLIN_ARCHER, true, "Cheburek", 1);
        goblin.setPosition(GameConfig.WORLD_WIDTH - 5f, GameConfig.WORLD_HEIGHT / 2f - 3f);
        enemyParty.add(goblin);

        goblin = factory.createGoblin(RegionsNames.GOBLIN_NINJA, true, "Lol", 1);
        goblin.setPosition(GameConfig.WORLD_WIDTH - 5f, GameConfig.WORLD_HEIGHT / 2f + 1f);
        enemyParty.add(goblin);


        playerTurn = true;
    }

    public void update(float delta) {
        if (isGameOver()) {
            return;
        }

        updateUnits(delta);
    }

    private void updateUnits(float delta) {
        for (int i = 0; i < playerParty.size; i++) {
            playerParty.get(i).update(delta);
        }

        for (int i = 0; i < enemyParty.size; i++) {
            enemyParty.get(i).update(delta);
        }

        if (!playerTurn) {
            turnDelayTimer += delta;
            if (turnDelayTimer >= TURN_DELAY) {
                if (!enemyParty.get(unitIndex).isDead()) {
                    // TODO: 01-Nov-17 добавить выбор по уровню угрозы
                    enemyParty.get(unitIndex).meleeAttack(selectedHero);
                    turnDelayTimer = 0;
                }
                notifyObservers();
                unitIndex++;
                if (unitIndex >= enemyParty.size) {
                    unitIndex = 0;
                    playerTurn = true;
                }
            }
        }

    }

    public boolean isGameOver() {
//        return selectedHero.getHp() <= 0;
        return false;
    }

    public UnitBase getSelectedHero() {
        return selectedHero;
    }

    public UnitBase getSelectedEnemy() {
        return selectedEnemy;
    }

    public Array<UnitBase> getEnemyParty() {
        return enemyParty;
    }

    public Array<UnitBase> getPlayerParty() {
        return playerParty;
    }

    // TODO: 01-Nov-17 придумать получше название метода))
    public void endPlayerTurn(boolean end) {
        this.playerTurn = !end;
        for (int i = 0; i < enemyParty.size; i++) {
            enemyParty.get(i).setMoved(false);
        }
        for (int i = 0; i < playerParty.size; i++) {
            playerParty.get(i).setMoved(false);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        if (playerTurn) {
            for (int i = 0; i < enemyParty.size; i++) {
                UnitBase enemy = enemyParty.get(i);
                if (enemy.getBounds().contains(worldTouch)) {
                    if (enemy == selectedEnemy && !selectedHero.isMoved()) {
                        if (!enemy.isDead()) {
                            selectedHero.meleeAttack(enemy);
                            Gdx.app.debug(":::", "Player Attack Phase\nPlayer\n\t" + selectedHero.toString());
                            Gdx.app.debug("", "Target\n\t" + enemy.toString() + "\n========");
                        }
                    } else {
                        Gdx.app.debug("", "Target\n" + enemy.toString());
                        selectedEnemy = enemy;
                    }
                    notifyObservers();
                    break;
                }
            }
            for (int i = 0; i < playerParty.size; i++) {
                UnitBase hero = playerParty.get(i);
                if (hero.getBounds().contains(worldTouch)) {
                    if (hero != selectedHero) {
                        Gdx.app.debug("", "Selected Hero\n" + hero.toString());
                        selectedHero = hero;
                    }
                    notifyObservers();
                    break;
                }
            }
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
}
