package ru.haazad.stargame.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.haazad.stargame.pools.impl.BulletPool;
import ru.haazad.stargame.pools.impl.ExplosionPool;
import ru.haazad.stargame.sprites.impl.Bullet;
import ru.haazad.stargame.sprites.impl.Explosion;
import ru.haazad.stargame.utils.Rect;

public class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected Vector2 v;
    protected Vector2 v0;

    protected ExplosionPool explosionPool;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;
    protected Sound bulletSound;

    protected Rect worldBounds;

    protected int hp;

    protected float reloadTimer;
    protected float reloadInterval;

    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        position.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0;
            shoot();
        }
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        damageAnimateTimer = 0f;
        frame = 1;
    }

    public int getHp() {
        return hp;
    }

    public Vector2 getV() {
        return v;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, position, bulletV, bulletHeight, worldBounds, damage);
        bulletSound.play();
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(position, getHeight());
    }
}
