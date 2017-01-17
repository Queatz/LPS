package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Update;

import java.util.Date;
import java.util.Random;

/**
 * Created by jacob on 12/30/16.
 */

public class Player extends Thing {

    private Vector3 oldPosition = new Vector3();

    @Override
    public void update(Update update) {
        Map map = ((Map) update.world.map().thing);
        if (map.color(existential.position).a > .9) {

            if (map.color(new Vector3(existential.position.x, oldPosition.y, 0)).a <= .9) {
                existential.position.y = oldPosition.y;
            } else if (map.color(new Vector3(oldPosition.x, existential.position.y, 0)).a <= .9) {
                existential.position.x = oldPosition.x;
            } else {
                existential.position.set(oldPosition);
            }
        }

        if (existential.sentiment.value < 0) {
            update.game.lose();
        }

        oldPosition.set(existential.position);
    }

    @Override
    public Decal render(Camera camera) {
        Texture image = ResourceManager.img("ship_2.png");
        Decal sprite = Decal.newDecal(new TextureRegion(image), true);
        float s = existential.sentiment.capacity / 50f;
        sprite.setScale(s);

        float boop = (float) Math.sin(new Date().getTime() / 533d + (new Random(this.id).nextFloat() % Math.PI));
        sprite.rotateZ(boop * 6.3f);
        sprite.rotateX(45);

        return sprite;
    }
}
