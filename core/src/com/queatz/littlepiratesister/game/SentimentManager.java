package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.queatz.littlepiratesister.game.engine.Camera;

/**
 * Created by jacob on 1/2/17.
 */

public class SentimentManager {
    public static void drawSentiment(Camera camera, float sentiment, Color color) {
        Texture image = ResourceManager.img("sentimental.png");
        float s = (float) Math.sqrt(Math.abs(sentiment) * 1000f);

        SpriteBatch draw = camera.sprite();
        draw.setColor(color);

        int restore[] = {draw.getBlendSrcFunc(), draw.getBlendDstFunc()};
        draw.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        if (sentiment < 0) {
            Gdx.gl.glBlendEquation(GL20.GL_FUNC_REVERSE_SUBTRACT);
        } else {
            Gdx.gl.glBlendEquation(GL20.GL_FUNC_ADD);
        }
        draw.draw(image, camera.getRef().x + (s / -2), camera.getRef().y + (s / -2), s, s);
        draw.setColor(Color.WHITE);
        draw.setBlendFunction(restore[0], restore[1]);

        Gdx.gl.glBlendEquation(GL20.GL_FUNC_ADD);
    }

    public static void drawSentiment(SpriteBatch draw, float sentiment, float x, float y, Color color) {
        Texture image = ResourceManager.img("sentimental.png");
        float s = (float) Math.sqrt(Math.abs(sentiment) * 1000f);

        draw.setColor(color);

        int restore[] = {draw.getBlendSrcFunc(), draw.getBlendDstFunc()};
        draw.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        if (sentiment < 0) {
            Gdx.gl.glBlendEquation(GL20.GL_FUNC_REVERSE_SUBTRACT);
        } else {
            Gdx.gl.glBlendEquation(GL20.GL_FUNC_ADD);
        }
        draw.draw(image, x + (s / -2), y + (s / -2), s, s);
        draw.setColor(Color.WHITE);
        draw.setBlendFunction(restore[0], restore[1]);

        Gdx.gl.glBlendEquation(GL20.GL_FUNC_ADD);
    }
}
