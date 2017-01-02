package com.queatz.littlepiratesister.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jacob on 12/30/16.
 *
 * Handles loading resources.
 */

public class ResourceManager {
    public static Map<String, Texture> images = new HashMap<>();
    public static Map<String, Sound> sounds = new HashMap<>();
    public static Map<String, Music> musics = new HashMap<>();

    public static Texture img(String img) {
        if (images.containsKey(img)) {
            return images.get(img);
        }

        Texture texture = new Texture(img);
        images.put(img, texture);
        return texture;
    }

    public static Music mus(String mus) {
        if (musics.containsKey(mus)) {
            return musics.get(mus);
        }

        Music music = Gdx.audio.newMusic(Gdx.files.internal(mus));
        musics.put(mus, music);
        return music;
    }

    public static Sound snd(String snd) {
        if (sounds.containsKey(snd)) {
            return sounds.get(snd);
        }

        Sound sound = Gdx.audio.newSound(Gdx.files.internal(snd));
        sounds.put(snd, sound);
        return sound;
    }

    public static void dispose() {
        while (!images.isEmpty()) {
            images.remove(images.keySet().iterator().next()).dispose();
        }

        while (!musics.isEmpty()) {
            musics.remove(musics.keySet().iterator().next()).dispose();
        }

        while (!sounds.isEmpty()) {
            sounds.remove(sounds.keySet().iterator().next()).dispose();
        }
    }
}
