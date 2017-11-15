package com.romantupikov.game.simplerpg.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
import com.romantupikov.game.simplerpg.entity.Unit;

import javax.xml.soap.Text;

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
    private TextButton btnSupportSkill;
    private TextButton btnDefenceSkill;
    private TextButton btnOffenceSkill;

    private Unit selectedHero;
    private Unit selectedEnemy;
    private Unit aiSelectedHero;
    private Unit aiSelectedEnemy;
    private Array<Unit> enemyParty;
    private Array<Unit> playerParty;

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
        selectedHero = controller.getSelectedUnit();
        enemyParty = controller.getEnemyParty();
        playerParty = controller.getPlayerParty();

        stage = new Stage(hudViewport, batch);
        game.addFirstInputProcessor(stage);

        Skin uiSkin = assetManager.get(AssetsDescriptors.UI_SKIN);

        Table rootTable = new Table();
        Table topTable = new Table(uiSkin);
        Table botTable = new Table(uiSkin);

        rootTable.setFillParent(true);

//        topTable.setBackground(RegionsNames.PANEL_INSET_BROWN);
//        botTable.setBackground(RegionsNames.PANEL_INSET_BROWN);

//        rootTable.setDebug(true);
//        topTable.setDebug(true);
//        botTable.setDebug(true);

//        heroHealthBar = new ProgressBar(0f, 100f, 1f, false, uiSkin);
//        heroHealthBar.setValue(selectedUnit.getHp() * (100f / selectedUnit.getMaxHp()));
//        heroHealthBar.setAnimateDuration(0.5f);
//        heroHealthBar.setAnimateInterpolation(Interpolation.elasticOut);
//
//        enemyHealthBar = new ProgressBar(0f, 100f, 1f, false, uiSkin);
//        enemyHealthBar.setValue(100f);
//        enemyHealthBar.setAnimateDuration(0.5f);
//        enemyHealthBar.setAnimateInterpolation(Interpolation.elasticOut);
//        enemyHealthBar.setVisible(false);

//        ui.font = assetManager.get(AssetsDescriptors.FONT_32);
//        glyphLayout.setText(ui.font, "HP");

        // TODO: 15-Nov-17 сделать класс кнопок. Или переделать под ImageButton, а картинку брать из Skill
        btnSupportSkill = new TextButton("Sup Skill", uiSkin);
        btnSupportSkill.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedHero.getSupportSkill().execute();
            }
        });
        btnDefenceSkill = new TextButton("Def Skill", uiSkin);
        btnDefenceSkill.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedHero.getDefenceSkill().execute();
            }
        });
        btnOffenceSkill = new TextButton("Off Skill", uiSkin);
        btnOffenceSkill.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectedHero.getOffenceSkill().execute();
            }
        });

        // top table
        topTable.defaults().fillX().expandX();

//        topTable.add(heroHealthBar)
//                .padRight(Value.percentWidth(0.5f))
//                .padLeft(Value.percentWidth(0.5f))
//                .minHeight(Value.percentHeight(0.1f))
//                .align(Align.left);
//
//        topTable.add(enemyHealthBar)
//                .padRight(Value.percentWidth(0.5f))
//                .padLeft(Value.percentWidth(0.5f))
//                .minHeight(Value.percentHeight(0.1f))
//                .align(Align.right);

        // bottom table
        botTable.defaults().expandX();

        botTable.add(btnSupportSkill)
                .pad(Value.percentHeight(0.1f))
                .align(Align.left);
        botTable.add(btnDefenceSkill)
                .pad(Value.percentHeight(0.1f))
                .align(Align.left);
        botTable.add(btnOffenceSkill)
                .pad(Value.percentHeight(0.1f))
                .align(Align.left);

        rootTable.add(topTable).top().expandX().fillX();
        rootTable.row();
        rootTable.add(botTable).bottom().expand().fillX();
        rootTable.pack();
        stage.addActor(rootTable);
    }

    public void update(float delta) {

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
    public void getControllerUpdate() {
        selectedHero = controller.getSelectedUnit();
        selectedEnemy = controller.getSelectedEnemy();
        aiSelectedHero = controller.getAiSelectedUnit();
        aiSelectedEnemy = controller.getAiSelectedEnemy();
        enemyParty = controller.getEnemyParty();
        playerParty = controller.getPlayerParty();

        // TODO: 15-Nov-17 придумать идею получше. Что если нужно будет добавить больше скиллов

        if (selectedHero.getSupportSkill() == null) {
            btnSupportSkill.setVisible(false);
        } else {
            btnSupportSkill.setText(selectedHero.getSupportSkill().getName());
            btnSupportSkill.setVisible(true);
        }

        if (selectedHero.getOffenceSkill() == null) {
            btnOffenceSkill.setVisible(false);
        } else {
            btnOffenceSkill.setText(selectedHero.getOffenceSkill().getName());
            btnOffenceSkill.setVisible(true);
        }

        if (selectedHero.getDefenceSkill() == null) {
            btnDefenceSkill.setVisible(false);
        } else {
            btnDefenceSkill.setText(selectedHero.getDefenceSkill().getName());
            btnDefenceSkill.setVisible(true);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
