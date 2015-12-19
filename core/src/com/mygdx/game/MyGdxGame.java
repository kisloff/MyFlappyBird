package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

public class MyGdxGame extends ApplicationAdapter {

	public static final int WIDTH = 1520;
	public static final int HEIGHT = 1000;

	private GameStateManager gsm;
	private SpriteBatch batch; //д.б. единственным в игре

	private Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();

		//создаем музыку
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();

		Gdx.gl.glClearColor(1, 1, 1, 1); //достаточно, чтобы цвет был сгенерирован единожды и
		// не перерисоывался все время
		gsm.push(new MenuState(gsm));
	}

	@Override
	//этот метод в постоянном цикле отрисовывает сцену
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime()); //показывает разницу между обновлениями
		gsm.render(batch);
	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}
}
