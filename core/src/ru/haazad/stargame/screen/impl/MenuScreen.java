package ru.haazad.stargame.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.haazad.stargame.screen.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture img;
    private Vector2 touch;
    private Vector2 v;
    private Vector2 position;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        touch = new Vector2();
        v = new Vector2(1, 1);
        position = new Vector2(0, 0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(img, touch.x, touch.y);
        batch.end();
        if (touch != position) {
            touch.set(position);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        position.set(screenX - img.getWidth() / 2, Gdx.graphics.getHeight() - screenY - img.getHeight() / 2);
        return super.touchDown(screenX, screenY, pointer, button);
    }

}
