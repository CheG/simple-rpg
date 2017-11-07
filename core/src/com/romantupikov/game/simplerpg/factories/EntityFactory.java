package com.romantupikov.game.simplerpg.factories;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.entity.Unit;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class EntityFactory {
    private final AssetManager assetManager;

    private TextureAtlas gameplay;
    private ParticleEffectPool healEffectPool;

    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;

        gameplay = assetManager.get(AssetsDescriptors.GAMEPLAY);

        ParticleEffect effect = assetManager.get(AssetsDescriptors.HEAL);
        healEffectPool = new ParticleEffectPool(effect, 5, 20);
    }

    public Unit createUnit(String regionName, String name) {
        TextureRegion region = gameplay.findRegion(regionName);
        TextureRegion barRegion = gameplay.findRegion(RegionsNames.BAR);
        return new Unit(region, barRegion, name, 1f);
    }

    public ParticleEffectPool.PooledEffect createHealEffect(float x, float y) {
        ParticleEffectPool.PooledEffect effect = healEffectPool.obtain();
        effect.setPosition(x, y);
        effect.start();
        return effect;
    }
}
