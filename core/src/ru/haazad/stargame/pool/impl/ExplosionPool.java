package ru.haazad.stargame.pool.impl;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.haazad.stargame.pool.SpritesPool;
import ru.haazad.stargame.sprite.impl.Explosion;

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
