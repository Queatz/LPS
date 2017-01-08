package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.ui.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacob on 12/30/16.
 *
 * Common UI elements.
 */

public class UIManager {
    public OrthographicCamera camera;
    public SpriteBatch spriteBatch;
    private float horizontalMaxView = 360;
    private Element startElement;

    private List<Element> elementList = new ArrayList<>();

    public UIManager() {
        camera = new OrthographicCamera();
        spriteBatch = new SpriteBatch();
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
    }

    public void resize(Vector2 dimensions) {
        camera.viewportWidth = dimensions.x;
        camera.viewportHeight = dimensions.y;
        camera.zoom = horizontalMaxView / camera.viewportWidth;
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public boolean startTap(Vector2 tap) {
        startElement = elementAtPosition(tap);
        return startElement != null;
    }

    public boolean tap(Vector2 tap) {
        Element element = elementAtPosition(tap);

        if (element != null && element == startElement) {
            element.tap();
        }

        return false;
    }

    private Element elementAtPosition(Vector2 tap) {
        Vector3 tap3 = camera.unproject(new Vector3(tap.x, tap.y, 0f));
        tap.x = tap3.x;
        tap.y = tap3.y;

        for (Element element : elementList) {
            if (!element.bounds().contains(tap)) {
                continue;
            }

            return element;
        }

        return null;
    }

    public void add(Element element) {
        element.uiManager = this;
        elementList.add(element);
    }
}
