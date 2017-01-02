package com.queatz.littlepiratesister.game.engine;

import com.badlogic.gdx.math.Vector2;
import com.queatz.littlepiratesister.game.things.Thing;

/**
 * Created by jacob on 1/1/17.
 */

public class Positional {
    public Thing thing;
    public Vector2 position;

    public Positional(Thing thing) {
        this(thing, new Vector2());
    }

    public Positional(Thing thing, Vector2 position) {
        this.thing = thing;
        this.position = position;
    }
}
