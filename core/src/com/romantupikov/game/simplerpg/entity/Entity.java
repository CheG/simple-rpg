package com.romantupikov.game.simplerpg.entity;

import com.romantupikov.game.simplerpg.entity.component.Component;
import com.romantupikov.game.simplerpg.screen.game.GameController;
import com.romantupikov.utils.entity.EntityBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hvitserk on 17-Nov-17.
 */

public class Entity extends EntityBase {
    private final GameController controller;
    private Map<String, Component> components;

    public Entity(GameController controller) {
        super();
        this.controller = controller;
        components = new HashMap<String, Component>();
    }

    public void addComponent(String name, Component component) {
        components.put(name, component);
    }

    public void removeComponent(String name) {
        components.remove(name);
    }

    public Component getComponent(String name) {
        return components.get(name);
    }

    public GameController getController() {
        return controller;
    }
}
