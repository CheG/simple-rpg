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
    protected void updateBounds() {
        bounds.setPosition(position);
        bounds.setSize(width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
