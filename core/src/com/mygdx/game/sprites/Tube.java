package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Кирилл on 09.12.2015.
 */
public class Tube {
    public static final int TUBE_WIDTH = 52;

    private static final int FLUCTUATION = 130;//трубы могут "ходить" на 130 вверх-вниз
    private static final int TUBE_GAP = 180; //расстояние между трубами 100
    private static final int LOWEST_OPENING = 120;

    private Texture topTube, bottomTube, testTube;
    private Vector2 posTopTube, posBotTube;
    private Random random;

    private Rectangle boundsTop, boundsBot, boundsMid; //делаем невидимые прямоугольнки для фиксации столкновений

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Tube(float x){
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        random = new Random();

        posTopTube = new Vector2(x, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        //создаем невидимые прямоугольники по размерам верхних и нижних
        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
        //невидимый прямоугольник для фиксации факта пролета между трубами
        boundsMid = new Rectangle(posTopTube.x, posTopTube.y - TUBE_GAP , 1 , TUBE_GAP);

    }

    public Tube(int x, int y){
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        posTopTube = new Vector2(x, y);
    }


    public void reposition(float x){
        //перенос труб
        posTopTube.set(x, random.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        //перенос прямоугольников
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsMid.setPosition(posTopTube.x, posTopTube.y - TUBE_GAP);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public boolean scores(Rectangle player){

        return player.overlaps(boundsMid);
    }

    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }
}
