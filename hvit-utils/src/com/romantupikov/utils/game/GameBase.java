package com.romantupikov.utils.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;

public abstract class GameBase extends Game {
    private AssetManager assetManager;
    private SpriteBatch batch;
    private InputMultiplexer inputMultiplexer;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();

        inputMultiplexer = new InputMultiplexer();

        postCreate();
    }

    protected abstract void postCreate();

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
        batch.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void addInputProcessor(InputProcessor inputProcessor) {
        inputMultiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void removeInputProcessor(InputProcessor inputProcessor) {
        inputMultiplexer.removeProcessor(inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
}
