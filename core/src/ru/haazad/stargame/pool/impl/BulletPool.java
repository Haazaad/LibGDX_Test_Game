package ru.haazad.stargame.pool.impl;

import ru.haazad.stargame.pool.SpritesPool;
import ru.haazad.stargame.sprite.impl.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
