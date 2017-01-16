package com.queatz.littlepiratesister.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
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

    @Override
    public void init() {
        uiManager = new UIManager();
        Gdx.input.setInputProcessor(new UIInput(uiManager));

        uiManager.add(new ScrollingWaterBackground("water_crests.png"));

        for (int i = 0; i < 15; i++) {
            uiManager.add(new TownButton("Thilelle", new Vector2(-.75f + (i % 4) / 3f, -.65f + (i / 4) * .35f)));
        }

        uiManager.add(new BackButton(new Runnable() {
            @Override
            public void run() {
                SceneManager.set(new MainScene());
            }
        }));
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
    }

    @Override
    public void resize(Vector2 dimensions) {
        uiManager.resize(dimensions);
    }
}
