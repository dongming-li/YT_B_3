package com.mygdx.creepycaverns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Brennyn on 10/31/2017.
 */

public class MainMenuScreen implements Screen {

    final CreepyCaverns game;
    Stage stage;


    public MainMenuScreen(final CreepyCaverns game) {
        this.game = game;
        System.out.println("Check MMS constructor - " + this.game.inter.getUser());
    }

    @Override
    public void show() {

        game.inter.dispose();

        Skin skin = CreepyCaverns.buildSkin();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        final Label title = new Label("Creepy Caverns", skin, "labelDefault");
        table.add(title).spaceBottom(200).fillX().center();
        table.row();


        final TextButton loginButton = new TextButton("Login", skin);
        table.add(loginButton).spaceBottom(100);
        table.row();
        final TextButton registerButton = new TextButton("Register", skin);
        table.add(registerButton);

        loginButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new Login(game));
                dispose();
            }
        });

        registerButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new Register(game));
                dispose();
            }
        });

        System.out.println("Check MM user: " + game.inter.getUser());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(12/255f, 6/255f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
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
        System.out.println("Check MM dispose " + game.inter.getUser());
    }
}
