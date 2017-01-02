package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.engine.Camera;

/**
 * Created by jacob on 12/30/16.
 */

public class Player extends Thing {
    @Override
    public void render(Camera camera) {
        SpriteBatch draw = camera.use();
        Texture image = ResourceManager.img("pirate_ship.png");
        draw.draw(image, image.getWidth() / -2, 0);
    }
}
