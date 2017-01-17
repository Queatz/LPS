package com.queatz.littlepiratesister.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.SceneManager;
import com.queatz.littlepiratesister.game.UIManager;
import com.queatz.littlepiratesister.game.engine.Scene;
import com.queatz.littlepiratesister.game.engine.UIInput;
import com.queatz.littlepiratesister.game.ui.BackButton;
import com.queatz.littlepiratesister.game.ui.ScrollingWaterBackground;
import com.queatz.littlepiratesister.game.ui.ContinueButton;
import com.queatz.littlepiratesister.game.ui.SplashLogo;

/**
 * Created by jacob on 12/30/16.
 */

public class MainScene extends Scene {

    private UIManager uiManager;
    private Music music;

    @Override
    public void init() {
        ResourceManager.snd("tap.ogg");
        uiManager = new UIManager();
        Gdx.input.setInputProcessor(new UIInput(uiManager));

        uiManager.add(new ScrollingWaterBackground("water.png", new Runnable() {
            @Override
            public void run() {
                SceneManager.set(new OverworldScene());
            }
        }));
        uiManager.add(new SplashLogo());
        uiManager.add(new ContinueButton());
        uiManager.add(new BackButton(new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        }));

        music = ResourceManager.mus("LPS_Menu.ogg");
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
    }

    @Override
    public void render() {
        uiManager.render();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        uiManager.dispose();
        music.stop();
    }

    @Override
    public void resize(Vector2 dimensions) {
        uiManager.resize(dimensions);
    }
}
