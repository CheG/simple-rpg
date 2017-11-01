package com.romantupikov.game.simplerpg.screens.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.configs.GameConfig;
import com.romantupikov.game.simplerpg.entity.UnitBase;
import com.romantupikov.utils.GdxUtils;
import com.romantupikov.utils.MaterialColor;
import com.romantupikov.utils.debug.DebugCameraController;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class GameRenderer implements Disposable {
    private final GlyphLayout glyphLayout = new GlyphLayout();
    private final GameController controller;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final Camera camera;
    private final Viewport viewport;

    private ShapeRenderer renderer;
//    private DebugCameraController debugCameraController;

    private UnitBase hero;
    private Array<UnitBase> units;

    public GameRenderer(SpriteBatch batch, AssetManager assetManager, GameController gameController, Viewport viewport) {
        this.controller = gameController;
        this.assetManager = assetManager;
        this.batch = batch;
        this.viewport = viewport;
        this.camera = viewport.getCamera();
        init();
    }

    private void init() {
        renderer = new ShapeRenderer();
        hero = controller.getHero();
        units = controller.getUnits();
    }

    public void render(float delta) {
        GdxUtils.clearScreen(MaterialColor.BLUE_GREY);

        renderGameplay();
        renderUI();
        renderDebug();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    private void renderGameplay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        draw();

        batch.end();
    }

    private void draw() {
        hero.render(batch);
        for (int i = 0; i < units.size; i++) {
            units.get(i).render(batch);
        }
    }

    private void renderUI() {

    }

    private void renderDebug() {
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();
    }

    private void drawDebug() {
        hero.drawDebug(renderer);
        for (int i = 0; i < units.size; i++) {
            units.get(i).drawDebug(renderer);
        }
    }
}
