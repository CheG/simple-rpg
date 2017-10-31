package com.romantupikov.game.simplerpg.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by hvitserk on 31-Oct-17.
 */

public class AssetsDescriptors {
    private AssetsDescriptors() {}

    // == atlases ==
    public static final AssetDescriptor<TextureAtlas> GAMEPLAY =
            new AssetDescriptor<TextureAtlas>(AssetsPaths.GAMEPLAY, TextureAtlas.class);
}
