package com.queatz.littlepiratesister.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.queatz.littlepiratesister.game.ResourceManager;

import java.util.Date;

/**
 * Created by jacob on 1/4/17.
 */

public class Background extends Element {

    private Texture image;

    public Background(String image) {
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
}
