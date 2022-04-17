package com.mygdx.creepycaverns;

/**
 * Created by arctu_000 on 11/2/2017.
 */
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MapEditor implements Screen {

    final CreepyCaverns game;

    private Texture texture;
    private SpriteBatch batch;
    private TextureRegion[][] regions;
    private OrthographicCamera camera;
    private int centerX=0;
    private int centerY=0;
    private char[][] map;
    private MapEditorCursor cursor;
    private Character[][] charMap;
    private char[][] monsterCharMap = new char[10][10];

    public MapEditor(final CreepyCaverns game) {
        this.game = game;
    }

    /**
     * sets up the map
     */
    @Override
    public void show() {
        //setup camera and batch for graphics.
        batch = new SpriteBatch();
        texture=new Texture("CreepyCaverns.png");
        regions=TextureRegion.split(texture,64,64);
        camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //Find center of blank map;
        //should have a getMap method here
        map=new char[10][10];
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                map[i][j]='r';
            }
        }
        //map for holding the character, only npcs since stairs will indicate player's starting position.
        charMap=new Character[10][10];
        //center the view, and set cursor's initial position.
        cursor=new MapEditorCursor(map[0].length/2,map.length/2);
        centerX=(6*64)+32;
        centerY=64;
        //Setup camera's starting values
        camera.zoom+=-0.7;
        camera.position.set(centerX,centerY,0);
    }

    @Override
    /**
     * Renders the screen
     */
    public void render(float delta) {
//clear screen, set screen to black.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //update any changes to the camera if needed.
        camera.update();
        //attach camera, begin batch
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //offsets to flip the screen, or modify the view easily.
        int yOffset=6;
        int xOffset=1;
        for(int i=0;i<map.length;i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(j >= 0 && j <= map[0].length-1 && i >= 0 && i <= map.length-1&&cursor.getY()+4>i) {
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
                if(charMap[i][j] instanceof NonPlayerCharacter){
                    batch.draw(regions[2][4], (j + xOffset) * 64, (i + yOffset) * 64 );
                }
                if(charMap[i][j] instanceof ZombieNPC){
                    batch.draw(regions[2][5], (j + xOffset) * 64, (i + yOffset) * 64 );
                }
                if (cursor.getX()==j&&cursor.getY()==i) {//display cursor position
                    //display the tile on the map over the cursor's position.
                    if(cursor.mode==0) {//place terrain mode
                        switch (cursor.getTerrain()) {
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
                                batch.draw(regions[0][3], (j + xOffset) * 64, (i + yOffset) * 64);
                        }
                    }else if(cursor.mode==1){//place NPC mode
                        if(cursor.currMon==0){
                            batch.draw(regions[2][4], (j + xOffset) * 64, (i + yOffset) * 64 );
                        }else if(cursor.currMon==1){
                            batch.draw(regions[2][5], (j + xOffset) * 64, (i + yOffset) * 64 );
                        }
                    }
                    //cursor corners
                    batch.draw(regions[0][8],(j+xOffset)*64,(i+yOffset)*64);
                }//if point is the cursor's position
            }//end inner loop for display
            yOffset-=2;
        }//end outer loop for display
        //Need to display bottom 3 tiles and have a button for placing a tile, or display monsters.
        batch.draw(regions[3][8],centerX+78,centerY-256);
        if(cursor.mode==0) {
            for (int i = 0; i < 3; i++) {
                if (i == 1) {
                    switch (cursor.getTerrain()) {
                        case 'r'://rooms
                            batch.draw(regions[0][0], centerX - 82, centerY - 256);
                            break;
                        case 'h'://hallway
                            break;
                        case 't'://traps
                            break;
                        case 'w'://water
                            batch.draw(regions[1][0],centerX-82, centerY-256);
                            break;
                        case 's'://start tile
                            batch.draw(regions[0][1], centerX - 82, centerY - 256);
                            break;
                        case 'f'://end tile
                            batch.draw(regions[0][2], centerX - 82, centerY - 256);
                            break;
                        case 'i'://impassable terrain
                            batch.draw(regions[0][3], centerX - 82, centerY - 256);
                        default://default is to make impassable tile
                            batch.draw(regions[0][3], centerX - 82, centerY - 256);
                    }
                } else if (i == 0) {
                    switch (cursor.getTerrainLeft()) {
                        case 'r'://rooms
                            batch.draw(regions[0][0], centerX - 156, centerY - 256);
                            break;
                        case 'h'://hallway
                            break;
                        case 't'://traps
                            break;
                        case 'w'://water
                            batch.draw(regions[1][0],centerX-156 ,centerY-256);
                            break;
                        case 's'://start tile
                            batch.draw(regions[0][1], centerX - 156, centerY - 256);
                            break;
                        case 'f'://end tile
                            batch.draw(regions[0][2], centerX - 156, centerY - 256);
                            break;
                        case 'i'://impassable terrain
                            batch.draw(regions[0][3], centerX - 156, centerY - 256);
                        default://default is to make impassable tile
                            batch.draw(regions[0][3], centerX - 156, centerY - 256);
                    }
                } else if (i == 2) {
                    switch (cursor.getTerrainRight()) {
                        case 'r'://rooms
                            batch.draw(regions[0][0], centerX - 8, centerY - 256);
                            break;
                        case 'h'://hallway
                            break;
                        case 't'://traps
                            break;
                        case 'w'://water
                            batch.draw(regions[1][0],centerX-8, centerY-256);
                            break;
                        case 's'://start tile
                            batch.draw(regions[0][1], centerX - 8, centerY - 256);
                            break;
                        case 'f'://end tile
                            batch.draw(regions[0][2], centerX - 8, centerY - 256);
                            break;
                        case 'i'://impassable terrain
                            batch.draw(regions[0][3], centerX - 8, centerY - 256);
                        default://default is to make impassable tile
                            batch.draw(regions[0][3], centerX - 8, centerY - 256);
                    }
                }
            }//end for loop, should replace this with more concise code.
        }//end conditional for mode 0, displaying the bottom 3 terrain

        batch.draw(regions[2][8], centerX-155, centerY+225);

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

            if (screenX>50&&screenX<275&&screenY<175&&screenY>50) {

                game.inter.setEdit(true);
                game.inter.setEditorMap(map);
                for (int i = 0; i < map.length; i++)
                {
                    for (int j = 0; j < map[0].length; j++)
                    {
                        if(charMap[i][j] instanceof NonPlayerCharacter)
                        {
                            monsterCharMap[i][j] = 'm';
                            System.out.println("Message: Placing mummy at " + i + " " + j + " in map editor");
                        }
                        else if(charMap[i][j] instanceof ZombieNPC)
                        {
                            monsterCharMap[i][j] = 'z';
                            System.out.println("Message: Placing zombie at " + i + " " + j + " in map editor");
                        }
                    }
                }
                game.inter.setMapMonsters(monsterCharMap);
                game.setScreen(new PlayGame(game));

                dispose();
            }

            if(screenX>630&&screenX<830&&screenY<880&&screenY>680){//move right
                if(cursor.attemptMove(1,0,map)){
                    centerX+=64;
                    camera.position.set(centerX,centerY,0);
                }
            }else if(screenX>270&&screenX<470&&screenY<880&&screenY>680){//move left
                System.out.println("left");
                if(cursor.attemptMove(-1,0,map)){
                    centerX-=64;
                    camera.position.set(centerX,centerY,0);
                }
            }else if(screenX>470&&screenX<630&&screenY<1080&&screenY>880){//move down
                if(cursor.attemptMove(0,1,map)){
                    centerY-=64;
                    camera.position.set(centerX,centerY,0);
                }
            }else if(screenX>470&&screenX<630&&screenY<690&&screenY>490){//move up
                if(cursor.attemptMove(0,-1,map)){
                    centerY+=64;
                    camera.position.set(centerX,centerY,0);
                }
            }else if(screenX<630&&screenX>470&&screenY<880&&screenY>690){//place the current thing on the cursor's position
                cursor.placeThing(map, charMap);
            }else if(screenX>50&&screenX<250&&screenY<1820&&screenY>1620){//cycle things left
                cursor.cycleLeft();
            }else if(screenX>540&&screenX<740&&screenY<1820&&screenY>1620){//cycle things right
                cursor.cycleRight();
            }else if(screenX>750&&screenX<950&&screenY<1820&&screenY>1620){//bottom right corner, tapping should switch edit modes
                cursor.switchMode();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

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
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}
