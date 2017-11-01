package com.romantupikov.game.simplerpg.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
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
    private Button btnNextTurn;

    private ShapeRenderer renderer;

    private UnitBase selectedHero;
    private UnitBase selectedEnemy;
    private Array<UnitBase> enemyParty;
    private Array<UnitBase> playerParty;

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

        selectedHero = controller.getSelectedHero();
        enemyParty = controller.getEnemyParty();
        playerParty = controller.getPlayerParty();

        stage = new Stage(hudViewport, batch);

        // TODO: 01-Nov-17 это не должно быть в рендер классе
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(controller);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        Skin uiSkin = assetManager.get(AssetsDescriptors.UI_SKIN);

        heroHealthBar = new ProgressBar(0f, 100f, 1f, false, uiSkin);
        heroHealthBar.setValue(selectedHero.getHp() * (100f/ selectedHero.getMaxHp()));
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

        btnNextTurn = new TextButton("TURN", uiSkin);
        btnNextTurn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.endPlayerTurn(true);
            }
        });

        stage.addActor(heroHealthBar);
        stage.addActor(selUnitHealthBar);
        stage.addActor(btnNextTurn);
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
        for (int i = 0; i < enemyParty.size; i++) {
            enemyParty.get(i).render(batch);
        }
        for (int i = 0; i < playerParty.size; i++) {
            playerParty.get(i).render(batch);
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
        for (int i = 0; i < enemyParty.size; i++) {
            UnitBase enemy = enemyParty.get(i);
            if (selectedEnemy != null)
                if (selectedEnemy == enemy)
                    enemy.setDebugColor(MaterialColor.AMBER);
                else
                    enemy.setDebugColor(MaterialColor.RED);
            enemy.drawDebug(renderer);
        }
        for (int i = 0; i < playerParty.size; i++) {
            UnitBase hero = playerParty.get(i);
            if (selectedHero != null)
                if (selectedHero == hero)
                    hero.setDebugColor(MaterialColor.LIGHT_GREEN);
                else
                    hero.setDebugColor(MaterialColor.PINK);
            hero.drawDebug(renderer);
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void update() {
        selectedHero = controller.getSelectedHero();
        heroHealthBar.setValue(selectedHero.getHp() * (100f/ selectedHero.getMaxHp()));

        selectedEnemy = controller.getSelectedEnemy();
        if (selectedEnemy != null) {
            selUnitHealthBar.setVisible(true);
            selUnitHealthBar.setValue(selectedEnemy.getHp() * (100f / selectedEnemy.getMaxHp()));
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
        stage.dispose();
    }
}
