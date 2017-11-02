package com.romantupikov.game.simplerpg.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by hvitserk on 02-Nov-17.
 */

public class GameManager {
    private static final GameManager instance = new GameManager();

    public static GameManager getInstance() {
        return instance;
    }

    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();


    public void addInputProcessor(InputProcessor processor) {
        inputMultiplexer.addProcessor(processor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void removeInputProcessor(InputProcessor processor) {
        inputMultiplexer.removeProcessor(processor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private GameManager() {
    }
}
