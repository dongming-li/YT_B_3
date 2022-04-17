package com.mygdx.creepycaverns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Brennyn on 10/31/2017.
 */

public class Profile implements Screen{

    final CreepyCaverns game;
    Stage stage;

    public Profile(final CreepyCaverns game) {
        this.game = game;
    }


    @Override
    public void show() {
        Skin skin = CreepyCaverns.buildSkin();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        final Label userLabel = new Label("Username: " + game.inter.getUser(), skin, "labelDefault");
        table.add(userLabel).spaceBottom(150).fillX().center();
        table.row();

        final TextButton playButton = new TextButton("Play Game", skin);
        table.add(playButton).spaceBottom(100);
        table.row();
        final TextButton editButton = new TextButton("Map Edit", skin);
        table.add(editButton).spaceBottom(100);
        table.row();
        final TextButton logoutButton = new TextButton("Logout", skin);
        table.add(logoutButton).spaceBottom(100);
        table.row();
        final TextButton banButton = new TextButton("Ban", skin);

        System.out.println("Check admin: " + game.inter.isAdmin());

        if (game.inter.isAdmin().equalsIgnoreCase("true")) {
           System.out.println("Check admin");
           table.add(banButton);
        }

        playButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new PickMap(game));
                dispose();
            }
        });

        editButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new MapEditor(game));
                dispose();
            }
        });

        logoutButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

       if (game.inter.isAdmin().equalsIgnoreCase("true")) {
          banButton.addListener(new ClickListener() {
             public void clicked (InputEvent event, float x, float y) {
                  game.setScreen(new BanScreen(game));
                  dispose();
                }
          });
       }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(12/255f, 6/255f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
        // System.out.println("Check Profile dispose " + game.inter.getUser());
    }
}
