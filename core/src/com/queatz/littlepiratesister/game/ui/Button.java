package com.queatz.littlepiratesister.game.ui;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.FontManager;

import java.util.Date;

/**
 * Created by jacob on 1/4/17.
 */

public class Button extends Element {
    @Override
    public void render() {
        BitmapFont font = FontManager.getFont();
        String text = "Continue";
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);

        float y = (float) Math.sin((new Date().getTime() / 500d) % (Math.PI * 2)) * 1.5f;

        Matrix4 matrix = uiManager.spriteBatch.getTransformMatrix();
        uiManager.spriteBatch.setTransformMatrix(new Matrix4().setToTranslationAndScaling(new Vector3(-y, y, 0), new Vector3(1.5f, 1.5f, 1.5f)));

        font.setColor(0, 0, 0, .25f);
        font.draw(uiManager.spriteBatch, text, -glyphLayout.width / 2 + 5, glyphLayout.height / 2 - 5);

        int restore[] = {uiManager.spriteBatch.getBlendSrcFunc(), uiManager.spriteBatch.getBlendDstFunc()};
        uiManager.spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);

        uiManager.spriteBatch.setTransformMatrix(uiManager.spriteBatch.getTransformMatrix().translate(new Vector3(y, -y, 0)));

        font.setColor(15 / 255f, 255 / 255f, 127 / 255f, 1);
        font.draw(uiManager.spriteBatch, text, -glyphLayout.width / 2, glyphLayout.height / 2);

        uiManager.spriteBatch.setTransformMatrix(matrix);

        uiManager.spriteBatch.setBlendFunction(restore[0], restore[1]);
    }
}
