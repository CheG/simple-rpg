package com.romantupikov.game.simplerpg.entity.effect;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.romantupikov.game.simplerpg.entity.Unit;

/**
 * Created by hvitserk on 07-Nov-17.
 */

abstract class EffectBase implements Effect {
    protected ParticleEffectPool.PooledEffect visualEffect;
    protected Unit self;

    protected EffectBase(Unit unit) {

        this.self = unit;
    }

    public void setVisualEffect(ParticleEffectPool.PooledEffect visualEffect) {
        this.visualEffect = visualEffect;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (visualEffect != null) {
            visualEffect.draw(batch);
        }
    }
}
