package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.engine.Camera;

/**
 * Created by jacob on 1/4/17.
 */

public class FontManager {
    public static BitmapFont font = null;

    public static void init() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("CraftyGirls.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose();
    }

    public static BitmapFont getFont() {
        if (font == null) {
            init();
        }

        return font;
    }

    public static void write(Camera camera, String string, Vector3 position, boolean absolute) {
        camera.sprite().begin();
        getFont().setColor(1, 1, 1, 1);

        if (absolute) {
            position.add(camera.getPosition());
        }

        camera.sprite().setTransformMatrix(new Matrix4().setToTranslationAndScaling(position, new Vector3(.5f, .5f, .5f)));
        getFont().draw(camera.sprite(), string, 0, 0);
        camera.sprite().setTransformMatrix(new Matrix4());
        camera.sprite().end();
    }

    public static void dispose() {
        if (font != null) {
            font.dispose();
            font = null;
        }
    }
}
