package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Positional;
import com.queatz.littlepiratesister.game.engine.Update;

/**
 * Created by jacob on 12/30/16.
 */

public class Encampment extends Thing {

    private float sentiment = ((float) Math.random()) * -50f;
    private static final float normalDist = 100;

    public Encampment() {

    }

    @Override
    public void update(Update update) {
        Vector3 me = update.world.positionOf(this);

        for (Positional positional : update.world.things.values()) {
            Class clazz = positional.thing.getClass();
            float dist = me.dst(positional.position);

            if (Player.class.isAssignableFrom(clazz)) {
                dist = 1 - (dist / normalDist);

                if (dist > 0) {
                    float influence = (float) Math.pow(dist, .5) * (float) update.delta * 25;
                    if (influence > 0) {
                        sentiment += influence;

                        // check player max influence + town's max influence
                        if (sentiment > 150) {
                            sentiment = 150;
                        }
                    }
                }
            }
        }
    }

    @Override
    public Decal render(Camera camera) {
        Texture image = ResourceManager.img("sentimental.png");
        float s = Math.abs(sentiment) * 10f;

        SpriteBatch draw = camera.sprite();
        if (sentiment < 0) {
            draw.setColor(Color.RED);
        } else {
            draw.setColor(Color.GREEN);
        }

        int restore[] = {draw.getBlendSrcFunc(), draw.getBlendDstFunc()};
        draw.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        draw.draw(image, camera.getRef().x + (s / -2), camera.getRef().y + (s / -2), s, s);
        draw.setColor(Color.WHITE);
        draw.setBlendFunction(restore[0], restore[1]);

        image = ResourceManager.img("house.png");
        Decal sprite = Decal.newDecal(new TextureRegion(image), true);
        sprite.setRotationX(45);
        return sprite;
    }
}
