package ru.haazad.stargame.sprites.buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.haazad.stargame.screens.impl.GameScreen;
import ru.haazad.stargame.sprites.BaseButton;
import ru.haazad.stargame.utils.Rect;

public class ButtonNewGame extends BaseButton {

    private static final float HEIGHT = 0.05f;
    private static final float MARGIN = 0.15f;

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
