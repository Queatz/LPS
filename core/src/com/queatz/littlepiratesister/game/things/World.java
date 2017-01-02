package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Positional;
import com.queatz.littlepiratesister.game.engine.Update;

import java.util.HashMap;

/**
 * Created by jacob on 12/30/16.
 */

public class World extends Thing {

    private HashMap<Integer, Positional> things = new HashMap<>();

    @Override
    public void update(Update update) {
        for (Positional positional : this.things.values()) {
            positional.thing.update(update);
        }
    }

    @Override
    public void render(Camera camera) {
        camera.use().begin();

        background(camera);

        for (Positional positional : this.things.values()) {
            camera.offset(positional.position);
            positional.thing.render(camera);
        }

        camera.use().end();
    }

    public void add(Thing thing) {
        things.put(thing.id, new Positional(thing));
    }

    public void delete(Thing thing) {
        this.things.remove(thing.id);
    }

    public Positional get(Integer id) {
        if (this.things.containsKey(id)) {
            return this.things.get(id);
        }

        return null;
    }

    public void move(Thing thing, Vector2 position) {
        Positional positional = this.get(thing.id);

        if (positional != null) {
            positional.position.add(position);
        }
    }

    private void background(Camera camera) {
        Texture image = ResourceManager.img("water.png");
        float ts = image.getWidth();
        Vector2 vp = camera.getViewport();

        float s = camera.getZoom();
        SpriteBatch draw = camera.use();

        int xs = Math.max(1, (int) Math.ceil(vp.x / 2 * s / ts));
        int ys = Math.max(1, (int) Math.ceil(vp.y / 2 * s / ts));

        int xo = (int) Math.floor(camera.getPosition().x / ts);
        int yo = (int) Math.floor(camera.getPosition().y / ts);

        for (int x = -xs; x <= xs; x++) {
            for (int y = -ys; y <= ys; y++) {
                draw.draw(image, (xo + x) * ts, (yo + y) * ts);
            }
        }
    }

    public Vector2 positionOf(Thing thing) {
        Positional positional = get(thing.id);
        if (positional == null) {
            return new Vector2();
        }

        return new Vector2(positional.position);
    }
}
