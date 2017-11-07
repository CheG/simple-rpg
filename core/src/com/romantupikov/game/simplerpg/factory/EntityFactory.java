package com.romantupikov.game.simplerpg.factory;

import com.badlogic.gdx.assets.AssetManager;
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
    private final SkillFactory skillFactory;

    private TextureAtlas gameplay;

    public EntityFactory(AssetManager assetManager, SkillFactory skillFactory) {
        this.assetManager = assetManager;
        this.skillFactory = skillFactory;

        gameplay = assetManager.get(AssetsDescriptors.GAMEPLAY);
    }

    public Unit createUnit(String regionName, String name) {
        TextureRegion region = gameplay.findRegion(regionName);
        TextureRegion barRegion = gameplay.findRegion(RegionsNames.BAR);
        Unit unit = new Unit(region, barRegion, name, 1f);
        unit.addSkill(skillFactory.createMeleeSkill(unit));

        return unit;
    }
}
