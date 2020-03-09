package my.game.pool;

import com.badlogic.gdx.audio.Sound;

import my.game.base.SpritesPool;
import my.game.math.Rect;
import my.game.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Sound shootSound;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, shootSound, worldBounds);
    }
}
