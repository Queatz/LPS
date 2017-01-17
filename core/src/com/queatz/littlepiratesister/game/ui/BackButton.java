package com.queatz.littlepiratesister.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.SceneManager;
import com.queatz.littlepiratesister.game.scenes.MainScene;
import com.queatz.littlepiratesister.game.scenes.OverworldScene;

/**
 * Created by jacob on 1/7/17.
 */

public class BackButton extends Element {
    private Texture backButton = ResourceManager.img("back.png");
    private float scale = .33f;
    private Runnable onTap;

    public BackButton(Runnable onTap) {
        this.onTap = onTap;
    }

    @Override
    public void render() {
        Vector3 position = new Vector3(
                uiManager.camera.viewportWidth * uiManager.camera.zoom / 2 - backButton.getWidth() * scale,
                -uiManager.camera.viewportHeight * uiManager.camera.zoom / 2
                , 0);

        uiManager.spriteBatch.draw(backButton, position.x, position.y, backButton.getWidth() * scale, backButton.getHeight() * scale);
    }

    @Override
    public boolean tap() {
        onTap.run();
        ResourceManager.snd("tap.ogg").play();
        return true;
    }

    @Override
    public Rectangle bounds() {
        Vector2 position = new Vector2(
                uiManager.camera.viewportWidth * uiManager.camera.zoom / 2 - backButton.getWidth() * scale,
                -uiManager.camera.viewportHeight * uiManager.camera.zoom / 2);
        return new Rectangle(position.x, position.y, backButton.getWidth() * scale, backButton.getHeight() * scale);
    }
}
