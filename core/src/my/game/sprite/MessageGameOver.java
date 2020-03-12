package my.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import my.game.base.Sprite;
import my.game.math.Rect;

public class MessageGameOver extends Sprite {

    public MessageGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.09f);
        setTop(0.1f);
    }
}
