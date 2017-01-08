package com.queatz.littlepiratesister.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.SceneManager;
import com.queatz.littlepiratesister.game.scenes.MainScene;

/**
 * Created by jacob on 1/7/17.
 */

public class BackButton extends Element {
    private Texture backButton = ResourceManager.img("back.png");

    @Override
    public void render() {
        float s = .5f;

        Vector3 position = new Vector3(
                uiManager.camera.viewportWidth / 2 - backButton.getWidth() * s,
                -uiManager.camera.viewportHeight / 2
                , 0);

        uiManager.spriteBatch.draw(backButton, position.x, position.y, backButton.getWidth() * s, backButton.getHeight() * s);
    }

    @Override
    public boolean tap() {
        SceneManager.set(new MainScene());
        return true;
    }

    @Override
    public Rectangle bounds() {
        float s = .5f;
        Vector2 position = new Vector2(
                uiManager.camera.viewportWidth / 2 - backButton.getWidth() * s,
                -uiManager.camera.viewportHeight / 2);
        return new Rectangle(position.x, position.y, backButton.getWidth() * s, backButton.getHeight() * s);
    }
}
