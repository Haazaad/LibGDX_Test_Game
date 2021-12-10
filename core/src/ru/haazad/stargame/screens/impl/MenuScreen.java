package ru.haazad.stargame.screens.impl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.haazad.stargame.screens.BaseScreen;
import ru.haazad.stargame.sprites.impl.Background;
import ru.haazad.stargame.sprites.buttons.ButtonExit;
import ru.haazad.stargame.sprites.buttons.ButtonPlay;
import ru.haazad.stargame.sprites.Star;
import ru.haazad.stargame.utils.Rect;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private final Game game;

    private Texture bg;

    private Background background;

    private TextureAtlas atlas;
    private Star[] stars;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);

        atlas = new TextureAtlas("textures/menuAtlas.tpack");

        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }
        buttonPlay.resize(worldBounds);
        buttonExit.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star: stars) {
            star.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        buttonPlay.draw(batch);
        buttonExit.draw(batch);
        batch.end();
    }

}
