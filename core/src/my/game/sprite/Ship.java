package my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import my.game.base.Sprite;
import my.game.math.Rect;

public class Ship extends Sprite {

    private Vector2 touch;
    private Vector2 v;

    private Vector2 v0 = new Vector2(0.5f, 0);


    public Ship(TextureRegion regions) {
        super(regions);
        touch = new Vector2();
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.2f);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
    }

    public void moveRight() {
        v.set(v0);
    }

    public void moveLeft() {
        v.set(v0).rotate(180);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if(touch.x < 0) {
            moveLeft();
        } else {
            moveRight();
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer, int button) {
        super.touchUp(touch, pointer, button);
        stopMove();
    }

    private void stopMove() {
        v.setZero();
    }
}
