package com.romantupikov.game.simplerpg.entity.commands;

import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.romantupikov.game.simplerpg.factories.EffectFactory;
import com.romantupikov.game.simplerpg.factories.EntityFactory;
import com.romantupikov.game.simplerpg.screens.game.GameController;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public abstract class CommandBase implements Command {
    protected final GameController controller;
    protected final EffectFactory effectFactory;
    protected final EntityFactory entityFactory;
    protected ParticleEffectPool.PooledEffect effect;

    public CommandBase(GameController controller) {
        this.controller = controller;
        effectFactory = controller.getEffectFactory();
        entityFactory = controller.getEntityFactory();
    }
}
