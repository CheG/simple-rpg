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
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.common.GameManager;
import com.romantupikov.game.simplerpg.configs.GameConfig;
import com.romantupikov.game.simplerpg.entity.UnitBase;
import com.romantupikov.utils.GdxUtils;
import com.romantupikov.utils.MaterialColor;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class GameRenderer implements Disposable, Observer {
    private final GlyphLayout glyphLayout = new GlyphLayout();

    private final SimpleRpgGame game;
    private final GameController controller;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final Camera camera;
    private final Viewport viewport;

    private ShapeRenderer renderer;

    private UnitBase selectedHero;
    private UnitBase selectedEnemy;
    private Array<UnitBase> enemyParty;
    private Array<UnitBase> playerParty;

    public GameRenderer(SimpleRpgGame game, GameController gameController, Viewport viewport) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
        this.controller = gameController;
        this.viewport = viewport;
        this.camera = viewport.getCamera();

        this.controller.registerObserver(this);

        init();
    }

    private void init() {
        selectedEnemy = controller.getSelectedEnemy();
        selectedHero = controller.getSelectedHero();
        enemyParty = controller.getEnemyParty();
        playerParty = controller.getPlayerParty();

        renderer = new ShapeRenderer();
    }

    public void render(float delta) {
        GdxUtils.clearScreen(MaterialColor.BLUE_GREY);

        renderGameplay();
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
    }

    @Override
    public void update() {
        selectedHero = controller.getSelectedHero();
        selectedEnemy = controller.getSelectedEnemy();
        enemyParty = controller.getEnemyParty();
        playerParty = controller.getPlayerParty();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
