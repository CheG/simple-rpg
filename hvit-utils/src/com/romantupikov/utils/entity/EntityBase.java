package com.romantupikov.utils.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.romantupikov.utils.MaterialColor;

public abstract class EntityBase {
    protected Vector2 position;
    protected float width = 1f;
    protected float height = 1f;
    protected float scaleX = 1f;
    protected float scaleY = 1f;
    protected float rotation;
    protected float origX, origY;

    protected Color debugColor;

    protected EntityBase() {
        position = new Vector2();
        origX = width / 2f;
        origY = height / 2f;
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
        position.x = newPosition.x;
        position.y = newPosition.y;
        updateBounds();
    }

    public void setX(float x) {
        position.x = x - origX;
        updateBounds();
    }

    public void setY(float y) {
        position.y = y - origY;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        this.origX = width / 2f;
        this.origY = height / 2f;
        updateBounds();
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        updateBounds();
    }

    public void setDebugColor(Color debugColor) {
        this.debugColor = debugColor;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public float getOrigX() {
        return origX;
    }

    public void setOrigX(float origX) {
        this.origX = origX;
    }

    public float getOrigY() {
        return origY;
    }

    public void setOrigY(float origY) {
        this.origY = origY;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }
}
