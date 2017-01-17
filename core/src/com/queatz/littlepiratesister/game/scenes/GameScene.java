package com.queatz.littlepiratesister.game.scenes;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.GameManager;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.SceneManager;
import com.queatz.littlepiratesister.game.UIManager;
import com.queatz.littlepiratesister.game.engine.Scene;
import com.queatz.littlepiratesister.game.ui.BackButton;

/**
 * Created by jacob on 12/30/16.
 */

public class GameScene extends Scene {
    private UIManager uiManager;
    private GameManager gameManager;
    private Music music;

    @Override
    public void init() {
        gameManager = new GameManager();
        uiManager = new UIManager();
        uiManager.add(new BackButton(new Runnable() {
            @Override
            public void run() {
                SceneManager.set(new OverworldScene());
            }
        }));
        gameManager.uiManager = uiManager;

        music = ResourceManager.mus("LPS_Battle.ogg");
        music.setLooping(true);
        music.play();
    }

    @Override
    public void factor(float factor) {
        music.setVolume(factor);
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
        music.stop();
    }
}
