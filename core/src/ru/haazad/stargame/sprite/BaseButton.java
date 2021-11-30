package ru.haazad.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseButton extends Sprite{
    private static final float SCALE = 0.9f;

    private int pointer;
    private boolean isPressed;

    public BaseButton(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int buttom) {
        if (isPressed || !isMe(touch)) {
            return false;
        }
        this.pointer = pointer;
        this.scale = SCALE;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int buttom) {
        if (this.pointer != pointer || !isPressed) {
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        isPressed = false;
        scale = 1f;
        return false;
    }

    public abstract void action();
}
