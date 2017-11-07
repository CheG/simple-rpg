package com.romantupikov.game.simplerpg.factory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.romantupikov.game.simplerpg.assets.AssetsDescriptors;
import com.romantupikov.game.simplerpg.entity.Unit;
import com.romantupikov.game.simplerpg.entity.effect.HealEffect;
import com.romantupikov.game.simplerpg.entity.effect.MeleeEffect;
import com.romantupikov.game.simplerpg.entity.effect.MoveEffect;

/**
 * Created by hvitserk on 07-Nov-17.
 */

public class EffectFactory {
    private final AssetManager assetManager;

    private ParticleEffectPool healEffectPool;
    private ParticleEffectPool meleeEffectPool;
    private ParticleEffectPool moveEffectPool;

    // TODO: 07-Nov-17 Создать пулл для эффектов

    public EffectFactory(AssetManager assetManager) {
        this.assetManager = assetManager;

        ParticleEffect healParticleEffect = assetManager.get(AssetsDescriptors.HEAL);
        healEffectPool = new ParticleEffectPool(healParticleEffect, 5, 20);
        // TODO: 07-Nov-17 создать эффект удара
        meleeEffectPool = new ParticleEffectPool(healParticleEffect, 5, 20);
        // TODO: 07-Nov-17  создать эффект передвижения
        moveEffectPool = new ParticleEffectPool(healParticleEffect, 5, 20);
    }

    public HealEffect createHealEffect(Unit unit, float value) {
        ParticleEffectPool.PooledEffect effect = healEffectPool.obtain();
        effect.setPosition(unit.getX(), unit.getY());
        effect.start();

        HealEffect healEffect = new HealEffect(unit, value);
        healEffect.setVisualEffect(effect);

        return healEffect;
    }

    public MeleeEffect createMeleeEffect(Unit unit, float value) {
        ParticleEffectPool.PooledEffect effect = meleeEffectPool.obtain();
        effect.setPosition(unit.getX(), unit.getY());
        effect.start();

        MeleeEffect meleeEffect = new MeleeEffect(unit, value);
        meleeEffect.setVisualEffect(effect);

        return meleeEffect;
    }

    public MoveEffect createMoveEffect(Unit unit, Vector2 position) {
        ParticleEffectPool.PooledEffect effect = moveEffectPool.obtain();
        effect.setPosition(unit.getX(), unit.getY());
        effect.start();

        MoveEffect moveEffect = new MoveEffect(unit, position);
        moveEffect.setVisualEffect(effect);

        return moveEffect;
    }
}
