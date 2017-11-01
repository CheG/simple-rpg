package com.romantupikov.utils.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public abstract class EntityCircleBase extends EntityBase {
    protected Circle bounds;

    public EntityCircleBase(float radius) {
        super();
        bounds = new Circle(position, radius);
    }

    @Override
    public void drawDebug(ShapeRenderer renderer) {
        renderer.setColor(debugColor);
        renderer.circle(bounds.x, bounds.y, bounds.radius, 20);
        renderer.x(bounds.x, bounds.y, 0.1f);
    }

    @Override
    protected void updateBounds() {
        bounds.setPosition(position);
        bounds.setRadius(width);
    }

    public Circle getBounds() {
        return bounds;
    }
}
