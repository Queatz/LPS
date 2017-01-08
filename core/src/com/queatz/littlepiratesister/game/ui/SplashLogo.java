package com.queatz.littlepiratesister.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.queatz.littlepiratesister.game.ResourceManager;

import java.util.Date;

/**
 * Created by jacob on 1/7/17.
 */

public class SplashLogo extends Element {

    @Override
    public void render() {
        Texture ship = ResourceManager.img("ship_1.png");

        float y = (float) Math.sin((new Date().getTime() / 500d) % (Math.PI * 2)) * 3f;
        float s = uiManager.camera.viewportWidth * uiManager.camera.zoom / (float) ship.getWidth() * .15125f;

        uiManager.spriteBatch.draw(ship, ship.getWidth() / -2 * s, 64 + y, ship.getWidth() * s, ship.getHeight() * s);
    }
}
