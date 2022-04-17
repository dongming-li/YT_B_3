package com.mygdx.creepycaverns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayGame implements Screen {

    final CreepyCaverns game;

    private Texture texture;
    private SpriteBatch batch;
    private TextureRegion[][] regions;
    private char[][] map;
    private Character[][] charMap;
    private PlayerCharacter pc;
    private int pcScreenX=32;//centers on player's tile
    private int pcScreenY=-32;//centers on player's tile
    private OrthographicCamera camera;
    private int counter=0;

    public PlayGame(final CreepyCaverns game) {
        this.game = game;
    }

    @Override
    public void show() {

        System.out.println("Check PlayGame");

        batch = new SpriteBatch();
        //CreepyCaverns.png can be modified to add additional content
        texture = new Texture("CreepyCaverns.png");
        regions=TextureRegion.split(texture,64,64);
        camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        if(game.inter.getEdit()) {
        }else{
            game.inter.setMap(game.inter.getMAPID());
        }
        System.out.println("Check PlayMapID: " + game.inter.getMAPID());
        map = game.inter.getMap();

        //test map, replace with function/retrieved map
        // map=new char[][]{
                // new char[] {'i', 'r', 'r', 'r', 'r'},
                // new char[] {'i', 'r', 'r', 'i', 'i'},
                // new char[] {'i', 'r', 's', 'i', 'i'},
                // new char[] {'i', 'r', 'i', 'r', 'i'},
                // new char[] {'i', 'r', 'r', 'r', 'f'},
                // new char[] {'i', 'i', 'r', 'r', 'i'}
        // };
        //gonna try moving starting position around to affect map setup.
        int startingX=0;
        int startingY=0;
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(map[i][j]=='s'){
                    pcScreenX+=(j*64);
                    startingX=j;
                    pcScreenY-=(i*64);
                    startingY=i;
                }
            }
        }
        //initialize camera
        camera.zoom+=-0.7;
        camera.position.set(pcScreenX,pcScreenY,0);
        //initialize player
        pc = new PlayerCharacter(startingX,startingY);
        //initialize charMap, will need to be replaced by function/fetched map.
        charMap=new Character[map.length][map[0].length];
        char[][] monsterCharMap = game.inter.getMonsters();
        for (int i = 0; i < map.length; i++)
        {
            for (int j = 0; j < map[0].length; j++)
            {
                if(monsterCharMap[i][j] == 'm')
                {
                    charMap[i][j] = new NonPlayerCharacter(j, i);
                    System.out.println("Message: Placing mummy at " + i + " " + j);
                }
                else if(monsterCharMap[i][j] == 'z')
                {
                    charMap[i][j] = new ZombieNPC(j, i);
                    System.out.println("Message: Placing zombie at " + i + " " + j);
                }
            }
        }
        charMap[startingY][startingX] = pc;
        //charMap[1][1] = new ZombieNPC(1, 1);
    }

    @Override
    public void render(float delta) {
        counter++;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //set up the camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //dead code, but keeping for possible testing later
        int yOffset=0;
        int xOffset=0;
        //rather than rendering the whole map each time, could render once then modify only the previous tiles and current tiles players move on.
        for(int i=0;i<map.length;i++) {
            for (int j = 0; j <map[0].length; j++) {
                if(j >= 0 && j <= map[0].length-1 && i >= 0 && i <= map.length-1&&pc.getY()-7<i&&pc.getY()+7>i&&pc.getX()-5<j&&pc.getX()+5>j) {//modify the bounds so only printed within a certain range of pc.
                    switch (map[i][j]) {
                        case 'r'://rooms
                            batch.draw(regions[0][0], (j + xOffset) * 64, (i + yOffset) * 64);
                            break;
                        case 'h'://hallway
                            break;
                        case 't'://traps
                            break;
                        case 'w'://water
                            batch.draw(regions[1][0],(j + xOffset) * 64, (i + yOffset) * 64);
                            break;
                        case 's'://start tile
                            batch.draw(regions[0][1], (j + xOffset) * 64, (i + yOffset) * 64);
                            break;
                        case 'f'://end tile
                            batch.draw(regions[0][2], (j + xOffset) * 64, (i + yOffset) * 64);
                            break;
                        case 'i'://impassable terrain
                            batch.draw(regions[0][3], (j + xOffset) * 64, (i + yOffset) * 64);
                        default://default is to make impassable tile
                            batch.draw(regions[0][3],(j + xOffset) * 64, (i + yOffset) * 64 );
                    }
                }
                if(counter%50<25) {//animations layer
                    //monster layer
                    if (charMap[i][j] instanceof NonPlayerCharacter && !charMap[i][j].isPc()) {
                        batch.draw(regions[2][4], (j + xOffset) * 64, (i + yOffset) * 64);
                        //hit animation
                        if(charMap[i][j].isHit()){
                            batch.draw(regions[1][8], (j + xOffset) * 64, (i + yOffset) * 64);
                            charMap[i][j].resetHit();
                        }
                        if (counter % 50 == 0 && !charMap[i][j].hasMoved()) {
                            charMap[i][j].setHasMoved(true);
                            charMap[i][j].attemptMove(0, 0, map, charMap);
                        } else {
                            charMap[i][j].setHasMoved(false);
                        }
                    }
                    if (charMap[i][j] instanceof ZombieNPC && !charMap[i][j].isPc()) {
                        batch.draw(regions[2][5], (j + xOffset) * 64, (i + yOffset) * 64);
                        //hit animation
                        if(charMap[i][j].isHit()){
                            batch.draw(regions[1][8], (j + xOffset) * 64, (i + yOffset) * 64);
                            charMap[i][j].resetHit();
                        }
                        if (counter % 50 == 0 && !charMap[i][j].hasMoved()) {
                            charMap[i][j].setHasMoved(true);
                            charMap[i][j].attemptMove(0, 0, map, charMap);
                        } else {
                            charMap[i][j].setHasMoved(false);
                        }
                    }

                    if (pc.getX() == j && pc.getY() == i) {//display character
                        if (pc.getHealth() <= 0) {
                            game.setScreen(new Lose(game));
                            dispose();
                        }
                        if (map[i][j] == 'f') {
                            if(game.inter.getEdit()==true){
                                System.out.println("Message: Edit " + game.inter.getEdit());
                                game.setScreen(new MapInfo(game));
                                dispose();
                            }else if(game.inter.getEdit()==false) {
                                System.out.println("Message: Win " + game.inter.getEdit());
                                game.setScreen(new Win(game));
                                dispose();
                            }
                        }
                        batch.draw(regions[0][4], (j + xOffset) * 64, (i + yOffset) * 64);
                        //hit animation
                        if(pc.isHit()){
                            batch.draw(regions[1][8], (j + xOffset) * 64, (i + yOffset) * 64);
                            pc.resetHit();
                        }
                    } else if (pc.getX() + 1 == j && pc.getY() == i) {//to the right
                        batch.draw(regions[0][9], (j + xOffset) * 64, (i + yOffset) * 64);
                    } else if (pc.getX() - 1 == j && pc.getY() == i) {//to the left
                        batch.draw(regions[1][9], (j + xOffset) * 64, (i + yOffset) * 64);
                    } else if (pc.getX() == j && pc.getY() - 1 == i) {//up
                        batch.draw(regions[2][9], (j + xOffset) * 64, (i + yOffset) * 64);
                    } else if (pc.getX() == j && pc.getY() + 1 == i) {//down
                        batch.draw(regions[3][9], (j + xOffset) * 64, (i + yOffset) * 64);
                    }
                }else if(counter%50>=25){//second set of animations.
                    //monster layer
                    if (charMap[i][j] instanceof NonPlayerCharacter && !charMap[i][j].isPc()) {
                        batch.draw(regions[3][4], (j + xOffset) * 64, (i + yOffset) * 64);
                        //hit animation
                        if(charMap[i][j].isHit()){
                            batch.draw(regions[1][8], (j + xOffset) * 64, (i + yOffset) * 64);
                            charMap[i][j].resetHit();
                        }
                        if (counter % 50 == 0 && !charMap[i][j].hasMoved()) {
                            charMap[i][j].setHasMoved(true);
                            charMap[i][j].attemptMove(0, 0, map, charMap);
                        } else {
                            charMap[i][j].setHasMoved(false);
                        }

                    }
                    if (charMap[i][j] instanceof ZombieNPC && !charMap[i][j].isPc()) {
                        batch.draw(regions[3][5], (j + xOffset) * 64, (i + yOffset) * 64);
                        //hit animation
                        if(charMap[i][j].isHit()){
                            batch.draw(regions[1][8], (j + xOffset) * 64, (i + yOffset) * 64);
                            charMap[i][j].resetHit();
                        }
                        if (counter % 50 == 0 && !charMap[i][j].hasMoved()) {
                            charMap[i][j].setHasMoved(true);
                            charMap[i][j].attemptMove(0, 0, map, charMap);
                        } else {
                            charMap[i][j].setHasMoved(false);
                        }

                    }

                    if (pc.getX() == j && pc.getY() == i) {//display character
                        if (pc.getHealth() <= 0) {
                            game.setScreen(new Lose(game));
                            dispose();
                        }
                        if (map[i][j] == 'f') {
                            System.out.println("Message: " + game.inter.getEdit());
                            if(game.inter.getEdit()==true){
                                batch.end();
                                System.out.println("Message: Edit " + game.inter.getEdit());
                                game.setScreen(new MapInfo(game));
                                dispose();
                            }else if(game.inter.getEdit()==false) {
                                System.out.println("Message: Win " + game.inter.getEdit());
                                game.setScreen(new Win(game));
                                dispose();
                            }
                        }
                        batch.draw(regions[1][4], (j + xOffset) * 64, (i + yOffset) * 64);
                        //hit animation
                        if(pc.isHit()){
                            batch.draw(regions[1][8], (j + xOffset) * 64, (i + yOffset) * 64);
                            pc.resetHit();
                        }
                    } else if (pc.getX() + 1 == j && pc.getY() == i) {//to the right
                        batch.draw(regions[0][9], (j + xOffset) * 64, (i + yOffset) * 64);
                    } else if (pc.getX() - 1 == j && pc.getY() == i) {//to the left
                        batch.draw(regions[1][9], (j + xOffset) * 64, (i + yOffset) * 64);
                    } else if (pc.getX() == j && pc.getY() - 1 == i) {//up
                        batch.draw(regions[2][9], (j + xOffset) * 64, (i + yOffset) * 64);
                    } else if (pc.getX() == j && pc.getY() + 1 == i) {//down
                        batch.draw(regions[3][9], (j + xOffset) * 64, (i + yOffset) * 64);
                    }
                }//end animation layer
            }
            yOffset-=2;
        }

        // TODO HP TEXTURE
        batch.draw(regions[4][8], pcScreenX - 150, pcScreenY + 225);
        batch.draw(regions[4][9], pcScreenX - 86, pcScreenY + 225);

        // TODO HP BAR TEXTURE
        for (int h = 0; h < pc.getHealth(); h++) {
            batch.draw(regions[5][8], pcScreenX-(150 - h), pcScreenY+225);
        }

        batch.end();

        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(new Profile(game));
            dispose();
        }


        if (Gdx.input.isTouched()) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int screenX = Gdx.input.getX();
            int screenY = Gdx.input.getY();

            if(screenX>630&&screenX<830&&screenY<880&&screenY>680){//move right
                if(pc.attemptMove(1,0,map,charMap)){
                    pcScreenX+=64;
                    camera.position.set(pcScreenX,pcScreenY,0);
                }
            }else if(screenX>270&&screenX<470&&screenY<880&&screenY>680){//move left
                if(pc.attemptMove(-1,0,map,charMap)){
                    pcScreenX-=64;
                    camera.position.set(pcScreenX,pcScreenY,0);
                }
            }else if(screenX>470&&screenX<630&&screenY<1080&&screenY>880){//move down
                if(pc.attemptMove(0,1,map,charMap)){
                    pcScreenY-=64;
                    camera.position.set(pcScreenX,pcScreenY,0);
                }
            }else if(screenX>470&&screenX<630&&screenY<690&&screenY>490){//move up
                if(pc.attemptMove(0,-1,map,charMap)){
                    pcScreenY+=64;
                    camera.position.set(pcScreenX,pcScreenY,0);
                }
            }
        }
    }

    public void resize (int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
        texture.dispose();
    }

}
