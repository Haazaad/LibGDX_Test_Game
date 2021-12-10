package ru.haazad.stargame.sprites.impl;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.haazad.stargame.sprites.Sprite;
import ru.haazad.stargame.utils.Rect;

public class GameOver extends Sprite {

    private static final float HEIGHT = 0.08f;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
    }
}
