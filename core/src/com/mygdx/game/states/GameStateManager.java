package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Кирилл on 09.12.2015.
 */
public class GameStateManager {
    private Stack<State> states; //создаем стек возможных состояний программы "пауза", и т.п.

    //создаем новое состояние
    public GameStateManager(){
        states = new Stack<State>();
    }

    //пушим на стек
    public void push(State state){
        states.push(state);
    }

    //извлекаем состояние из стека
    public void pop(){
        states.pop().dispose();
    }

    //задаем текущее состояние?
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    //dt == delta time промежуток времени, через который нужно обновить
    public void update(float dt){
        states.peek().update(dt);
    }

    //отвечает за рендеринг всех изображений на экране в одном "пузыре"
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }


}
