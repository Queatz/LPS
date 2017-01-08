package com.queatz.littlepiratesister.game.scenes;

import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.GameManager;
import com.queatz.littlepiratesister.game.UIManager;
import com.queatz.littlepiratesister.game.engine.Scene;
import com.queatz.littlepiratesister.game.ui.BackButton;

/**
 * Created by jacob on 12/30/16.
 */

public class GameScene extends Scene {
    private UIManager uiManager;
    private GameManager gameManager;

    @Override
    public void init() {
        gameManager = new GameManager();
        uiManager = new UIManager();
        uiManager.add(new BackButton());
        gameManager.uiManager = uiManager;
    }

    @Override
    public void update() {
        uiManager.update();
        gameManager.update();
    }

    @Override
    public void render() {
        gameManager.render();
        uiManager.render();
    }

    @Override
    public void resize(Vector2 dimensions) {
        gameManager.setViewport(dimensions);
        uiManager.resize(dimensions);
    }

    @Override
    public void dispose() {
        gameManager.dispose();
        uiManager.dispose();
    }
}
