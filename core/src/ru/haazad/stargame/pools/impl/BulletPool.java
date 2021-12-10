package ru.haazad.stargame.pools.impl;

import ru.haazad.stargame.pools.SpritesPool;
import ru.haazad.stargame.sprites.impl.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
