package my.game.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ScaledButton extends Sprite {

    private static final float PRESS_SCALE = 0.9f;

    private int pointer;
    private boolean pressed;

    public ScaledButton(TextureRegion region) {
        super(region);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        if (pressed || !isMe(touch)) {
            return;
        }
        this.pointer = pointer;
        this.scale = PRESS_SCALE;
        this.pressed = true;
    }

    @Override
    public void touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pressed) {
            return;
        }
        if (isMe(touch)) {
            action();
        }
        pressed = false;
        scale = 1f;
    }

    public abstract void action();
}
