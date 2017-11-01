package com.romantupikov.game.simplerpg.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by hvitserk on 31-Oct-17.
 */

public class AssetsPacker {
    private static final boolean DRAW_DEBUG_OUTLINE = false;
    private static final boolean COMBINE_SUBDIRECTORIES = true;

    private static final String RAW_ASSETS_PATH = "desktop/assets-raw";
    private static final String OUTPUT_ATLAS_PATH = "android/assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;
        settings.combineSubdirectories = COMBINE_SUBDIRECTORIES;
        settings.maxHeight = 2048;
        settings.maxWidth = 2048;

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/gameplay",
                OUTPUT_ATLAS_PATH + "/gameplay",
                "gameplay");

        TexturePacker.process(settings,
                RAW_ASSETS_PATH + "/ui",
                OUTPUT_ATLAS_PATH + "/ui",
                "ui");
    }
}
