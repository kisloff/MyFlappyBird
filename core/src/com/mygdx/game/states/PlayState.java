package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

import java.util.Random;

/**
 * Created by Кирилл on 09.12.2015.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 185; //расстояние между трубами 100
    private static final  int TUBE_COUNT = 4; //количество труб
    private static final int GROUND_Y_OFFSET = -30; //опустить землю вниз на
    private BitmapFont font;

    private Array<Tube> tubes;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2, groundPos3, groundPos0;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        bird = new Bird(50, 300); //создаем новую птичку на этих координатах

        cam.setToOrtho(false, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        cam.zoom -= 0.09f;

        bg = new Texture("bg.png"); //задаем задний фон, он же бекграунд
        ground = new Texture("ground.png");

        groundPos0 = new Vector2((cam.position.x - cam.viewportWidth / 2) - ground.getWidth(), GROUND_Y_OFFSET);
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        groundPos3 = new Vector2((cam.position.x - cam.viewportWidth / 2) + 2 * ground.getWidth(), GROUND_Y_OFFSET);


        tubes = new Array<Tube>();

        Random random = new Random();
        random.nextInt();

        for(int i = 0; i < TUBE_COUNT; i++)
        {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for(int i = 0; i < tubes.size; i++)
        {
            Tube tube = tubes.get(i);

            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            //проверяем птицу на столкновение со всемии трубами
            if(tube.collides(bird.getBounds())) {
                //gsm.set(new PlayState(gsm));//при столкновении игра перезапускается
                System.out.println("Tube collision!!!! " +  Util.score);
                gsm.set(new GameOverState(gsm));
            }

            //проверяем прохождение
            if(tube.scores(bird.getBounds())){
                Util.score++;
                System.out.println("Score!!!! " +  Util.score);
            }
        }

        //проверяем птицу на пролет просвета между трубами
        /*if(){
            //gsm.set(new PlayState(gsm)); //в случае столкновения игра перезапускается == создается новое игровое состояние
            System.out.println("Score!!!!");
        }*/

        //проверяем птицу на столкновение с землей
        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
            //gsm.set(new PlayState(gsm)); //в случае столкновения игра перезапускается == создается новое игровое состояние
            gsm.set(new GameOverState(gsm));
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        font = new BitmapFont();

        sb.setProjectionMatrix(cam.combined); //сделали зум на птицу
        sb.begin();

        //отрисовка фона
        sb.draw(bg, cam.position.x + (cam.viewportWidth / 4), 0); //справа от центра
        sb.draw(bg, cam.position.x, 0); //центральный
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 4), 0); //первый слева//
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0); //второй слева


        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);

        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        //отрисовываем землю
        sb.draw(ground, groundPos0.x, groundPos0.y);
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.draw(ground, groundPos3.x, groundPos3.y);
        font.draw(sb, Util.SCORE + Util.score, cam.position.x - (cam.viewportWidth / 2) + 60 ,
                cam.position.y + (cam.viewportHeight/2) - 40);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        font.dispose();

        for(Tube tube : tubes)
        {
            tube.dispose();
        }
        System.out.println("Play State Disposed");
    }

    private void updateGround(){

        if((cam.position.x - cam.viewportWidth / 2)  > groundPos0.x + ground.getWidth()) {
            groundPos0.add(ground.getWidth(), 0);
            groundPos1.add(ground.getWidth(), 0);
            groundPos2.add(ground.getWidth(), 0);
            groundPos3.add(ground.getWidth(), 0);
        }
    }
}
