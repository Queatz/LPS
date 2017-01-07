package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.queatz.littlepiratesister.game.SentimentManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Update;

/**
 * Created by jacob on 1/7/17.
 */

public class Target extends Thing {

    private float life = .3f;

    @Override
    public void update(Update update) {
        life -= update.delta;

        if (life <= 0) {
            update.world.delete(this);
        }
    }

    @Override
    public Decal render(Camera camera) {
        SentimentManager.drawSentiment(camera, life * 100, new Color(.25f, .25f, 1, .5f));
        return null;
    }
}
