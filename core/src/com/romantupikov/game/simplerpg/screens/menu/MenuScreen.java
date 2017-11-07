package com.romantupikov.game.simplerpg.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.screens.game.GameScreen;

/**
 * Created by hvitserk on 02-Nov-17.
 */

public class MenuScreen extends MenuScreenBase {

    public MenuScreen(SimpleRpgGame game) {
        super(game);
    }

    @Override
    protected Actor createUI() {
        Skin uiSkin = assetManager.get(AssetsDescriptors.UI_SKIN);

        Table table = new Table();

        TextButton btnPlay = new TextButton("PLAY", uiSkin);
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        TextButton btnExit = new TextButton("EXIT", uiSkin);
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exit();
            }
        });

        Table tableButtons = new Table(uiSkin);
        tableButtons.defaults().pad(10f);
        tableButtons.setBackground(RegionsNames.PANEL_BROWN);

        tableButtons.add(btnPlay).prefSize(300f, 50f).row();
        tableButtons.add(btnExit).prefSize(300f, 50f).padTop(30f);

        tableButtons.center();

        table.add(tableButtons);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private void play() {
        game.setScreen(new GameScreen(game));
    }

    private void exit() {
        Gdx.app.exit();
    }
}
