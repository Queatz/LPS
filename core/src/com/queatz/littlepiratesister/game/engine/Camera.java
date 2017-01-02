package com.queatz.littlepiratesister.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.things.Thing;

/**
 * Created by jacob on 1/1/17.
 */

public class Camera {
    private OrthographicCamera cam = new OrthographicCamera();
    private SpriteBatch spriteBatch = new SpriteBatch();
    private Vector2 position = new Vector2();

    private float horizontalMaxView = 320;
    private float zoom;

    public Camera() {
        cam.update();
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        this.offset(new Vector2(0, 0));
    }

    public SpriteBatch use() {
        return spriteBatch;
    }

    public void render(Thing thing) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.enableBlending();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.setProjectionMatrix(cam.combined);

        offset(new Vector2());
        thing.render(this);
    }

    public void setViewport(Vector2 viewport) {
        cam.viewportWidth = viewport.x;
        cam.viewportHeight = viewport.y;
        cam.zoom = zoom = horizontalMaxView / cam.viewportWidth;
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);
    }

    public void offset(Vector2 offset) {
        cam.position.x = position.x - offset.x;
        cam.position.y = position.y - offset.y;
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);
    }

    public Vector2 getViewport() {
        return new Vector2(cam.viewportWidth, cam.viewportHeight);
    }

    public float getZoom() {
        return zoom;
    }

    public Vector2 getPosition() {
        return position;
    }
}
