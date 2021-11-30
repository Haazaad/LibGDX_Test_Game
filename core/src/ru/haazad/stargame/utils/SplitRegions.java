package ru.haazad.stargame.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SplitRegions {

    public static TextureRegion[] split(TextureRegion region, int rows, int cols, int frames) {
        TextureRegion[] regions = new TextureRegion[frames];
        int titleWidth = region.getRegionWidth() / cols;
        int titleHeight = region.getRegionHeight() / rows;

        int frame = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                regions[frame] = new TextureRegion(region, titleWidth * j, titleHeight * i, titleWidth, titleHeight);
                if (frame == frames - 1) {
                    return regions;
                }
                frame++;
            }
        }
        return regions;
    }
}
