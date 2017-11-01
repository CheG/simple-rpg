package com.romantupikov.game.simplerpg.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;

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

    public UnitBase createHero(String textureRegionName, boolean flipX, String name, int level,
                               int strength, int dexterity, int endurance, int spellpower,
                               int defence) {

        TextureRegion region = gameplay.findRegion(textureRegionName);
        UnitBase hero = new UnitBase(region, name, level);
        hero.strength = strength;
        hero.dexterity = dexterity;
        hero.endurance = endurance;
        hero.spellpower = spellpower;
        hero.defence = defence;
        hero.maxHp = BASE_MAX_HP + endurance + level + 50;
        hero.hp = hero.maxHp;
        hero.getRegion().flip(flipX, false);

        return hero;
    }

    public UnitBase createGoblin(String textureRegionName, boolean flipX, String name, int level) {
        TextureRegion region = gameplay.findRegion(textureRegionName);
        UnitBase goblin = new UnitBase(region, name, level);
        goblin.strength = 1f + level;
        goblin.dexterity = 3f + level;
        goblin.endurance = 1f + level;
        goblin.spellpower = 0f + level;
        goblin.defence = 1f + level;
        goblin.maxHp = BASE_MAX_HP + goblin.endurance + goblin.level;
        goblin.hp = goblin.maxHp;
        goblin.getRegion().flip(flipX, false);

        return goblin;
    }
}
