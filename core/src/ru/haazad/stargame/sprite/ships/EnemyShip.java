package ru.haazad.stargame.sprite.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.haazad.stargame.pool.impl.BulletPool;
import ru.haazad.stargame.pool.impl.ExplosionPool;
import ru.haazad.stargame.sprite.Ship;
import ru.haazad.stargame.sprite.impl.Bullet;
import ru.haazad.stargame.utils.Rect;

public class EnemyShip extends Ship {
    private static final Vector2 INITIAL_V = new Vector2(0, -2.2f);
    private static final float INITIAL_SHIFT = 0.01f;

    private final Sound explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

    public EnemyShip(ExplosionPool explosionPool, BulletPool bulletPool, Sound bulletSound, Rect worldBounds) {
        this.explosionPool = explosionPool;
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        this.worldBounds = worldBounds;
        this.v = new Vector2();
        this.v0 = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() > worldBounds.getTop() - INITIAL_SHIFT) {
            position.mulAdd(INITIAL_V, delta);
            reloadTimer = reloadInterval - delta - INITIAL_SHIFT;
        }
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    public void set(TextureRegion[] regions, Vector2 v, TextureRegion bulletRegion, float bulletHeight, Vector2 bulletV, int damage, float reloadInterval, float height, int hp) {
        this.regions = regions;
        this.v.set(v);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV = bulletV;
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < position.y);
    }
}
