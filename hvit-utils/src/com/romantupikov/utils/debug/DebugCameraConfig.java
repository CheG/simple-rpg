package com.romantupikov.utils.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.StringBuilder;

class DebugCameraConfig {
    private static final Logger log = new Logger("DebugCameraController", Logger.DEBUG);

    private static final String CONFIG_FILE_PATH = "debug/debug-camera-config.json";

    private static final String MOVE_SPEED = "moveSpeed";
    private static final String ZOOM_SPEED = "zoomSpeed";
    private static final String MAX_ZOOM_IN = "maxZoomIn";
    private static final String MAX_ZOOM_OUT = "maxZoomOut";

    private static final String UP_KEY = "upKey";
    private static final String LEFT_KEY = "leftKey";
    private static final String DOWN_KEY = "downKey";
    private static final String RIGHT_KEY = "rightKey";

    private static final String ZOOM_IN_KEY = "zoomInKey";
    private static final String ZOOM_OUT_KEY = "zoomOutKey";

    private static final String RESET_KEY = "resetKey";
    private static final String LOG_KEY = "logKey";

    private static final int DEFAULT_UP_KEY = Input.Keys.W;
    private static final int DEFAULT_LEFT_KEY = Input.Keys.A;
    private static final int DEFAULT_DOWN_KEY = Input.Keys.S;
    private static final int DEFAULT_RIGHT_KEY = Input.Keys.D;
    private static final int DEFAULT_ZOOM_IN_KEY = Input.Keys.E;
    private static final int DEFAULT_ZOOM_OUT_KEY = Input.Keys.Q;
    private static final int DEFAULT_RESET_KEY = Input.Keys.Z;
    private static final int DEFAULT_LOG_KEY = Input.Keys.C;

    private static final float DEFAULT_MOVE_SPEED = 20f;
    private static final float DEFAULT_ZOOM_SPEED = 2f;

    private static final float DEFAULT_MAX_ZOOM_OUT = 10f;
    private static final float DEFAULT_MAX_ZOOM_IN = 0.2f;

    private float moveSpeed;
    private float zoomSpeed;
    private float maxZoomIn;
    private float maxZoomOut;

    private int leftKey;
    private int upKey;
    private int downKey;
    private int rightKey;

    private int zoomInKey;
    private int zoomOutKey;

    private int resetKey;
    private int logKey;

    private FileHandle fileHandle;

    DebugCameraConfig() {
        init();
    }

    private void init() {
        fileHandle = Gdx.files.internal(CONFIG_FILE_PATH);

        if(fileHandle.exists()) {
            load();
        } else {
            log.debug("Config file not found: " + CONFIG_FILE_PATH + "; loading defaults");
            loadDefaults();
        }
    }

    private void load() {
        try {
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(fileHandle);

            moveSpeed = root.getFloat(MOVE_SPEED, DEFAULT_MOVE_SPEED);
            zoomSpeed = root.getFloat(ZOOM_SPEED, DEFAULT_ZOOM_SPEED);
            maxZoomIn = root.getFloat(MAX_ZOOM_IN, DEFAULT_MAX_ZOOM_IN);
            maxZoomOut = root.getFloat(MAX_ZOOM_OUT, DEFAULT_MAX_ZOOM_OUT);

            upKey = getInputKeyValue(root, UP_KEY, DEFAULT_UP_KEY);
            leftKey = getInputKeyValue(root, LEFT_KEY, DEFAULT_LEFT_KEY);
            downKey = getInputKeyValue(root, DOWN_KEY, DEFAULT_DOWN_KEY);
            rightKey = getInputKeyValue(root, RIGHT_KEY, DEFAULT_RIGHT_KEY);

            zoomInKey = getInputKeyValue(root, ZOOM_IN_KEY, DEFAULT_ZOOM_IN_KEY);
            zoomOutKey = getInputKeyValue(root, ZOOM_OUT_KEY, DEFAULT_ZOOM_OUT_KEY);

            resetKey = getInputKeyValue(root, RESET_KEY, DEFAULT_RESET_KEY);
            logKey = getInputKeyValue(root, LOG_KEY, DEFAULT_LOG_KEY);

        } catch(Exception e) {
            log.debug("Error loading: " + CONFIG_FILE_PATH + "; loading defaults");
            loadDefaults();
        }
    }

