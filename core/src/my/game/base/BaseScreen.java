package my.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import my.game.math.MatrixUtils;
import my.game.math.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds;
    protected Rect worldBounds;
    private Rect glBounds;

    private Vector2 touch;

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    @Override
    public void show() {
        System.out.println("show");
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);
        touch = new Vector2();
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height = " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        worldBounds.setHeight(1f);
        float aspect = width / (float) height;
        worldBounds.setWidth(1f* aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {
        System.out.println("worldBounds width = " + worldBounds.getWidth() + " height = " + worldBounds.getHeight());
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer, button);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("touchDown touchX = " + touch.x + " touchY = " + touch.y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY = " + screenY);
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer, button);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        System.out.println("touchUp touchX = " + touch.x + " touchY = " + touch.y);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled amount = " + amount);
        return false;
    }
}
