package com.romantupikov.utils.entity;

import com.badlogic.gdx.math.Circle;

public abstract class EntityCircleBase extends EntityBase {
    protected Circle bounds;
    protected float radius = 1f;

    public EntityCircleBase() {
        super();
        bounds = new Circle(position.x, position.y, radius);
    }

    @Override
    protected void updateBounds() {
        bounds.setPosition(position.x + origX, position.y + origY);
        bounds.setRadius(width / 2f);
    }

    public Circle getBounds() {
        return bounds;
    }
}