    private void loadDefaults() {
        moveSpeed = DEFAULT_MOVE_SPEED;
        zoomSpeed = DEFAULT_ZOOM_SPEED;
        maxZoomIn = DEFAULT_MAX_ZOOM_IN;
        maxZoomOut = DEFAULT_MAX_ZOOM_OUT;

        leftKey = DEFAULT_LEFT_KEY;
        upKey = DEFAULT_UP_KEY;
        downKey = DEFAULT_DOWN_KEY;
        rightKey = DEFAULT_RIGHT_KEY;

        zoomInKey = DEFAULT_ZOOM_IN_KEY;
        zoomOutKey = DEFAULT_ZOOM_OUT_KEY;

        resetKey = DEFAULT_RESET_KEY;
        logKey = DEFAULT_LOG_KEY;
    }

    float getMoveSpeed() {
        return moveSpeed;
    }

    float getZoomSpeed() {
        return zoomSpeed;
    }

    float getMaxZoomIn() {
        return maxZoomIn;
    }

    float getMaxZoomOut() {
        return maxZoomOut;
    }

    boolean isUpPressed() {
        return Gdx.input.isKeyPressed(upKey);
    }

    boolean isLeftPressed() {
        return Gdx.input.isKeyPressed(leftKey);
    }

    boolean isDownPressed() {
        return Gdx.input.isKeyPressed(downKey);
    }

    boolean isRightPressed() {
        return Gdx.input.isKeyPressed(rightKey);
    }

    boolean isZoomInPressed() {
        return Gdx.input.isKeyPressed(zoomInKey);
    }

    boolean isZoomOutPressed() {
        return Gdx.input.isKeyPressed(zoomOutKey);
    }

    boolean isResetPressed() {
        return Gdx.input.isKeyJustPressed(resetKey);
    }

    boolean isLogPressed() {
        return Gdx.input.isKeyJustPressed(logKey);
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder("DebugCameraConfig {");
        strBuilder.append("\n\tmoveSpeed: ");
        strBuilder.append(moveSpeed);
        strBuilder.append("\n\tzoomSpeed: ");
        strBuilder.append(zoomSpeed);
        strBuilder.append("\n\tmaxZoomIn: ");
        strBuilder.append(maxZoomIn);
        strBuilder.append("\n\tmaxZoomOut: ");
        strBuilder.append(maxZoomOut);
        strBuilder.append("\n\tleftKey: ");
        strBuilder.append(Input.Keys.toString(leftKey));
        strBuilder.append("\n\tupKey: ");
        strBuilder.append(Input.Keys.toString(upKey));
        strBuilder.append("\n\tdownKey: ");
        strBuilder.append(Input.Keys.toString(downKey));
        strBuilder.append("\n\trightKey: ");
        strBuilder.append(Input.Keys.toString(rightKey));
        strBuilder.append("\n\tzoomInKey: ");
        strBuilder.append(Input.Keys.toString(zoomInKey));
        strBuilder.append("\n\tzoomOutKey: ");
        strBuilder.append(Input.Keys.toString(zoomOutKey));
        strBuilder.append("\n\tresetKey: ");
        strBuilder.append(Input.Keys.toString(resetKey));
        strBuilder.append("\n\tlogKey: ");
        strBuilder.append(Input.Keys.toString(logKey));
        strBuilder.append("\n}");
        return strBuilder.toString();
    }

    private static int getInputKeyValue(JsonValue root, String name, int defaultKey) {
        String keyName = root.getString(name, Input.Keys.toString(defaultKey));
        return Input.Keys.valueOf(keyName);
    }
}
