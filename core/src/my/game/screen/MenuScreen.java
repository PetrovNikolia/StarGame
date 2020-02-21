package my.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import my.game.base.Basescreen;

public class MenuScreen extends Basescreen {

    private static final float V_LEN=1f;

    private Texture img;
     private Vector2 touth;
     private  Vector2 v;
     private Vector2 pos;
     private Vector2 temp;


    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        touth = new Vector2();
        v = new Vector2();
        pos = new Vector2();
        temp = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        temp.set(touth);
        if (temp.sub(pos).len()> V_LEN){
            pos.add(v);
        } else {
            pos.set(touth);
        }
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        //Опрежеляет куда нажал пользователь
        touth.set(screenX,Gdx.graphics.getHeight()-screenY);
        v.set(touth.cpy().sub(pos).setLength(V_LEN));
        return false;

    }
}
