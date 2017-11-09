package com.romantupikov.game.simplerpg.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by hvitserk on 31-Oct-17.
 */

public class AssetsDescriptors {
    private AssetsDescriptors() {
    }

    // == atlases ==
    public static final AssetDescriptor<TextureAtlas> GAMEPLAY =
            new AssetDescriptor<TextureAtlas>(AssetsPaths.GAMEPLAY, TextureAtlas.class);

    // == fonts ==
    public static final AssetDescriptor<BitmapFont> FONT_32 =
            new AssetDescriptor<BitmapFont>(AssetsPaths.FONT_32, BitmapFont.class);

    // == skins ==
    public static final AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<Skin>(AssetsPaths.UI_SKIN, Skin.class);

    // == effects ==
    public static final AssetDescriptor<ParticleEffect> PARTICLE_HEAL =
            new AssetDescriptor<ParticleEffect>(AssetsPaths.PARTICLES_HEAL, ParticleEffect.class);

    public static final AssetDescriptor<ParticleEffect> PARTICLE_BLOOD =
            new AssetDescriptor<ParticleEffect>(AssetsPaths.PARTICLES_BLOOD, ParticleEffect.class);

    public static final AssetDescriptor<ParticleEffect> PARTICLE_DUST =
            new AssetDescriptor<ParticleEffect>(AssetsPaths.PARTICLES_DUST, ParticleEffect.class);
}
