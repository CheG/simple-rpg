package com.romantupikov.game.simplerpg.screens.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.entity.UnitBase;

/**
 * Created by hvitserk on 02-Nov-17.
 */

public class GameHUD implements Disposable, Observer {
    private final GlyphLayout glyphLayout = new GlyphLayout();

    private final SimpleRpgGame game;
    private final GameController controller;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private Camera hudCamera;
    private Viewport hudViewport;

    private Stage stage;
    private ProgressBar heroHealthBar;
    private ProgressBar enemyHealthBar;
    private BitmapFont font;
    private Button btnNextTurn;

    private UnitBase selectedHero;
    private UnitBase selectedEnemy;
    private Array<UnitBase> enemyParty;
    private Array<UnitBase> playerParty;

    public GameHUD(SimpleRpgGame game, GameController controller, Viewport hudViewport) {
        this.game = game;
        this.controller = controller;
        this.assetManager = game.getAssetManager();
        this.batch = game.getBatch();
        this.hudViewport = hudViewport;
        this.hudCamera = hudViewport.getCamera();

        init();
    }

    private void init() {
        controller.registerObserver(this);

        selectedEnemy = controller.getSelectedEnemy();
        selectedHero = controller.getSelectedHero();
        enemyParty = controller.getEnemyParty();
        playerParty = controller.getPlayerParty();

        stage = new Stage(hudViewport, batch);
        game.addInputProcessor(stage);

        Skin uiSkin = assetManager.get(AssetsDescriptors.UI_SKIN);

        Table rootTable = new Table();
        Table topTable = new Table(uiSkin);
        Table botTable = new Table(uiSkin);

        rootTable.setFillParent(true);

        topTable.setBackground(RegionsNames.PANEL_INSET_BROWN);
//        botTable.setBackground(RegionsNames.PANEL_INSET_BROWN);

//        rootTable.setDebug(true);
//        topTable.setDebug(true);
//        botTable.setDebug(true);

        heroHealthBar = new ProgressBar(0f, 100f, 1f, false, uiSkin);
        heroHealthBar.setValue(selectedHero.getHp() * (100f / selectedHero.getMaxHp()));
        heroHealthBar.setAnimateDuration(0.5f);
        heroHealthBar.setAnimateInterpolation(Interpolation.elasticOut);

        enemyHealthBar = new ProgressBar(0f, 100f, 1f, false, uiSkin);
        enemyHealthBar.setValue(100f);
        enemyHealthBar.setAnimateDuration(0.5f);
        enemyHealthBar.setAnimateInterpolation(Interpolation.elasticOut);
        enemyHealthBar.setVisible(false);

        font = assetManager.get(AssetsDescriptors.FONT_32);
        glyphLayout.setText(font, "HP");

        btnNextTurn = new TextButton("TURN", uiSkin);
        btnNextTurn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.endPlayerTurn(true);
            }
        });

        // top table
        topTable.defaults().fillX().expandX();

        topTable.add(heroHealthBar)
                .padRight(Value.percentWidth(0.5f))
                .padLeft(Value.percentWidth(0.5f))
                .minHeight(Value.percentHeight(0.1f))
                .align(Align.left);

        topTable.add(enemyHealthBar)
                .padRight(Value.percentWidth(0.5f))
                .padLeft(Value.percentWidth(0.5f))
                .minHeight(Value.percentHeight(0.1f))
                .align(Align.right);

        // bottom table
        botTable.defaults().expandX();

        botTable.add(btnNextTurn)
                .pad(Value.percentHeight(0.1f))
                .align(Align.right);

        rootTable.add(topTable).top().expandX().fillX();
        rootTable.row();
        rootTable.add(botTable).bottom().expand().fillX();
        rootTable.pack();
        stage.addActor(rootTable);
    }

    public void render(float delta) {
        renderUI(delta);
    }

    private void renderUI(float delta) {
        hudViewport.apply();
        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        hudViewport.update(width, height, true);
    }

    @Override
    public void update() {
        selectedHero = controller.getSelectedHero();
        selectedEnemy = controller.getSelectedEnemy();
        enemyParty = controller.getEnemyParty();
        playerParty = controller.getPlayerParty();

        heroHealthBar.setValue(selectedHero.getHp() * (100f / selectedHero.getMaxHp()));
        if (selectedEnemy != null) {
            enemyHealthBar.setVisible(true);
            enemyHealthBar.setValue(selectedEnemy.getHp() * (100f / selectedEnemy.getMaxHp()));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
