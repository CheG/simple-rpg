package com.romantupikov.utils.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.romantupikov.utils.MaterialColor;

public abstract class EntityBase {
    protected Vector2 position;
    protected Vector2 velocity;
    protected float width;
    protected float height;

    protected Color debugColor;

    protected EntityBase() {
        position = new Vector2();
        velocity = new Vector2();
        debugColor = MaterialColor.PINK;
    }

    public abstract void drawDebug(ShapeRenderer renderer);

    protected abstract void updateBounds();

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
        updateBounds();
    }

    public void setPosition(Vector2 newPosition) {
        position = newPosition;
        updateBounds();
    }

    public void setX(float x) {
        position.x = x;
        updateBounds();
    }

    public void setY(float y) {
        position.y = y;
        updateBounds();
    }

    public void setVelocity(float x, float y) {
        velocity.x = x;
        velocity.y = y;
    }

    public void setVelocity(Vector2 newVelocity) {
        velocity = newVelocity;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public void setDebugColor(Color debugColor) {
        this.debugColor = debugColor;
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector2 getVelocity() {
        return velocity.cpy();
    }

    public float getVelocityX() {
        return velocity.x;
    }

    public float getVelocityY() {
        return velocity.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
