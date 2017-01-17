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
import com.queatz.littlepiratesister.game.ui.TownButton;

/**
 * Created by jacob on 1/16/17.
 */

public class OverworldScene extends Scene {
    private UIManager uiManager;
    private Music music;

    private static String[] townNames = {
            "Thilelle",
            "Maqruelle",
            "Vliad",
            "Reyenne",
            "Tessa",
            "Muycera",
            "Stissen",
            "Iations",
            "Desselle",
            "Mehrlitt",
            "Coera",
            "Lella",
            "Yvumen",
            "Vashter",
            "Thailiad",
            "Eessa",
    };

    @Override
    public void init() {
        uiManager = new UIManager();
        Gdx.input.setInputProcessor(new UIInput(uiManager));

        uiManager.add(new ScrollingWaterBackground("water.png", null));

        for (int i = 0; i < 16; i++) {
            int x = (i % 4);
            int y = (i / 4);

            uiManager.add(new TownButton(townNames[15 - i],
                    new Vector2(
                            -.75f + x / 4f * 2f,
                            -.75f + y / 4f * 2f
                    ).scl(.85f)));
        }

        uiManager.add(new BackButton(new Runnable() {
            @Override
            public void run() {
                SceneManager.set(new MainScene());
            }
        }));

        music = ResourceManager.mus("LPS1.ogg");
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
