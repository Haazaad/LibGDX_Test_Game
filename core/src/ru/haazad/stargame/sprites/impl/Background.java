package ru.haazad.stargame.sprites.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.haazad.stargame.sprites.Sprite;
import ru.haazad.stargame.utils.Rect;

public class Background extends Sprite {

    public Background(Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        this.position.set(worldBounds.position);
    }
}
