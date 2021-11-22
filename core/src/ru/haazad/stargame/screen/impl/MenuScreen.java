package ru.haazad.stargame.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.haazad.stargame.screen.BaseScreen;

public class MenuScreen extends BaseScreen {
    private static float SPEED = 2f;

    private Texture img;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 direction;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        position = new Vector2();
        velocity = new Vector2(1, 1);
        direction = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        move();
        batch.draw(img, position.x, position.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        direction.set(screenX - (float) img.getWidth() / 2, Gdx.graphics.getHeight() - screenY - (float) img.getHeight() / 2);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    private void move() {
        velocity = direction.cpy().sub(position).nor().scl(SPEED);
        position.add(velocity);
    }

}
