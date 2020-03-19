package my.game.pool;

import my.game.base.SpritesPool;
import my.game.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
