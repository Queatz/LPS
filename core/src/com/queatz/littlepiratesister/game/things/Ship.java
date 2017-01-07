package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Update;

/**
 * Created by jacob on 12/30/16.
 */

public class Ship extends Thing {
    private Vector3 target;
    private float shipSpeed = 16;
    private float life = 30;

    public void setTarget(Vector3 target) {
        this.target = target;
    }

    @Override
    public void update(Update update) {

        life -= update.delta;

        if (life <= 0) {
            update.world.delete(this);
            return;
        }

        float scl = shipSpeed * (float) update.delta;

        if (Math.abs(existential.sentiment.value) < existential.sentiment.capacity) {
            scl /= 2;
        }

        Vector3 current = update.world.getPosition(this);

        // Vector
        Vector2 pos = new Vector2(
                target.x - current.x,
                target.y - current.y
        );

        // Move in the desired direction at that speed
        update.world.move(this, new Vector3(pos.nor().scl(scl), 0));

        // decide to move if sentiment is reached
    }

    @Override
    public Decal render(Camera camera) {
        Texture image = ResourceManager.img("ship_" + (existential.sentiment.capacity > 10 ? "2" : "1") + ".png");
        Decal sprite = Decal.newDecal(new TextureRegion(image), true);
        sprite.setRotationX(45);

        float s;

        if (life > 29) {
            s = 30 - life;
        } else if (life < 1) {
            s = life;
        } else {
            s = 1;
        }

        sprite.setScale((existential.sentiment.capacity > 10 ? .2f : .125f) * s);
        return sprite;
    }
}
