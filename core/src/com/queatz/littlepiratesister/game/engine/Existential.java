package com.queatz.littlepiratesister.game.engine;

import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.things.Thing;

/**
 * Created by jacob on 1/1/17.
 */

public class Existential {

    // Subjects of concern
    public Thing thing;
    public Vector3 position;
    public Sentiment sentiment;
    public Resources resources;

    public Existential thing(Thing thing) {
        this.thing = thing;

        // Back reference for convenience
        thing.existential = this;

        return this;
    }

    public Existential position(Vector3 position) {
        this.position = new Vector3(position);
        return this;
    }

    public Existential sentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
        return this;
    }

    public Existential resources(Resources resources) {
        this.resources = resources;
        return this;
    }
}
