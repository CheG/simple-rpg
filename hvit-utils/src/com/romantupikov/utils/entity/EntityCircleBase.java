package com.romantupikov.utils.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public abstract class EntityCircleBase extends EntityBase {
    protected Circle bounds;
    protected float radius = 1f;

    public EntityCircleBase() {
        super();
        bounds = new Circle(position.x, position.y, radius);
    }

    @Override
    public void drawDebug(ShapeRenderer renderer) {
        renderer.setColor(debugColor);
        renderer.circle(bounds.x, bounds.y, bounds.radius, 20);
        renderer.x(bounds.x, bounds.y, 0.1f);
    }

    @Override
    protected void updateBounds() {
        bounds.setPosition(position.x, position.y);
        bounds.setRadius(width / 2f);
    }

    public Circle getBounds() {
        return bounds;
    }
}
