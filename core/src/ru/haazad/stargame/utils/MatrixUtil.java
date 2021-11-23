package ru.haazad.stargame.utils;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class MatrixUtil {

    private MatrixUtil() {}

    public static void calcTransitionMatrix(Matrix4 matrix4, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        matrix4.idt().translate(dst.position.x, dst.position.y, 0f).scale(scaleX, scaleY, 1f).translate(-src.position.x, -src.position.y, 0f);
    }

    public static void calcTransitionMatrix(Matrix3 matrix3, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        matrix3.idt().translate(dst.position.x, dst.position.y).scale(scaleX, scaleY).translate(-src.position.x, -src.position.y);
    }
}
