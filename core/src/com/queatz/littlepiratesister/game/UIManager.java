package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.engine.UIInput;
import com.queatz.littlepiratesister.game.scenes.GameScene;
import com.queatz.littlepiratesister.game.ui.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacob on 12/30/16.
 *
 * Common UI elements.
 */

public class UIManager {
    private InputProcessor inputProcessor;
    public OrthographicCamera camera;
    public SpriteBatch spriteBatch;

    private List<Element> elementList = new ArrayList<>();

    public UIManager() {
        inputProcessor = new UIInput(this);
        camera = new OrthographicCamera();
        spriteBatch = new SpriteBatch();
    }

    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public void update() {
        for (Element element : elementList) {
            element.update();
        }
    }

    public void render() {
        spriteBatch.begin();
        for (Element element : elementList) {
            element.render();
        }
        spriteBatch.end();
    }

    public void dispose() {
        spriteBatch.dispose();
    }

    public void resize(Vector2 dimensions) {
        camera.viewportWidth = dimensions.x;
        camera.viewportHeight = dimensions.y;
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public void tap(Vector2 vector2) {
        // find element and tap that...
        SceneManager.set(new GameScene());
    }

    public void add(Element element) {
        element.uiManager = this;
        elementList.add(element);
    }
}
