package com.romantupikov.game.simplerpg.factory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.assets.RegionsNames;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.component.Attributes;
import com.romantupikov.game.simplerpg.screen.game.GameController;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class EntityFactory {
    private final GameController controller;
    private final AssetManager assetManager;
    private final SkillFactory skillFactory;
    private InputHandler playerInput;
    private InputHandler aiInput;

    private TextureAtlas gameplay;

    public EntityFactory(GameController controller, AssetManager assetManager, SkillFactory skillFactory,
                         InputHandler playerInput, InputHandler aiInput) {
        this.controller = controller;
        this.assetManager = assetManager;
        this.skillFactory = skillFactory;

        this.aiInput = aiInput;
        this.playerInput = playerInput;
        gameplay = assetManager.get(AssetsDescriptors.GAMEPLAY);
    }

    public Unit createDummyUnit(String regionName, String name, Unit.HeroClass heroClass, InputHandler input) {
        TextureRegion region = gameplay.findRegion(regionName);
        TextureRegion barRegion = gameplay.findRegion(RegionsNames.BAR);
        Attributes attributes = new Attributes(name);
        Unit unit = new Unit(controller, input, region, barRegion, attributes, heroClass);


        return unit;
    }

    public Unit createDummyAIUnit(String regionName, String name, Unit.HeroClass heroClass) {
        TextureRegion region = gameplay.findRegion(regionName);
        TextureRegion barRegion = gameplay.findRegion(RegionsNames.BAR);
        Attributes attributes = new Attributes(name);
        Unit unit = new Unit(controller, aiInput, region, barRegion, attributes, heroClass);


        return unit;
    }
}
