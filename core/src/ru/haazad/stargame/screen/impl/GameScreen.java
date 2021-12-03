package ru.haazad.stargame.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.haazad.stargame.pool.impl.BulletPool;
import ru.haazad.stargame.pool.impl.EnemyPool;
import ru.haazad.stargame.screen.BaseScreen;
import ru.haazad.stargame.sprite.impl.Background;
import ru.haazad.stargame.sprite.ships.MainShip;
import ru.haazad.stargame.sprite.impl.Star;
import ru.haazad.stargame.utils.EnemyEmitter;
import ru.haazad.stargame.utils.Rect;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 128;

    private Texture bg;
    private Background background;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;

    private TextureAtlas atlas;
    private Star[] stars;
    private MainShip mainShip;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;

    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);

        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));

        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool,bulletSound, worldBounds);

        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        mainShip = new MainShip(atlas, bulletPool, laserSound);

        enemyEmitter = new EnemyEmitter(atlas, worldBounds, enemyPool);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star: stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        mainShip.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    private void update(float delta) {
        for (Star star: stars) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
        mainShip.update(delta);
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star: stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }
}
