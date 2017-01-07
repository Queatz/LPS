package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.engine.Scene;

/**
 * Created by jacob on 12/30/16.
 *
 * Handles all scenes and transitions, such as initial and game scenes.
 */

public class SceneManager {

    private static Scene activeScene;

    public static void dispose() {
        if (activeScene == null) {
            return;
        }

        activeScene.dispose();
    }

    public static void resize(Vector2 dimensions) {
        if (activeScene == null) {
            return;
        }

        activeScene.resize(dimensions);
    }

    public static void update() {
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
    }

    public static void set(Scene scene) {
        if (activeScene != null) {
            activeScene.dispose();
        }

        activeScene = scene;

        if (activeScene != null) {
            activeScene.init();
            activeScene.resize(new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        }
    }
}
