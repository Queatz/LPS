package com.queatz.littlepiratesister.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.UIManager;
import com.queatz.littlepiratesister.game.engine.Scene;
import com.queatz.littlepiratesister.game.ui.Background;
import com.queatz.littlepiratesister.game.ui.Button;
import com.queatz.littlepiratesister.game.ui.Name;

/**
 * Created by jacob on 12/30/16.
 */

public class MainScene extends Scene {

    private UIManager uiManager;

    @Override
    public void init() {
        uiManager = new UIManager();
        Gdx.input.setInputProcessor(uiManager.getInputProcessor());

        uiManager.add(new Background("water.png"));
        uiManager.add(new Name());
        uiManager.add(new Button());
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
