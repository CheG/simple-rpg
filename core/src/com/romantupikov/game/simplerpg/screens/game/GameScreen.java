package com.romantupikov.game.simplerpg.screens.game;

import com.badlogic.gdx.ScreenAdapter;
import com.romantupikov.game.simplerpg.SimpleRpgGame;
import com.romantupikov.utils.GdxUtils;
import com.romantupikov.utils.MaterialColor;

/**
 * Created by hvitserk on 31-Oct-17.
 */

public class GameScreen extends ScreenAdapter {
    private final SimpleRpgGame game;

    public GameScreen(SimpleRpgGame game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen(MaterialColor.BLUE_GREY);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
