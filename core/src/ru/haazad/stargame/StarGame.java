package ru.haazad.stargame;

import com.badlogic.gdx.Game;

import ru.haazad.stargame.screens.impl.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
