package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.ShaderManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Positional;
import com.queatz.littlepiratesister.game.engine.Update;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by jacob on 12/30/16.
 */

public class World extends Thing {

    HashMap<Integer, Positional> things = new HashMap<>();

    @Override
    public void update(Update update) {
        for (Positional positional : this.things.values()) {
            positional.thing.update(update);
        }
    }

    @Override
    public Decal render(Camera camera) {
        background(camera);

        for (Positional positional : this.things.values()) {
            camera.setRef(positional.position);
            Decal decal = positional.thing.render(camera);

            if (decal != null) {
                decal.setPosition(new Vector3(positional.position));
                camera.use().add(decal);
            }
        }

        return null;
    }

    public void add(Thing thing) {
        things.put(thing.id, new Positional(thing));
    }

    public void add(Thing thing, Vector3 position) {
        things.put(thing.id, new Positional(thing, position));
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

    public void move(Thing thing, Vector3 position) {
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

    public Vector3 positionOf(Thing thing) {
        Positional positional = get(thing.id);
        if (positional == null) {
            return new Vector3();
        }

        return new Vector3(positional.position);
    }
}
