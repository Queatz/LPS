package com.queatz.littlepiratesister.game.ui;

import com.badlogic.gdx.math.Rectangle;
import com.queatz.littlepiratesister.game.UIManager;

/**
 * Created by jacob on 1/4/17.
 */

public class Element {

    // Set when added to UIManager
    public UIManager uiManager;

    public void update() {

    }

    public void render() {

    }

    public boolean tap() {
        return false;
    }

    public Rectangle bounds() {
        return new Rectangle();
    }
}
