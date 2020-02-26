package my.game.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import my.game.base.Sprite;
import my.game.math.Rect;

public class Logo extends Sprite {

    private static final float V_LEN = 0.01f;

    private Vector2 touch;
    private Vector2 v;
    private Vector2 buf;

    public Logo(Texture region) {
        super(new TextureRegion(region));
        touch = new Vector2();
        v = new Vector2();
        buf = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.4f);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.sub(pos)).setLength(V_LEN);
    }

    @Override
    public void update(float delta) {
        buf.set(touch);
        if (buf.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else {
            pos.set(touch);
        }
    }
}
