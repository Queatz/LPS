package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Update;

/**
 * Created by jacob on 12/30/16.
 */

public class Encampment extends Thing {

    @Override
    public void update(Update update) {
        if (this.existential.sentiment.value < 0) {
            update.game.getPlayer2AI().resources.money += Math.abs(this.existential.sentiment.value) * update.delta / 5; // 2X more resources...
        } else if (this.existential.sentiment.value > 0) {
            update.game.getPlayerAI().resources.money += this.existential.sentiment.value * update.delta / 10;
        }
    }

    @Override
    public Decal render(Camera camera) {
        Texture image = ResourceManager.img("house.png");
        Decal sprite = Decal.newDecal(new TextureRegion(image), true);
        sprite.setRotationX(45);
        sprite.setScale(.5f);
        return sprite;
    }
}
