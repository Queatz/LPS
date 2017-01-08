package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

    private static Color shadowColor = new Color(0, 0, 0, .25f);

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
        write(camera, string, position, true, .5f);
    }

    public static void write(Camera camera, String string, Vector3 position, boolean absolute, float s) {
        camera.sprite().begin();

        if (absolute) {
            position.add(camera.getPosition());
        }

        Color originalColor = new Color(getFont().getColor());

        camera.sprite().setTransformMatrix(new Matrix4().setToTranslationAndScaling(position, new Vector3(s, s, s)));
        getFont().setColor(shadowColor);
        getFont().draw(camera.sprite(), string, 4, -4);
        getFont().setColor(originalColor);
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
