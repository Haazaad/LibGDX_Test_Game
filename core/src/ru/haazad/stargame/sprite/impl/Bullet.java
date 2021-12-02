package ru.haazad.stargame.sprite.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.haazad.stargame.sprite.Sprite;
import ru.haazad.stargame.utils.Rect;

public class Bullet extends Sprite {

    private final Vector2 v = new Vector2();

    private Rect worldBounds;
    private int damage;
    private Sprite owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    public void set(Sprite owner, TextureRegion region, Vector2 position, Vector2 v, float height, Rect worldBounds, int damage) {
        this.owner = owner;
        this.regions[0] = region;
        this.position.set(position);
        this.v.set(v);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        this.position.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }
}
