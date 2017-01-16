package com.queatz.littlepiratesister.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.FontManager;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.SceneManager;
import com.queatz.littlepiratesister.game.SentimentManager;
import com.queatz.littlepiratesister.game.scenes.GameScene;
import com.queatz.littlepiratesister.game.scenes.OverworldScene;
import com.queatz.littlepiratesister.game.ui.Element;

import java.util.Random;

/**
 * Created by jacob on 1/16/17.
 */

public class TownButton extends Element {

    private String name;
    private Vector2 position;

    private Color sentimentColor = new Color(15 / 255f, 255 / 255f, 127 / 255f, 1);
    private float sentiment = new Random().nextInt(30) * (new Random().nextInt(2) == 0 ? -1 : 1);

    public TownButton(String name, Vector2 position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public void render() {
        Texture image = ResourceManager.img("house.png");

        float x = uiManager.camera.viewportWidth / 2 * uiManager.camera.zoom * position.x;
        float y = uiManager.camera.viewportHeight / 2 * uiManager.camera.zoom * position.y;

        SentimentManager.drawSentiment(uiManager.spriteBatch, sentiment, x, y + 10, sentimentColor);

        uiManager.spriteBatch.draw(image,
                x - image.getWidth() / 2 / 2,
                y,
                image.getWidth() / 2,
                image.getHeight() / 2);

        BitmapFont font = FontManager.getFont();
        GlyphLayout glyphLayout = new GlyphLayout(font, name);

        Matrix4 matrix = new Matrix4(uiManager.spriteBatch.getTransformMatrix());
        uiManager.spriteBatch.setTransformMatrix(new Matrix4().setToTranslationAndScaling(new Vector3(x, y, 0), new Vector3(.25f, .25f, 1f)));

        font.setColor(new Color(0, 0, 0, .25f));
        font.draw(uiManager.spriteBatch, name, -glyphLayout.width / 2 + 3, 0 - 3);

        font.setColor(new Color(1, 1, 1, 1));
        font.draw(uiManager.spriteBatch, name, -glyphLayout.width / 2, 0);

        uiManager.spriteBatch.setTransformMatrix(matrix);
    }

    @Override
    public boolean tap() {
        SceneManager.set(new GameScene());
        return true;
    }

    @Override
    public Rectangle bounds() {
        float s = 80;
        float x = uiManager.camera.viewportWidth / 2 * uiManager.camera.zoom * position.x;
        float y = uiManager.camera.viewportHeight / 2 * uiManager.camera.zoom * position.y;

        return new Rectangle(x - s / 2, y - s / 2, s, s);
    }
}
