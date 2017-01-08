package com.queatz.littlepiratesister.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.SceneManager;
import com.queatz.littlepiratesister.game.scenes.GameScene;

import java.util.Date;

/**
 * Created by jacob on 1/4/17.
 */

public class ScrollingWaterBackground extends Element {

    private Texture image;

    public ScrollingWaterBackground(String image) {
        this.image = ResourceManager.img(image);
        this.image.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.image.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void render() {
        float t = (float) (((double) new Date().getTime() / 50000d) % 1d);

        float aspect = uiManager.camera.viewportHeight / uiManager.camera.viewportWidth;
        TextureRegion region = new TextureRegion(image, 0f, 0f - t, 1f, 1f * aspect - t);

        float w = uiManager.camera.viewportWidth;
        float h = uiManager.camera.viewportHeight;

        uiManager.spriteBatch.draw(region, -w / 2, -h / 2, w, h);
    }

    @Override
    public boolean tap() {
        SceneManager.set(new GameScene());
        return true;
    }

    @Override
    public Rectangle bounds() {
        float w = uiManager.camera.viewportWidth;
        float h = uiManager.camera.viewportHeight;

        return new Rectangle(-w / 2, -h / 2, w, h);
    }
}
