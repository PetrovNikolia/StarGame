package my.game.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import my.game.base.ScaledButton;
import my.game.math.Rect;
import my.game.screen.GameScreen;

public class NewGame extends ScaledButton {

    private final Game game;

    public NewGame(TextureAtlas atlas,Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.09f);
        setBottom(-0.1f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
