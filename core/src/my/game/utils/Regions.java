package my.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Regions {
    public static TextureRegion[] split(TextureRegion region, int rows, int cols, int frames){
        if(region==null) throw new RuntimeException("Split null region");
        TextureRegion[] regions = new TextureRegion[frames];
        int titleWidth = region.getRegionWidth()/ cols;
        int titleHeigth = region.getRegionHeight()/ rows;


        int frame= 0;
        for (int i = 0; i <rows ; i++) {
            for (int j = 0; j <cols ; j++) {
                regions[frame] = new TextureRegion(region,titleWidth*j,titleHeigth*i,titleWidth,titleHeigth);
                if(frame==frames-1) return regions;
                frame++;

            }
        }
        return regions;
    }

}
