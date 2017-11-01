package com.romantupikov.game.simplerpg.screens.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.configs.GameConfig;
import com.romantupikov.game.simplerpg.screens.game.GameScreen;
import com.romantupikov.utils.GdxUtils;
import com.romantupikov.utils.MaterialColor;

/**
 * Created by hvitserk on 31-Oct-17.
 */

public class LoadingScreen extends ScreenAdapter {
    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH;
    private static final float PROGRESS_BAR_HEIGHT = 5f;

    private Camera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private final SimpleRpgGame game;
    private final AssetManager assetManager;

    public LoadingScreen(SimpleRpgGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        renderer.setColor(MaterialColor.LIGHT_BLUE);

        assetManager.load(AssetsDescriptors.GAMEPLAY);
        assetManager.load(AssetsDescriptors.FONT_32);
        assetManager.load(AssetsDescriptors.UI_SKIN);
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();

        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if (changeScreen) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private void update(float delta) {
        progress = assetManager.getProgress();
        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        renderer.rect(0f, 0f,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
    }
}
