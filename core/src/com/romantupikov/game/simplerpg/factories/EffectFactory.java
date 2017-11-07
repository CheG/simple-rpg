package com.romantupikov.game.simplerpg.factories;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class EffectFactory {
    private final AssetManager assetManager;

    private ParticleEffectPool healEffectPool;

    public EffectFactory(AssetManager assetManager) {
        this.assetManager = assetManager;

        ParticleEffect effect = assetManager.get(AssetsDescriptors.HEAL);
        healEffectPool = new ParticleEffectPool(effect, 5, 20);
    }

    public ParticleEffectPool.PooledEffect createHealEffect(float x, float y) {
        ParticleEffectPool.PooledEffect effect = healEffectPool.obtain();
        effect.setPosition(x, y);
        effect.start();
        return effect;
    }
}
