package ru.haazad.stargame.pool.impl;

import com.badlogic.gdx.audio.Sound;

import ru.haazad.stargame.pool.SpritesPool;
import ru.haazad.stargame.sprite.ships.EnemyShip;
import ru.haazad.stargame.sprite.ships.MainShip;
import ru.haazad.stargame.utils.Rect;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private final BulletPool bulletPool;
    private final Sound bulletSound;
    private final Rect worldBounds;
    private final MainShip mainShip;

    public EnemyPool(BulletPool bulletPool, Sound bulletSound, Rect worldBounds, MainShip mainShip) {
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, bulletSound, worldBounds, mainShip);
    }
}
