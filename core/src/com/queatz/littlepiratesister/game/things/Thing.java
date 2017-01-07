package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Existential;
import com.queatz.littlepiratesister.game.engine.Update;

import java.util.Random;

/**
 * Created by jacob on 1/1/17.
 */

public class Thing {
    public Integer id = new Random().nextInt();
    public Existential existential;

    public void update(Update update) {}
    public Decal render(Camera camera) { return null; }
}
