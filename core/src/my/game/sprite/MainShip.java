package my.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import my.game.base.Sprite;
import my.game.math.Rect;
import my.game.math.Rnd;
import my.game.pool.BulletPool;

public class MainShip extends Sprite {

    private static final int INVALID_POINTER = -1;

    private Vector2 v = new Vector2();
    private Vector2 v0 = new Vector2(0.5f, 0) ;

    private Rect worldBounds;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private Vector2 billetPos;

    private boolean presetLeft;
    private boolean presetRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private float animateTimer;
    private float animateInterval = 0.1f;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"),1,2,2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletV = new Vector2(0, 0.5f);
        this.billetPos=new Vector2();
        this.animateTimer = Rnd.nextFloat(0, 1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + 0.05f);
        this.worldBounds= worldBounds;

    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if(getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
            stopMove();
        }
        if(getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stopMove();
        }
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0;
            shoot();
        }

    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x < worldBounds.pos.x){
            if(leftPointer!= INVALID_POINTER){
                return;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if(rightPointer!= INVALID_POINTER){
                return;
            }
            rightPointer = pointer;
            moveRight();
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer, int button) {
        if(pointer == leftPointer){
            leftPointer=INVALID_POINTER;
            if(rightPointer!= INVALID_POINTER){
                moveRight();
            } else {
                stopMove();
            }
        } else if(pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if(leftPointer != INVALID_POINTER){
                moveLeft();
            } else {
                stopMove();
            }
        }
    }

    public void keyDown(int keyCode){
        switch (keyCode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                presetLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                presetRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
        }
    }
    public void keyUp(int keyCode){
        switch (keyCode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                presetLeft = false;
                if(presetRight){
                    moveRight();
                } else {
                    stopMove();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                presetRight = false;
                if(presetLeft){
                    moveLeft();
                } else {
                    stopMove();
                }
                break;
        }
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stopMove() {
        v.setZero();
    }

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        billetPos.set(pos.x, pos.y+halfHeight);
        bullet.set(this,bulletRegion, billetPos ,bulletV,0.01f,worldBounds,1 );
    }
}
