package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Кирилл on 12.12.2015.
 */
public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime; //как долго фрейм должен быть виден
    private float currentFrameTime; // как долго анимация должна быть в конкретном фрейме
    private int frameCount; //количество фреймов
    private int frame; //фрейм, в котором находимся в данный момент

    public Animation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;

        //смотри картиеку birdanimation
        //отображение фрейма начинается с нижнего левого угла, где координата 0, 0
        //первый проход 0*frameWidth => отображается первая часть картинки
        //второй проход 1*frameWidth => отображается вторая часть картинки

        for(int i=0; i< frameCount; i++)
        {
            frames.add(new TextureRegion(region, i* frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;

    }

    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount)
            frame = 0;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
