package com.romantupikov.game.simplerpg.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
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

    private UnitBase hero;
    private UnitBase selectedUnit;
    private Array<UnitBase> units = new Array<UnitBase>();

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
        Gdx.input.setInputProcessor(this);

        factory = new EntityFactory(assetManager);

        hero = factory.createHero(RegionsNames.DWARF_BASE, false, "Vasya", 1,
                9, 3, 3, 3, 1);
        hero.setPosition(5f, GameConfig.WORLD_HEIGHT / 2f - 1f);

        UnitBase goblin = factory.createGoblin(RegionsNames.GOBLIN_BASE, true, "Kek", 1);
        goblin.setPosition(GameConfig.WORLD_WIDTH - 6f, GameConfig.WORLD_HEIGHT / 2f - 1f);

        UnitBase goblin1 = factory.createGoblin(RegionsNames.GOBLIN_BASE, false, "Cheburek", 1);
        goblin1.setPosition(GameConfig.WORLD_WIDTH - 5f, GameConfig.WORLD_HEIGHT / 2f - 3f);

        UnitBase goblin2 = factory.createGoblin(RegionsNames.GOBLIN_BASE, false, "Lol", 1);
        goblin2.setPosition(GameConfig.WORLD_WIDTH - 5f, GameConfig.WORLD_HEIGHT / 2f + 1f);

        units.add(goblin);
        units.add(goblin1);
        units.add(goblin2);

        playerTurn = true;
    }

    public void update(float delta) {
        if (isGameOver()) {
            return;
        }

        updateUnits(delta);
    }

    // TODO: 01-Nov-17 Сделать переход хода
    private void updateUnits(float delta) {
        hero.update(delta);

        for (int i = 0; i < units.size; i++) {
            units.get(i).update(delta);
        }

        if (!playerTurn) {
            turnDelayTimer += delta;
            if (turnDelayTimer >= TURN_DELAY) {
                if (!units.get(unitIndex).isDead()) {
                    units.get(unitIndex).meleeAttack(hero);
                    turnDelayTimer = 0;
                }
                notifyObservers();
                unitIndex++;
                if (unitIndex >= units.size) {
                    unitIndex = 0;
                    playerTurn = true;
                }
            }
        }

    }

    public boolean isGameOver() {
//        return hero.getHp() <= 0;
        return false;
    }

    public void meleeAttack() {
        hero.meleeAttack(selectedUnit);
        notifyObservers();
        playerTurn = false;
    }

    public UnitBase getHero() {
        return hero;
    }

    public UnitBase getSelectedUnit() {
        return selectedUnit;
    }

    public Array<UnitBase> getUnits() {
        return units;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        if (playerTurn) {
            for (int i = 0; i < units.size; i++) {
                if (units.get(i).getBounds().contains(worldTouch)) {
                    if (units.get(i) == selectedUnit) {
                        if (!units.get(i).isDead()) {
                            hero.meleeAttack(units.get(i));
                            playerTurn = false;
                        }
                    } else {
                        selectedUnit = units.get(i);
                    }
                    notifyObservers();
                    Gdx.app.debug("", "hero hp: " + hero.getHp());
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
