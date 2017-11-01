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

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class GameController extends InputAdapter {
    private final SimpleRpgGame game;
    private final AssetManager assetManager;
    private final Viewport viewport;

    private EntityFactory factory;

    private UnitBase hero;
    private Array<UnitBase> units = new Array<UnitBase>();

    private boolean playerTurn;

    public GameController(SimpleRpgGame game, Viewport viewport) {
        this.game = game;
        this.viewport = viewport;
        assetManager = game.getAssetManager();
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);

        factory = new EntityFactory(assetManager);

        hero = factory.createHero(RegionsNames.DWARF_BASE, false, "Vasya", 1,
                3, 3, 3, 3, 1);
        hero.setPosition(6f, GameConfig.WORLD_HEIGHT / 2f);

        UnitBase goblin = factory.createGoblin(RegionsNames.GOBLIN_BASE, true, "Kek", 1);
        goblin.setPosition(GameConfig.WORLD_WIDTH - 6f, GameConfig.WORLD_HEIGHT / 2f);
        units.add(goblin);

        playerTurn = true;
    }

    public void update(float delta) {
        if (isGameOver()) {
            return;
        }

        updateUnits(delta);
    }

    private void updateUnits(float delta) {
        hero.update(delta);

        for (int i = 0; i < units.size; i++) {
            if (!playerTurn) {
                units.get(i).meleeAttack(hero);
            }
            units.get(i).update(delta);
        }
        playerTurn = true;
    }

    public boolean isGameOver() {
//        return hero.getHp() <= 0;
        return false;
    }

    public UnitBase getHero() {
        return hero;
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
                    hero.meleeAttack(units.get(i));
                    playerTurn = false;
                    break;
                }
            }
        }
        return false;
    }
}
