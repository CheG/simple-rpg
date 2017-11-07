package com.romantupikov.game.simplerpg.entity.effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public interface Effect {
    boolean update(float delta);
    void render(SpriteBatch batch);
}
