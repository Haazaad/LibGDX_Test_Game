package ru.haazad.stargame.sprite.buttons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.haazad.stargame.screen.impl.GameScreen;
import ru.haazad.stargame.sprite.BaseButton;
import ru.haazad.stargame.utils.Rect;

public class NewGame extends BaseButton {

    private static final float HEIGHT = 0.05f;
    private static final float MARGIN = 0.15f;

    private Game game;

    public NewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
