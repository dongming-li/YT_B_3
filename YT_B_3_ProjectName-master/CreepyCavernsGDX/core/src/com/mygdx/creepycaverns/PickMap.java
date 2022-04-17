package com.mygdx.creepycaverns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;

/**
 * Created by Brennyn on 11/5/2017.
 */

public class PickMap implements Screen {

    final CreepyCaverns game;
    Stage stage;


    public PickMap(final CreepyCaverns game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);
        Skin skin = CreepyCaverns.buildSkin();

        game.inter.getJSONMaps();
        String MAPLIST = game.inter.getMAPLIST();

        System.out.println("Check ML: " + MAPLIST);

        final ArrayList<String> strings = game.inter.parseMaps(MAPLIST);

        final Table scrollTable = new Table();
        System.out.println("Check size: " + strings.size());
        for(int i = 0; i < strings.size(); i += 3) {

            Label tempLabel = new Label(strings.get(i), skin, "labelDefault");
            TextButton tempButton = new TextButton("Play Game", skin);
            scrollTable.add(tempLabel).spaceRight(50).spaceBottom(50);
            scrollTable.add(tempButton).spaceBottom(50).left();

            scrollTable.row();

            final int map = i + 2;

            tempButton.addListener(new ClickListener() {
                public void clicked (InputEvent event, float x, float y) {
                    System.out.println("Check mapButton: " + strings.get(map));

                    game.inter.setMAPID(strings.get(map));

                    game.setScreen(new PlayGame(game));
                    game.inter.setEdit(false);
                    dispose();
                }
            });

        }

        final ScrollPane scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();

        this.stage.addActor(table);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(12/255f, 6/255f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act();
        this.stage.draw();
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
        stage.dispose();
    }
}
