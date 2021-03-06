package ru.haazad.stargame.sprites.buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.haazad.stargame.screens.impl.GameScreen;
import ru.haazad.stargame.sprites.BaseButton;
import ru.haazad.stargame.utils.Rect;

public class ButtonPlay extends BaseButton {

    private static final float HEIGHT = 0.25f;
    private static final float MARGIN = 0.03f;

    private final Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setLeft(worldBounds.getLeft() + MARGIN);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
