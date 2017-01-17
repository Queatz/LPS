package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.engine.Scene;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by jacob on 12/30/16.
 *
 * Handles all scenes and transitions, such as initial and game scenes.
 */

public class SceneManager {

    private static Scene activeScene;
    private static Scene transitionTo;
    private static Date transitionStart;
    private static OrthographicCamera camera = new OrthographicCamera();
    private static SpriteBatch spriteBatch = new SpriteBatch();
    private static int transitionMs = 440;

    public static void dispose() {
        if (activeScene == null) {
            return;
        }

        activeScene.dispose();
    }

    public static void resize(Vector2 dimensions) {
        camera.viewportWidth = dimensions.x;
        camera.viewportHeight = dimensions.y;
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        if (activeScene == null) {
            return;
        }

        activeScene.resize(dimensions);
    }

    public static void update() {
        if (transitionStart != null && getFactor() <= 1 && getFactor() >= 0) {
            activeScene.factor(1 - getFactor());
        }

        if (transitionTo != null) {
            if (transitionStart == null || (new Date().getTime() - transitionStart.getTime()) > transitionMs) {
                set();
            }
        }

        if (activeScene == null) {
            return;
        }

        activeScene.update();
    }

    public static void render() {
        if (activeScene == null) {
            return;
        }

        activeScene.render();

        if (transitionStart != null && getFactor() <= 1 && getFactor() >= 0) {
            drawSwirl();
        } else {
            transitionStart = null;
        }
    }

    public static void set(Scene scene) {
        if (transitionTo != null) {
            return;
        }

        transitionTo = scene;

        if (activeScene != null) {
            transitionStart = new Date();
        }
    }

    private static void set() {
        if (activeScene != null) {
            activeScene.dispose();
        }

        activeScene = transitionTo;
        transitionTo = null;

        if (activeScene != null) {
            activeScene.init();
            resize(new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        }
    }

    private static float getFactor() {
        float diff = (float) (new Date().getTime() - transitionStart.getTime()) / transitionMs;

        if (diff < 1) {
            return diff;
        } else {
            return 2 - diff;
        }
    }

    private static void drawSwirl() {
        TextureRegion region = new TextureRegion(ResourceManager.img("water.png"), 0f, 0f, 1f, 1f);
        spriteBatch.begin();
        spriteBatch.setShader(ShaderManager.getSwirlShader());
        ShaderManager.shaderFactor(ShaderManager.getSwirlShader(), getFactor());
        spriteBatch.draw(region, -camera.viewportWidth / 2, -camera.viewportHeight / 2, camera.viewportWidth, camera.viewportHeight);
        spriteBatch.setShader(null);
        spriteBatch.end();
    }
}
