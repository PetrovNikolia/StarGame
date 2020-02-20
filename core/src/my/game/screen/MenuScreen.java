package my.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import my.game.base.Basescreen;

public class MenuScreen extends Basescreen {
    private Texture img;
     private Vector2 touth;
     private  Vector2 v;
     private Vector2 pos;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        touth = new Vector2();
        v = new Vector2();
        pos = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (pos.x+ img.getHeight() != touth.x){
            pos.add(v);
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
        touth.sub(pos);
        v=touth.scl(0.01f,0.01f);
        return false;

    }
}
