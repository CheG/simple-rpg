package com.romantupikov.game.simplerpg.factory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.entity.Entity;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.component.Attributes;
import com.romantupikov.game.simplerpg.entity.component.AttributesComponent;
import com.romantupikov.game.simplerpg.entity.component.ClassComponent;
import com.romantupikov.game.simplerpg.entity.component.Component;
import com.romantupikov.game.simplerpg.entity.component.EffectsComponent;
import com.romantupikov.game.simplerpg.entity.component.HeroClass;
import com.romantupikov.game.simplerpg.entity.component.SpellsComponent;
import com.romantupikov.game.simplerpg.entity.component.StatesComponent;
import com.romantupikov.game.simplerpg.entity.component.TextureComponent;
import com.romantupikov.game.simplerpg.screen.game.GameController;
import com.romantupikov.game.simplerpg.screen.game.input.InputHandler;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public class EntityFactory {
    private final GameController controller;
    private final AssetManager assetManager;
    private final SpellFactory spellFactory;
    private InputHandler playerInput;
    private InputHandler aiInput;

    private TextureAtlas gameplayAtlas;

    public EntityFactory(GameController controller, AssetManager assetManager, SpellFactory spellFactory,
                         InputHandler playerInput, InputHandler aiInput) {
        this.controller = controller;
        this.assetManager = assetManager;
        this.spellFactory = spellFactory;

        this.aiInput = aiInput;
        this.playerInput = playerInput;
        gameplayAtlas = assetManager.get(AssetsDescriptors.GAMEPLAY);
    }

    public Unit createDummyUnit(String regionName, String name, Unit.HeroClass heroClass, InputHandler input) {
        TextureRegion region = gameplayAtlas.findRegion(regionName);
        Attributes attributes = new Attributes(name);
        Unit unit = new Unit(controller, input, region, attributes, heroClass);


        return unit;
    }

    public Unit createDummyAIUnit(String regionName, String name, Unit.HeroClass heroClass) {
        TextureRegion region = gameplayAtlas.findRegion(regionName);
        Attributes attributes = new Attributes(name);
        Unit unit = new Unit(controller, aiInput, region, attributes, heroClass);


        return unit;
    }

    public Entity createCharacter(String regionName, String name, HeroClass heroClass) {
        AttributesComponent attributesComp = new AttributesComponent(name);
        ClassComponent heroClassComp = new ClassComponent();
        heroClassComp.heroClass = heroClass;
        EffectsComponent effectsComp = new EffectsComponent();
        SpellsComponent spellsComp = new SpellsComponent();
        StatesComponent statesComp = new StatesComponent();
        TextureComponent textureComp = new TextureComponent();
        textureComp.region = gameplayAtlas.findRegion(regionName);

        Entity entity = new Entity(controller);
        entity.addComponent(Component.ATTRIBUTES, attributesComp);
        entity.addComponent(Component.HERO_CLASS, heroClassComp);
        entity.addComponent(Component.EFFECTS, effectsComp);
        entity.addComponent(Component.SPELLS, spellsComp);
        entity.addComponent(Component.STATES, statesComp);
        entity.addComponent(Component.TEXTURE, textureComp);

        return entity;
    }
}
