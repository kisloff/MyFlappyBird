package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

/**
 * Created by Кирилл on 09.12.2015.
 */
public class MenuState extends State {

    private Texture background;//создаем ссылки для объектов для отображения на экране
    private Texture playBtn;//создаем ссылки для объектов для отображения на экране
    private BitmapFont font;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm)); //по нажатию в любое место экрана создаем новое игровое состояние
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        font = new BitmapFont();

        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(background, 0, 0);
        sb.draw(background, background.getWidth(),0);
        sb.draw(background, 2 * background.getWidth(),0);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);
        font.draw(sb, "TAP TO START", cam.position.x - playBtn.getWidth() / 2, cam.position.y + 80);
        sb.end();
    }

    @Override
    public void dispose() { //разместить
        background.dispose(); //разместить фоновый рисунок
        playBtn.dispose(); //разместить кнопку "Играть"
        font.dispose();
        System.out.println("Menu State disposed");
    }
}
