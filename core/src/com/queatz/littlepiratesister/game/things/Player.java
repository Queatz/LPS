package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.engine.Camera;

/**
 * Created by jacob on 12/30/16.
 */

public class Player extends Thing {
    @Override
    public Decal render(Camera camera) {
        Texture image = ResourceManager.img("ship_2.png");
        Decal sprite = Decal.newDecal(new TextureRegion(image), true);
        sprite.setScale(existential.sentiment.capacity / 50f);
        sprite.setRotationX(45);
        return sprite;
    }
}
