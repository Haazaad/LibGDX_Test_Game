package ru.haazad.stargame.screen.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.haazad.stargame.pool.impl.BulletPool;
import ru.haazad.stargame.pool.impl.EnemyPool;
import ru.haazad.stargame.pool.impl.ExplosionPool;
import ru.haazad.stargame.screen.BaseScreen;
import ru.haazad.stargame.sprite.impl.Background;
import ru.haazad.stargame.sprite.impl.Bullet;
import ru.haazad.stargame.sprite.impl.GameOver;
import ru.haazad.stargame.sprite.ships.EnemyShip;
import ru.haazad.stargame.sprite.ships.MainShip;
import ru.haazad.stargame.sprite.impl.Star;
import ru.haazad.stargame.utils.EnemyEmitter;
import ru.haazad.stargame.utils.Rect;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 128;

    private Texture bg;
    private Background background;
    private GameOver gameOver;

    private ExplosionPool explosionPool;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;

    private TextureAtlas atlas;
    private Star[] stars;
    private MainShip mainShip;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        gameOver = new GameOver(atlas);

        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(explosionPool, bulletPool, bulletSound, worldBounds);

        mainShip = new MainShip(atlas, explosionPool, bulletPool, laserSound);

        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }

        enemyEmitter = new EnemyEmitter(atlas, worldBounds, enemyPool);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        explosionPool.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
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
        for (Star star : stars) {
            star.update(delta);
        }
        if (!mainShip.isDestroyed()) {
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
            mainShip.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
    }

    private void checkCollision() {
        if (mainShip.isDestroyed()) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip: enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = (mainShip.getWidth() + enemyShip.getWidth()) * 0.5f;
            if (mainShip.position.dst(enemyShip.position) < minDist) {
                mainShip.damage(enemyShip.getHp() * 2);
                enemyShip.destroy();
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet: bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for (EnemyShip enemyShip: enemyShipList) {
                if (enemyShip.isDestroyed()) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
    }

    private void freeAllDestroyed() {
        explosionPool.freeAllDestroyed();
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }
}
