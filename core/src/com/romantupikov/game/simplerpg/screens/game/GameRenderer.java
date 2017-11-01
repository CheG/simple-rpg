package com.romantupikov.game.simplerpg.screens.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.configs.GameConfig;
import com.romantupikov.game.simplerpg.entity.UnitBase;
import com.romantupikov.utils.GdxUtils;
import com.romantupikov.utils.MaterialColor;

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

    private Camera hudCamera;
    private Viewport hudViewport;

    private Stage stage;
    private ProgressBar healthBar;
    private BitmapFont font;

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
        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        hero = controller.getHero();
        units = controller.getUnits();

        stage = new Stage(hudViewport, batch);
        Skin uiSkin = assetManager.get(AssetsDescriptors.UI_SKIN);

        healthBar = new ProgressBar(0f, hero.getHp(), 1f, false, uiSkin);
        healthBar.setPosition(50, GameConfig.HUD_HEIGHT - 70f);
        healthBar.setSize(200f, 30f);
        healthBar.setAnimateDuration(0.55f);
        healthBar.setAnimateInterpolation(Interpolation.elasticOut);

        font = assetManager.get(AssetsDescriptors.FONT_32);
        glyphLayout.setText(font, "HP");

        stage.addActor(healthBar);
    }

    public void render(float delta) {
        GdxUtils.clearScreen(MaterialColor.BLUE_GREY);

        renderGameplay();
        renderUI(delta);
        renderDebug();
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

    private void renderUI(float delta) {
        hudViewport.apply();
        healthBar.setValue(hero.getHp());
        stage.act();
        stage.draw();
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

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        stage.dispose();
    }
}
