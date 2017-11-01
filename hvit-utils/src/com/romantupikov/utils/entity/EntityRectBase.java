package com.romantupikov.utils.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class EntityRectBase extends EntityBase {
    protected Rectangle bounds;

    public EntityRectBase() {
        super();
        bounds = new Rectangle(position.x, position.y, width, height);
        setSize(width, height);
    }

    @Override
    public void drawDebug(ShapeRenderer renderer) {
        renderer.setColor(debugColor);
        renderer.rect(bounds.x, bounds.y,
                0f, 0f,
                bounds.width, bounds.height,
                1f, 1f,
                rotation);
        renderer.x(bounds.x, bounds.y, 0.1f);
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
