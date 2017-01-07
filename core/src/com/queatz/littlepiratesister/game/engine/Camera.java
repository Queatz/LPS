package com.queatz.littlepiratesister.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.SimpleGroupStrategy;
import com.queatz.littlepiratesister.game.things.Thing;

/**
 * Created by jacob on 1/1/17.
 */

public class Camera {
    public OrthographicCamera cam = new OrthographicCamera();
    private SpriteBatch spriteBath = new SpriteBatch();
    private DecalBatch decalBatch = new DecalBatch(new SimpleGroupStrategy(cam));
    private Vector3 position = new Vector3();

    private float horizontalMaxView = 480;
    private float zoom;
    private Vector3 ref;

    public Camera() {
        setPersp(true);
        cam.far = 1000;
        cam.near = -1000;

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_LESS);
        Gdx.gl.glDepthRangef(-100, 100);
    }

    public void setPosition(Vector3 position) {
        this.position = position;
        this.cam.position.set(position);
        this.cam.update();
    }

    public DecalBatch use() {
        return decalBatch;
    }

    public SpriteBatch sprite() {
        return spriteBath;
    }

    public void render(Thing thing) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        spriteBath.setProjectionMatrix(cam.combined);
        spriteBath.begin();
        thing.render(this);
        spriteBath.end();
        decalBatch.flush();
    }

    public void setViewport(Vector2 viewport) {
        cam.viewportWidth = viewport.x;
        cam.viewportHeight = viewport.y;
        cam.zoom = zoom = horizontalMaxView / cam.viewportWidth;
        cam.update();
    }


    public Vector2 getViewport() {
        return new Vector2(cam.viewportWidth, cam.viewportHeight);
    }

    public Vector2 getViewportReal() {
        Vector3 unproject = cam.unproject(new Vector3());
        return new Vector2(-unproject.x, -unproject.y);
    }

    public float getZoom() {
        return zoom;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setRef(Vector3 ref) {
        this.ref = ref;
    }

    public Vector3 getRef() {
        return ref;
    }

    public void setPersp(boolean persp) {
        cam.up.set(0, 1, 0);
        cam.direction.set(0, 0, -1);

        if (persp) {
            cam.rotate(new Vector3(1, 0, 0), 45);
        }

        cam.update();
        spriteBath.setProjectionMatrix(cam.combined);
    }

    public Vector3 screenToWorld(Vector3 screenXY) {
        float z = (screenXY.y / cam.viewportHeight) - .5f;

        // Account for 45 degree rotation
        z *= (cam.viewportHeight / 2f) * zoom / 1000f;

        screenXY.z = .5f - (z / 1f);

        return cam.unproject(screenXY);
    }
}
