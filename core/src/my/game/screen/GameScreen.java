package my.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import my.game.base.BaseScreen;
import my.game.math.Rect;
import my.game.pool.BulletPool;
import my.game.pool.EnemyPool;
import my.game.pool.ExplosionPool;
import my.game.sprite.Background;
import my.game.sprite.Bullet;
import my.game.sprite.Enemy;
import my.game.sprite.MainShip;
import my.game.sprite.MessageGameOver;
import my.game.sprite.NewGame;
import my.game.sprite.Star;
import my.game.sprite.TrackingStar;
import my.game.utils.EnemiesEmitter;
import my.game.utils.Font;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final float FONT_SIZE = 0.02f;
    private static final String FRAGS = "Frags:";
    private static final String HP = "HP:";
    private static final String LEVEL = "Level:";
    private static final float PADDING = 0.01f;


    private TextureAtlas atlas;

    public GameScreen(Game game) {
        this.game = game;
    }

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private Texture bg;
    private Background background;
    private TrackingStar[] stars;

    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private Music music;
    private Sound bulletSound;
    private Sound explosionSound;

    private EnemiesEmitter enemiesEmitter;

    private State state;
    private State pervState;

    private MessageGameOver messageGameOver;

    private NewGame newGame;

    private final Game game;

    private Font font;

    private int frags;

    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;


    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        stars = new TrackingStar[STAR_COUNT];
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, worldBounds);
        enemiesEmitter = new EnemiesEmitter(atlas, enemyPool, worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new TrackingStar(atlas, mainShip.getV());
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        messageGameOver = new MessageGameOver(atlas);
        state = State.PLAYING;
        newGame = new NewGame(atlas, game);
        font = new Font("Font/font.fnt", "Font/font.png");
        font.setSize(FONT_SIZE);
        frags = 0;
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
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
        messageGameOver.resize(worldBounds);
        newGame.resize(worldBounds);
    }

    @Override
    public void pause() {
        music.pause();
        pervState = state;
        state = State.PAUSE;
    }

    @Override
    public void resume() {
        music.play();
        state = pervState;
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
        mainShip.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        }
        newGame.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        }
        newGame.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemiesEmitter.generate(delta, frags);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        List<Enemy> enemyShipList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyShipList) {
            float minDist = mainShip.getHalfWidth() + enemy.getHalfWidth();
            if (mainShip.pos.dst(enemy.pos) <= minDist) {
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
                frags++;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for (Enemy enemy : enemyShipList) {
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()) {
                        frags++;
                    }
                }
            }
        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.5f, 0.9f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            newGame.draw(batch);
        }
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + PADDING, worldBounds.getTop() - PADDING);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - PADDING, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemiesEmitter.getLevel()), worldBounds.getRight() - PADDING, worldBounds.getTop() - PADDING, Align.right);
    }
}
