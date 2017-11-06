package com.romantupikov.game.simplerpg.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.assets.RegionsNames;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class EntityFactory {
    private static final float BASE_MAX_HP = 5;

    private final AssetManager assetManager;
    private TextureAtlas gameplay;

    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
        gameplay = assetManager.get(AssetsDescriptors.GAMEPLAY);
    }

    public Unit createUnit(String regionName, String name) {
        TextureRegion region = gameplay.findRegion(regionName);
        TextureRegion barRegion = gameplay.findRegion(RegionsNames.BAR);
        return new Unit(region, barRegion, name, 1f);
    }
}
