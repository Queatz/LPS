package com.queatz.littlepiratesister.game.engine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.things.Thing;

/**
 * Created by jacob on 1/1/17.
 */

public class Positional {
    public Thing thing;
    public Vector3 position;

    public Positional(Thing thing) {
        this(thing, new Vector3());
    }

    public Positional(Thing thing, Vector3 position) {
        this.thing = thing;
        this.position = position;
    }
}
