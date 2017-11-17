package com.romantupikov.game.simplerpg.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.Effect;
import com.romantupikov.utils.GdxUtils;
import com.romantupikov.utils.MaterialColor;

import java.util.Comparator;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class GameRenderer implements Disposable, Observer {
    private final SimpleRpgGame game;
    private final GameController controller;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final Camera camera;
    private final Viewport viewport;

    private ShapeRenderer renderer;

    private Unit selectedHero;
    private Unit selectedEnemy;
    private Unit aiSelectedHero;
    private Unit aiSelectedEnemy;
    private Array<Unit> enemyParty;
    private Array<Unit> playerParty;
    private Array<Unit> allUnits;

    private TextureRegion barRegion;

    public GameRenderer(SimpleRpgGame game, GameController gameController, Viewport viewport) {
        this.game = game;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
        this.controller = gameController;
        this.viewport = viewport;
        this.camera = viewport.getCamera();

        init();
    }

    private void init() {
        controller.registerObserver(this);

        selectedEnemy = controller.getSelectedEnemy();
        selectedHero = controller.getSelectedUnit();
        enemyParty = controller.getEnemyParty();
        playerParty = controller.getPlayerParty();
        allUnits = controller.getAllUnits();

        TextureAtlas gameplayAtlas = assetManager.get(AssetsDescriptors.GAMEPLAY);
        barRegion = gameplayAtlas.findRegion(RegionsNames.BAR);

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
        for (int i = 0; i < allUnits.size; i++) {
            Unit unit = allUnits.get(i);
            drawUnit(unit);
        }
    }

    private void drawUnit(Unit unit) {
        batch.draw(unit.getRegion(),
                unit.getPosition().x, unit.getPosition().y,
                unit.getOrigX(), unit.getOrigY(),
                unit.getWidth(), unit.getHeight(),
                unit.getScaleX(), unit.getScaleY(),
                unit.getRotation());

        if (!unit.getAttributes().isDead()) {
            batch.setColor(MaterialColor.RED_900);
            batch.draw(barRegion,
                    unit.getPosition().x, unit.getPosition().y + unit.getHeight() + 0.1f,
                    0f, 0f,
                    unit.getWidth(), unit.getHeight() / 8f,
                    1f, 1f,
                    0f);
            batch.setColor(MaterialColor.LIGHT_GREEN);
            batch.draw(barRegion,
                    unit.getPosition().x, unit.getPosition().y + unit.getHeight() + 0.1f,
                    0f, 0f,
                    unit.getWidth(), unit.getHeight() / 8f,
                    unit.getAttributes().getHp() * (1f / unit.getAttributes().getMaxHP()), 1,
                    0f);
            batch.setColor(Color.WHITE);
        }

        Array<Effect> effects = unit.getEffects();
        for (int i = 0; i < effects.size; i++) {
            Effect effect = effects.get(i);
            effect.render(batch);
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
        renderer.setColor(Color.LIME);

        drawUnitBounds(selectedHero);

        renderer.setColor(MaterialColor.AMBER);

        for (int i = 0; i < enemyParty.size; i++) {
            Unit enemy = enemyParty.get(i);
            if (enemy == aiSelectedHero) {
                drawUnitBounds(enemy);
            }
            Circle enemyBounds = enemy.getBounds();
            if (enemy.getTarget() != null) {
                Circle targetBounds = enemy.getTarget().getBounds();
                renderer.line(enemyBounds.x, enemyBounds.y, targetBounds.x, targetBounds.y);
            }
        }
    }

    private void drawUnitBounds(Unit unit) {
        renderer.circle(unit.getBounds().x, unit.getBounds().y, unit.getBounds().radius, 20);
        renderer.x(unit.getBounds().x, unit.getBounds().y, 0.1f);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void getControllerUpdate() {
        selectedHero = controller.getSelectedUnit();
        selectedEnemy = controller.getSelectedEnemy();
        aiSelectedHero = controller.getAiSelectedUnit();
        aiSelectedEnemy = controller.getAiSelectedEnemy();
        enemyParty = controller.getEnemyParty();
        playerParty = controller.getPlayerParty();
        allUnits = controller.getAllUnits();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
