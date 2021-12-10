package ru.haazad.stargame.pools.impl;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.haazad.stargame.pools.SpritesPool;
import ru.haazad.stargame.sprites.impl.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private final TextureAtlas atlas;
    private final Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound exposionSound) {
        this.atlas = atlas;
        this.explosionSound = exposionSound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, explosionSound);
    }
}
