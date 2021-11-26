package ru.haazad.stargame.sprite.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.haazad.stargame.sprite.Sprite;
import ru.haazad.stargame.utils.Rect;

public class Logo extends Sprite {

    private static final float HEIGHT = 0.15f;
    private static final float V_LEN = 0.01f;

    private final Vector2 dest;
    private final Vector2 v;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        this.dest = new Vector2();
        this.v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (position.dst(dest) > V_LEN) {
            position.add(v);
        } else {
            position.set(dest);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int buttom) {
        this.dest.set(touch);
        v.set(dest.cpy().sub(position)).setLength(V_LEN);
        return false;
    }
}
