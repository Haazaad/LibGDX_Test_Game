package ru.haazad.stargame.sprite.impl;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.haazad.stargame.sprite.Sprite;
import ru.haazad.stargame.utils.Rect;

public class MainShip extends Sprite {

    private static final float HEIGHT = 0.1f;
    private static final float MARGIN = 0.03f;

    private Rect worldBounds;

    private Vector2 v;
    private Vector2 vZero;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.v = new Vector2();
        this.vZero = new Vector2(0.5f, 0f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void update(float delta) {
        position.mulAdd(v, delta);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.position.x) {
            moveLeft();
        } else {
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        stop();
        return false;
    }



    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                moveRight();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                stop();
                break;
        }
        return false;
    }

    public void moveLeft() {
        v.set(vZero).rotateDeg(180);
    }

    public void moveRight() {
        v.set(vZero);
    }

    public void stop() {
        v.setZero();
    }
}
