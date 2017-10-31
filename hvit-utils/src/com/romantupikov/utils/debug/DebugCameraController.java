package com.romantupikov.utils.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

public class DebugCameraController {
    private static final Logger log = new Logger("DebugCameraController", Logger.DEBUG);

    private DebugCameraConfig config;

    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();

    private float zoom = 1f;

    public DebugCameraController() {
        config = new DebugCameraConfig();
        log.debug(config.toString());
    }

    public void setStartPosition(float x, float y) {
        startPosition.set(x, y);
        position.set(x, y);
    }

    public void applyTo(Camera camera) {
        camera.position.set(position, 0f);
        if(camera instanceof OrthographicCamera) {
            ((OrthographicCamera) camera).zoom = zoom;
        }
        camera.update();
    }

    public void handleDebugInput(float delta) {
        if(!(Application.ApplicationType.Desktop.equals(Gdx.app.getType()))) {
            return;
        }

        float moveSpeed = (config.getMoveSpeed() * (zoom / 2f)) * delta;
        float zoomSpeed = config.getZoomSpeed() * delta;

        if(config.isLeftPressed()) {
            moveOnXAxis(-moveSpeed);
        }
        if(config.isRightPressed()) {
            moveOnXAxis(moveSpeed);
        }
        if(config.isUpPressed()) {
            moveOnYAxis(moveSpeed);
        }
        if(config.isDownPressed()) {
            moveOnYAxis(-moveSpeed);
        }
        if(config.isZoomInPressed()) {
            zoomInOut(-zoomSpeed);
        }
        if(config.isZoomOutPressed()) {
            zoomInOut(zoomSpeed);
        }
        if(config.isLogPressed()) {
            logPosition();
        }
        if(config.isResetPressed()) {
            resetPosition();
        }
    }

    private void setPosition(float x, float y) {
        position.set(x, y);
    }

    private void setZoom(float value) {
        zoom = MathUtils.clamp(value, config.getMaxZoomIn(), config.getMaxZoomOut());
    }

    private void moveCamera(float xSpeed, float ySpeed) {
        setPosition(position.x + xSpeed, position.y + ySpeed);
    }

    private void moveOnXAxis(float speed) {
        moveCamera(speed, 0f);
    }

    private void moveOnYAxis(float speed) {
        moveCamera(0f, speed);
    }

    private void zoomInOut(float zoomSpeed) {
        setZoom(zoom + zoomSpeed);
    }

    private void resetPosition() {
        position.set(startPosition);
        setZoom(1.0f);
    }

    private void logPosition() {
        log.debug("Camera pos: " + position.toString() + " zoom: " + this.zoom);
    }
}
