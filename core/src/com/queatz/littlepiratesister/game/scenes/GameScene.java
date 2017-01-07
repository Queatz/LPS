package com.queatz.littlepiratesister.game.scenes;

import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.GameManager;
import com.queatz.littlepiratesister.game.engine.Scene;

/**
 * Created by jacob on 12/30/16.
 */

public class GameScene extends Scene {
    private GameManager gameManager;

    @Override
    public void init() {
        gameManager = new GameManager();
    }

    @Override
    public void update() {
        gameManager.update();
    }

    @Override
    public void render() {
        gameManager.render();
    }

    @Override
    public void resize(Vector2 dimensions) {
        gameManager.setViewport(dimensions);
    }

    @Override
    public void dispose() {
        gameManager.dispose();
    }
}
