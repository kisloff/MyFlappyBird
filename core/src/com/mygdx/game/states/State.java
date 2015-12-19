package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Кирилл on 08.12.2015.
 */
public abstract class State {
    protected OrthographicCamera cam; //часть игорового мира, видимая игроку
    protected Vector3 mouse; //
    protected GameStateManager gsm; //класс, управлящий состояниями игры

    protected State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
        cam.setToOrtho(false, 240, 400);//задаем параметры камеры

    }

    protected abstract void handleInput();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();
}
