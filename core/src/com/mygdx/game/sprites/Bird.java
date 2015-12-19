package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Кирилл on 09.12.2015.
 */
public class Bird {

    private static final int GRAVITY = -7; //задаем величину гравитации -15
    private static final int MOVEMENT = 120; //задаем движение птицы по горизонтали
    private Vector3 position; //зададим координаты в трехмерном мире; поскольку у нас двумерный,
    // используем только две переменных
    private Vector3 velocity; //скорость
    private Rectangle bounds; //рамка птички
    private Animation birdAnimation;

    private Texture texture ;
    private Sound flap;

    public Bird(int x, int y){
        position = new Vector3(x, y, 0); //задаем начальгую позицию птицы
        velocity = new Vector3(0, 0, 0); //задаем начальную скорость птицы

       // bird = new Texture("bird.png"); //задаем статичное изображение птицы
        texture = new Texture("birdanimation.png");

        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);

        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight()); ////задаем рамку вокруг птички
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));

    }

    public void update(float dt){
        birdAnimation.update(dt);
        if(position.y > 0) {
            velocity.add(0, GRAVITY, 0); //птица падает вниз
        }
        velocity.scl(dt); //scl == scale /делим scl на dt
        position.add(MOVEMENT * dt, velocity.y, 0);

        if(position.y < 0)
        {
            position.y = 0;
        }

        velocity.scl(1/dt); //обращаем scl, чтобы снова можно было делить на dt в след итерации
        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public void jump(){
        velocity.y = 180; //задаем величину "прыжка" птицы 250
        flap.play(0.5f);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose()
    {
        texture.dispose();
        flap.dispose();
    }

}
