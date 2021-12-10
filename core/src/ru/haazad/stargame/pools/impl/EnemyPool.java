package ru.haazad.stargame.pools.impl;

import com.badlogic.gdx.audio.Sound;

import ru.haazad.stargame.pools.SpritesPool;
import ru.haazad.stargame.sprites.ships.EnemyShip;
import ru.haazad.stargame.utils.Rect;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private final ExplosionPool explosionPool;
    private final BulletPool bulletPool;
    private final Sound bulletSound;
    private final Rect worldBounds;

    public EnemyPool(ExplosionPool explosionPool, BulletPool bulletPool, Sound bulletSound, Rect worldBounds) {
        this.explosionPool = explosionPool;
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(explosionPool, bulletPool, bulletSound, worldBounds);
    }
}
