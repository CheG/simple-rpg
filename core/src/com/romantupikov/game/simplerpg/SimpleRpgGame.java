package com.romantupikov.game.simplerpg;

import com.romantupikov.game.simplerpg.screen.loading.LoadingScreen;
import com.romantupikov.utils.game.GameBase;

public class SimpleRpgGame extends GameBase {

    @Override
    protected void postCreate() {
        setScreen(new LoadingScreen(this));
    }
}
