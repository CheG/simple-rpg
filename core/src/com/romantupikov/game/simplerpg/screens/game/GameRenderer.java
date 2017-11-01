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

public class GameRenderer implements Disposable, Observer {
    private final GlyphLayout glyphLayout = new GlyphLayout();
    private final GameController controller;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final Camera camera;
    private final Viewport viewport;

    private Camera hudCamera;
    private Viewport hudViewport;

    private Stage stage;
    private ProgressBar heroHealthBar;
    private ProgressBar selUnitHealthBar;
    private BitmapFont font;

    private ShapeRenderer renderer;

    private UnitBase hero;
    private UnitBase selUnit;
    private Array<UnitBase> units;

    public GameRenderer(SpriteBatch batch, AssetManager assetManager, GameController gameController, Viewport viewport) {
        this.controller = gameController;
        this.controller.registerObserver(this);
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
        units = controller.getEnemyParty();

        stage = new Stage(hudViewport, batch);
        Skin uiSkin = assetManager.get(AssetsDescriptors.UI_SKIN);

        heroHealthBar = new ProgressBar(0f, 100f, 1f, false, uiSkin);
        heroHealthBar.setValue(hero.getHp() * (100f/hero.getMaxHp()));
        heroHealthBar.setPosition(50, GameConfig.HUD_HEIGHT - 70f);
        heroHealthBar.setSize(300f, 50f);
        heroHealthBar.setAnimateDuration(0.55f);
        heroHealthBar.setAnimateInterpolation(Interpolation.elasticOut);

        selUnitHealthBar = new ProgressBar(0f, 100f, 1f, false, uiSkin);
        selUnitHealthBar.setValue(100f);
        selUnitHealthBar.setPosition(GameConfig.HUD_WIDTH - 350f, GameConfig.HUD_HEIGHT - 70f);
        selUnitHealthBar.setSize(300f, 50f);
        selUnitHealthBar.setAnimateDuration(0.55f);
        selUnitHealthBar.setAnimateInterpolation(Interpolation.elasticOut);
        selUnitHealthBar.setVisible(false);

        font = assetManager.get(AssetsDescriptors.FONT_32);
        glyphLayout.setText(font, "HP");

        stage.addActor(heroHealthBar);
        stage.addActor(selUnitHealthBar);
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
            if (selUnit != null)
                if (selUnit == units.get(i))
                    units.get(i).setDebugColor(MaterialColor.AMBER);
                else
                    units.get(i).setDebugColor(MaterialColor.RED);
            units.get(i).drawDebug(renderer);
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void update() {
        heroHealthBar.setValue(hero.getHp() * (100f/hero.getMaxHp()));

        selUnit = controller.getSelectedUnit();
        if (selUnit != null) {
            selUnitHealthBar.setVisible(true);
            selUnitHealthBar.setValue(selUnit.getHp() * (100f / selUnit.getMaxHp()));
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
        stage.dispose();
    }
}
