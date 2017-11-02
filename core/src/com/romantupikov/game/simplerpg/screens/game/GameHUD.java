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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.common.GameManager;
import com.romantupikov.game.simplerpg.configs.GameConfig;
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
    private ProgressBar selUnitHealthBar;
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

        this.controller.registerObserver(this);

        init();
    }

    private void init() {
        selectedEnemy = controller.getSelectedEnemy();
        selectedHero = controller.getSelectedHero();
        enemyParty = controller.getEnemyParty();
        playerParty = controller.getPlayerParty();

        stage = new Stage(hudViewport, batch);
        Skin uiSkin = assetManager.get(AssetsDescriptors.UI_SKIN);

        GameManager.getInstance().addInputProcessor(stage);

        heroHealthBar = new ProgressBar(0f, 100f, 1f, false, uiSkin);
        heroHealthBar.setValue(selectedHero.getHp() * (100f / selectedHero.getMaxHp()));
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
            selUnitHealthBar.setVisible(true);
            selUnitHealthBar.setValue(selectedEnemy.getHp() * (100f / selectedEnemy.getMaxHp()));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
