package my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import my.game.base.Sprite;
import my.game.math.Rect;
import my.game.math.Rnd;

public class Star extends Sprite {

    private static final float STAR_HEIGHT = 0.007f;

    private final Vector2 v;
    private Rect worldBounds;

    private float animateTimer;
    private float animateInterval = 1f;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        v = new Vector2();
        v.set(Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.2f, -0.01f));
        animateTimer = Rnd.nextFloat(0, 1f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
        if (getBottom() > worldBounds.getTop()) {
            setTop(worldBounds.getBottom());
        }
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0;
            setHeightProportion(STAR_HEIGHT);
        } else {
            setHeightProportion(getHeight() + 0.0001f);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(STAR_HEIGHT);
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
        this.worldBounds = worldBounds;
    }
}
