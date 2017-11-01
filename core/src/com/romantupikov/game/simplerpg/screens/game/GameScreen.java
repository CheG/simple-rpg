package com.romantupikov.game.simplerpg.screens.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.configs.GameConfig;
import com.romantupikov.utils.GdxUtils;
import com.romantupikov.utils.MaterialColor;

/**
 * Created by hvitserk on 31-Oct-17.
 */

public class GameScreen extends ScreenAdapter {
    private final SimpleRpgGame game;
    private final AssetManager assetManager;

    private GameController gameController;
    private GameRenderer gameRenderer;

    private Viewport viewport;
    private Camera camera;

    public GameScreen(SimpleRpgGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        gameController = new GameController(game, viewport);
        gameRenderer = new GameRenderer(game.getBatch(), assetManager, gameController, viewport);

    }

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameRenderer.render(delta);

        if(gameController.isGameOver()) {
//            game.setScreen(new MenuScreen(game));

        }
    }

    @Override
    public void resize(int width, int height) {
        gameRenderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
    }
}
