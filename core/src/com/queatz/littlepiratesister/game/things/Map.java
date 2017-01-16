package com.queatz.littlepiratesister.game.things;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.queatz.littlepiratesister.game.ResourceManager;
import com.queatz.littlepiratesister.game.engine.Camera;
import com.queatz.littlepiratesister.game.engine.Update;

import java.util.Random;

/**
 * Created by jacob on 12/30/16.
 */

public class Map extends Thing {

    private static int[][] map = {
        {1002,   1002,   1002,   1003,   0,   0,   1001,   1002},
        {1002,   1003,   0,   0,   0,   4,   1001,   1002},
        {1002,   1003,   0,   0,   0,   0,   1001,   1002},
        {1002,   1003,   0,   0,   0,   0,   1001,   1002},
        {1002,   1003,   0,   4,   0,   0,   1001,   1002},
        {1002,   1003,   0,   0,   0,   0,   1001,   1002},
        {1002,   1003,   0,   0,   0,   0,   1001,   1002},
        {1002,   1003,   0,   0,   1001,   1002,   1002,   1002},
    };

    private static int tileSize = 64;
    private static float renderSize = 256;
    private static float mapWidth = 8;
    private static float mapHeight = 8;
    private static float mapOffset = -4 * renderSize;
    private static Pixmap pixmap;

    @Override
    public void update(Update update) {

    }

    @Override
    public Decal render(Camera camera) {
        Texture image = ResourceManager.img("tiles.png");

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {

                int t = map[y][x];

                if (t < 1) {
                    continue;
                }

                int tx = (t - 1) % 1000;
                int ty = (t - 1) / 1000;

                TextureRegion region = new TextureRegion(image);
                region.setRegion(tx * tileSize, ty * tileSize, tileSize, tileSize);
                camera.sprite().draw(region, x * renderSize + mapOffset, y * renderSize + mapOffset, renderSize, renderSize);
            }
        }

        return null;
    }

    public Color color(Vector3 at) {
        float ax = at.x - mapOffset;
        float ay = at.y - mapOffset;

        int x = (int) Math.floor(ax / renderSize);
        int y = (int) Math.floor(ay / renderSize);

        if (y < 0 || y >= mapHeight || x < 0 || x >= mapWidth) {
            return new Color(0);
        }

        int t = map[y][x];
        int tx = (t - 1) % 1000;
        int ty = (t - 1) / 1000;

        if (t < 1) {
            return new Color(0);
        }

        float xo = ((ax - x * renderSize) / renderSize);
        float yo = 1 - ((ay - y * renderSize) / renderSize);

        return new Color(pixmap().getPixel(
                (int) (tx * tileSize + tileSize * xo),
                (int) (ty * tileSize + tileSize * yo))
        );
    }

    private Pixmap pixmap() {
        if (pixmap != null) {
            return pixmap;
        }

        Texture image = ResourceManager.img("tiles.png");

        if (!image.getTextureData().isPrepared()) {
            image.getTextureData().prepare();
        }

        pixmap = image.getTextureData().consumePixmap();

        return pixmap;
    }
}
