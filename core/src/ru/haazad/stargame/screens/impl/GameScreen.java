package ru.haazad.stargame.screens.impl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.haazad.stargame.fonts.Font;
import ru.haazad.stargame.pools.impl.BulletPool;
import ru.haazad.stargame.pools.impl.EnemyPool;
import ru.haazad.stargame.pools.impl.ExplosionPool;
import ru.haazad.stargame.screens.BaseScreen;
import ru.haazad.stargame.sprites.buttons.ButtonNewGame;
import ru.haazad.stargame.sprites.impl.Background;
import ru.haazad.stargame.sprites.impl.Bullet;
import ru.haazad.stargame.sprites.impl.GameOver;
import ru.haazad.stargame.sprites.impl.TrackingStar;
import ru.haazad.stargame.sprites.ships.EnemyShip;
import ru.haazad.stargame.sprites.ships.MainShip;
import ru.haazad.stargame.sprites.Star;
import ru.haazad.stargame.utils.EnemyEmitter;
import ru.haazad.stargame.utils.Rect;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 128;
    private static final float MARGIN = 0.01f;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private Game game;

    private Texture bg;
    private Background background;
    private GameOver gameOver;
    private ButtonNewGame newGame;

    private ExplosionPool explosionPool;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;

    private TextureAtlas atlas;
    private TrackingStar[] stars;
    private MainShip mainShip;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

    private EnemyEmitter enemyEmitter;

    private int frags;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        gameOver = new GameOver(atlas);
        newGame = new ButtonNewGame(atlas, this);

        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        font = new Font("fonts/font.fnt", "fonts/font.png");
        font.setSize(0.02f);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(explosionPool, bulletPool, bulletSound, worldBounds);

        mainShip = new MainShip(atlas, explosionPool, bulletPool, laserSound);

        stars = new TrackingStar[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new TrackingStar(atlas, mainShip.getV());
        }

        enemyEmitter = new EnemyEmitter(atlas, worldBounds, enemyPool);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();

        frags = 0;
    }

    public void startNewGame() {
        frags = 0;

        mainShip.startNewGame();

        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
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
        newGame.resize(worldBounds);
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
        font.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (mainShip.isDestroyed()) {
            newGame.touchDown(touch, pointer, button);
        } else {
            mainShip.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (mainShip.isDestroyed()) {
            newGame.touchUp(touch, pointer, button);
        } else {
            mainShip.touchUp(touch, pointer, button);
        }
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
            enemyEmitter.generate(delta, frags);
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
                    if (enemyShip.isDestroyed()) {
                        frags++;
                    }
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
            newGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - MARGIN);
        sbHp.setLength(0);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.position.x, worldBounds.getTop() - MARGIN, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - MARGIN, worldBounds.getTop() - MARGIN, Align.right);
    }
}
