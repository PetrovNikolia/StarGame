package my.game;

import com.badlogic.gdx.Game;

import my.game.screen.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
