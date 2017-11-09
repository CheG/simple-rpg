package com.romantupikov.game.simplerpg.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.config.GameConfig;
import com.romantupikov.game.simplerpg.factory.EffectFactory;
import com.romantupikov.game.simplerpg.factory.EntityFactory;
import com.romantupikov.game.simplerpg.factory.SkillFactory;

/**
 * Created by hvitserk on 31-Oct-17.
 */

public class GameScreen extends ScreenAdapter {
    private final SimpleRpgGame game;
    private final AssetManager assetManager;

    private InputHandler inputHandler;

    private GameController gameController;
    private GameRenderer gameRenderer;
    private GameHUD gameHUD;

    private EntityFactory entityFactory;
    private SkillFactory skillFactory;
    private EffectFactory effectFactory;

    private Viewport viewport;
    private Viewport hudViewport;
    private Camera camera;
    private Camera hudCamera;

    // TODO: 02-Nov-17 удалить задержку перехода экрана
    private float DELAY = 2f;
    private float delayTimer;

    public GameScreen(SimpleRpgGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        hudCamera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, hudCamera);

        gameController = new GameController(game, viewport);
        gameRenderer = new GameRenderer(game, gameController, viewport);
//        gameHUD = new GameHUD(game, gameController, hudViewport);
    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRenderer.render(delta);
//        gameHUD.render(delta);

//        if(gameController.isGameOver()) {
//            delayTimer += delta;
//            if (delayTimer >= DELAY) {
//                game.setScreen(new MenuScreen(game));
//            }
//        }
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
//        gameHUD.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
//        gameHUD.dispose();
    }
}
