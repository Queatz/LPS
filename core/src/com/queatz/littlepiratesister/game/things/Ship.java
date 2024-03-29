package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Existential;
import com.queatz.littlepiratesister.game.engine.Update;

import java.util.Date;
import java.util.Random;

/**
 * Created by jacob on 12/30/16.
 */

public class Ship extends Thing {
    private Vector3 target;
    private Existential targetSubject;
    private Vector3 evadeTarget;
    private float evadeTime;
    private float shipSpeed = (float) Math.random() * 36f + 16f;
    private float life = 30;
    private float stuckTime;
    private float previousSentiment;

    private static Sound[] sounds = new Sound[] {
            ResourceManager.snd("got_u.ogg"),
            ResourceManager.snd("got_u2.ogg"),
            ResourceManager.snd("got_u3.ogg")
    };

    private static Sound[] soundsUtoh = new Sound[] {
            ResourceManager.snd("grunt_1.ogg"),
            ResourceManager.snd("grunt_2.ogg"),
            ResourceManager.snd("grunt_3.ogg")
    };

    public void setTarget(Vector3 target) {
        this.target = target;
        targetSubject = null;
    }

    public void setTarget(Existential target) {
        targetSubject = target;
    }

    @Override
    public void update(Update update) {

        if (previousSentiment != 0 && Math.signum(existential.sentiment.value) != Math.signum(previousSentiment)) {
            if (existential.sentiment.value > 0) {
                sounds[new Random().nextInt(3)].play();
            } else {
                soundsUtoh[new Random().nextInt(3)].play();
            }
        }

        previousSentiment = existential.sentiment.value;

//        life -= update.delta;
//
//        if (life <= 0) {
//            update.world.delete(this);
//            return;
//        }

        float scl = shipSpeed * (float) update.delta;

        if (Math.abs(existential.sentiment.value) < existential.sentiment.capacity) {
            scl /= 3;
        }

        Vector3 current = update.world.getPosition(this);

        if (targetSubject != null) {
            target.set(targetSubject.position);
        }

        // Vector
        Vector2 pos = new Vector2(
                target.x - current.x,
                target.y - current.y
        );

        Vector3 oldPosition = new Vector3(existential.position);

        // Move in the desired direction at that speed
        if (evadeTarget != null) {
            update.world.move(this, new Vector3(evadeTarget).scl(scl));

            if (evadeTime < 0) {
                evadeTarget = null;
            } else {
                evadeTime -= update.delta;
            }
        } else {
            Vector3 move = new Vector3(pos.nor().scl(scl), 0);

            if (current.dst(target) > 16) {
                update.world.move(this, move);
            }
        }

        Map map = ((Map) update.world.map().thing);
        if (map.color(existential.position).a > .9) {
            if (map.color(new Vector3(existential.position.x, oldPosition.y, 0)).a <= .9) {
                existential.position.y = oldPosition.y;
            } else if (map.color(new Vector3(oldPosition.x, existential.position.y, 0)).a <= .9) {
                existential.position.x = oldPosition.x;
            } else {
                existential.position.set(oldPosition);
                stuckTime += update.delta;
            }
        } else {
            stuckTime = 0;
        }

        if (stuckTime > 1) {
            float dir = (float) Math.random() * (float) Math.PI * 2;
            evadeTarget = new Vector3((float) Math.cos(dir), (float) Math.sin(dir), 0);
            evadeTime = (float) Math.random() * 7;
        }
    }

    @Override
    public Decal render(Camera camera) {
        Texture image = ResourceManager.img("ship_" + (existential.sentiment.capacity > 10 ? "2" : "1") + ".png");
        Decal sprite = Decal.newDecal(new TextureRegion(image), true);

        float boop = (float) Math.sin(new Date().getTime() / 533d + (new Random(this.id).nextFloat() % Math.PI));

        sprite.rotateZ(boop * 6.3f);
        sprite.rotateX(45);

        float s = 1;

//        if (life > 29) {
//            s = 30 - life;
//        } else if (life < 1) {
//            s = life;
//        } else {
//            s = 1;
//        }

        sprite.setScale((existential.sentiment.capacity > 10 ? .2f : .125f) * s);
        return sprite;
    }
}
