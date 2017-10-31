package com.romantupikov.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewportUtils {
    private ViewportUtils() {
    }

    private static final Logger log = new Logger("ViewportUtils", Logger.DEBUG);

    private static final float DEFAULT_CELL_SIZE = 1f;

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer) {
        drawGrid(viewport, renderer, DEFAULT_CELL_SIZE);
    }

    public static void drawGrid(Viewport viewport, ShapeRenderer renderer, float cellSize) {
        if (viewport == null) {
            throw new IllegalArgumentException("Viewport cant be null.");
        }
        if (renderer == null) {
            throw new IllegalArgumentException("ShapeRenderer cant be null.");
        }
        if (cellSize < 0.1f) {
            cellSize = 0.1f;
        }

//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float doubleWorldWidth = worldWidth * 2f;
        float doubleWorldHeight = worldHeight * 2f;

        // grid
        renderer.setColor(MaterialColor.GREY_900);
        for (float x = -doubleWorldWidth; x <= doubleWorldWidth; x += cellSize) {
            renderer.line(x, -doubleWorldHeight, x, doubleWorldHeight);
        }
        for (float y = -doubleWorldHeight; y <= doubleWorldHeight; y += cellSize) {
            renderer.line(-doubleWorldWidth, y, doubleWorldWidth, y);
        }

        // center
        renderer.setColor(MaterialColor.GREY_800);
        renderer.line(0f, -doubleWorldHeight, 0f, doubleWorldHeight);
        renderer.line(-doubleWorldWidth, 0f, doubleWorldWidth, 0f);

        // world bounds
        renderer.setColor(MaterialColor.LIGHT_GREEN);
        renderer.line(0f, 0f, 0f, worldHeight);
        renderer.line(0f, worldHeight, worldWidth, worldHeight);
        renderer.line(worldWidth, worldHeight, worldWidth, 0f);
        renderer.line(worldWidth, 0f, 0f, 0f);

        renderer.setColor(Color.WHITE);
        renderer.end();
//        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public static void debugPixelPerUnit(Viewport viewport) {
        if (viewport == null) {
            throw new IllegalArgumentException("Viewport cant be null");
        }

        float screenWidth = viewport.getScreenWidth();
        float screenHeight = viewport.getScreenHeight();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float xPPU = screenWidth / worldWidth;
        float yPPU = screenHeight / worldHeight;

        log.debug("debugPixelPerUnit: x = " + xPPU + " y = " + yPPU);
    }
}
