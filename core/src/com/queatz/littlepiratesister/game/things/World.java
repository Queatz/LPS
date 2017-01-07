package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.SentimentManager;
import com.queatz.littlepiratesister.game.ShaderManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Existential;
import com.queatz.littlepiratesister.game.engine.Sentiment;
import com.queatz.littlepiratesister.game.engine.Update;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by jacob on 12/30/16.
 */

public class World extends Thing {

    public HashMap<Integer, Existential> things = new HashMap<>();
    private Collection<Runnable> pendingItems = new Stack<>();
    private Color playerSentimentColor = new Color(15 / 255f, 255 / 255f, 127 / 255f, 1);

    @Override
    public void update(Update update) {
        for (Existential existential : this.things.values()) {
            // update sentiment
            calculateSentiment(update, existential);

            // update thing
            existential.thing.update(update);
        }

        // For object deletes

        Iterator<Runnable> i = pendingItems.iterator();
        while (i.hasNext()) {
            i.next().run();
        }

        pendingItems.clear();
    }

    @Override
    public Decal render(Camera camera) {
        background(camera);

        for (Existential existential : this.things.values()) {
            camera.setRef(existential.position);

            if (existential.sentiment != null) {
                SentimentManager.drawSentiment(camera, existential.sentiment.value, playerSentimentColor);
            }

            Decal decal = existential.thing.render(camera);

            if (decal != null) {
                decal.setPosition(new Vector3(existential.position));
                camera.use().add(decal);
            }
        }

        return null;
    }

    public void add(final Existential existential) {
        pendingItems.add(new Runnable() {
            @Override
            public void run() {
                if (existential.sentiment == null) {
                    existential.sentiment = new Sentiment();
                }

                if (existential.position == null) {
                    existential.position = new Vector3();
                }

                things.put(existential.thing.id, existential);
            }
        });
    }

    public void delete(final Thing thing) {
        this.pending(new Runnable() {
            @Override
            public void run() {
                World.this.things.remove(thing.id);
            }
        });
    }

    private void pending(Runnable runnable) {
        this.pendingItems.add(runnable);
    }

    public Existential get(Integer id) {
        if (this.things.containsKey(id)) {
            return this.things.get(id);
        }

        return null;
    }

    public void move(Thing thing, Vector3 position) {
        Existential positional = this.get(thing.id);

        if (positional != null) {
            positional.position.add(position);
        }
    }

    private void background(Camera camera) {
        Texture image = ResourceManager.img("water.png");
        float ts = image.getWidth();
        Vector2 vp = camera.getViewport();

        float s = camera.getZoom();
        SpriteBatch draw = camera.sprite();

        int xs = Math.max(1, (int) Math.ceil(vp.x / 2 * s / ts));
        int ys = Math.max(1, (int) Math.ceil(vp.y / 2 * s / ts)) * 2; // because rotate 45

        int xo = (int) Math.floor(camera.getPosition().x / ts);
        int yo = (int) Math.floor(camera.getPosition().y / ts);


        for (int x = -xs; x <= xs; x++) {
            for (int y = -ys; y <= ys; y++) {
                draw.draw(image, (xo + x) * ts, (yo + y) * ts);
            }
        }

        float offs = 0;//(float) (new Date().getTime() / 33000d % 1d);

        image = ResourceManager.img("water_crests.png");
//        int restore[] = {draw.getBlendSrcFunc(), draw.getBlendDstFunc()};
//        draw.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        draw.setShader(ShaderManager.getWaterShader());
        ShaderManager.shaderBegin(ShaderManager.getWaterShader());
        draw.setColor(1, 1, 1, .667f);
        ts *= 5;


        xs = Math.max(1, (int) Math.ceil(vp.x / 2 * s / ts));
        ys = Math.max(1, (int) Math.ceil(vp.y / 2 * s / ts)) * 2; // because rotate 45

        xo = (int) Math.floor(camera.getPosition().x / ts);
        yo = (int) Math.floor(camera.getPosition().y / ts);

        for (int x = -xs -1; x <= xs +1; x++) {
            for (int y = -ys -1; y <= ys +1; y++) {
                draw.draw(image, (xo + x - .25f) * ts, (yo + y + offs) * ts, ts, ts);
            }
        }
        draw.setColor(Color.WHITE);
//        draw.setBlendFunction(restore[0], restore[1]);
        draw.setShader(null);
    }

    public Vector3 getPosition(Thing thing) {
        Existential existential = get(thing.id);
        if (existential == null) {
            return new Vector3();
        }

        return new Vector3(existential.position);
    }

    private static final float normalDist = 100;

    private void calculateSentiment(Update update, Existential me) {
        for (Existential existential : update.world.things.values()) {
            float dist = me.position.dst(existential.position);

            if (existential.sentiment != null) {
                float amount = (float) update.delta * existential.sentiment.value * existential.sentiment.power;
                absorbSentiment(me.sentiment, dist, amount);
            }
        }
    }

    private void absorbSentiment(Sentiment sentiment, float dist, float power) {
        dist = 1 - (dist / normalDist);

        if (dist > 0) {
            float influence = (float) Math.pow(dist, .5) * power;
            sentiment.value += influence / (1 + sentiment.defense);

            // check player max influence + town's max influence
            if (Math.abs(sentiment.value) > sentiment.capacity) {
                sentiment.value = Math.signum(sentiment.value) * sentiment.capacity;
            }
        }
    }

}
