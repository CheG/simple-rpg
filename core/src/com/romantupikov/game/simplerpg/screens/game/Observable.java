package com.romantupikov.game.simplerpg.screens.game;

/**
 * Created by hvitserk on 01-Nov-17.
 */

public interface Observable {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}
