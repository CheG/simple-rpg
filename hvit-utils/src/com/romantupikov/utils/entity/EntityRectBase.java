package com.romantupikov.utils.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class EntityRectBase extends EntityBase {
    protected Rectangle bounds;

    public EntityRectBase() {
        super();
        bounds = new Rectangle(position.x, position.y, width, height);
        setSize(width, height);
        origX = width / 2f;
        origY = height / 2f;
    }

    @Override
    public void drawDebug(ShapeRenderer renderer) {
        renderer.setColor(debugColor);
        renderer.rect(bounds.x, bounds.y,
                origX, origY,
                bounds.width, bounds.height,
                1f, 1f,
                rotation);
//        renderer.x(bounds.x + origX, bounds.y + origY, 0.1f);
    }

    @Override
    protected void updateBounds() {
        bounds.setPosition(position);
        bounds.setSize(width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
