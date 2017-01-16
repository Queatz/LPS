package com.queatz.littlepiratesister.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.ui.Element;

/**
 * Created by jacob on 1/16/17.
 */

public class TownButton extends Element {

    private String name;
    private Vector2 position;

    public TownButton(String name, Vector2 position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public void render() {
        Texture image = ResourceManager.img("house.png");

        uiManager.spriteBatch.draw(image,
                uiManager.camera.viewportWidth / 2 * uiManager.camera.zoom * position.x,
                uiManager.camera.viewportHeight / 2 * uiManager.camera.zoom * position.y,
                image.getWidth() / 2,
                image.getHeight() / 2);
    }

    @Override
    public boolean tap() {
        return super.tap();
    }

    @Override
    public Rectangle bounds() {
        return super.bounds();
    }
}
