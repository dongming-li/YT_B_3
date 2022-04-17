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
 * Created by Brennyn on 11/7/2017.
 */

public class Lose implements Screen {

    final CreepyCaverns game;
    Stage stage;

    public Lose(final CreepyCaverns game) {
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

        final Label labelWin = new Label("You lose!", skin, "labelDefault");
        table.add(labelWin).spaceBottom(150).fillX().center();
        table.row();

        final TextButton profileButton = new TextButton("Profile", skin);
        table.add(profileButton).spaceBottom(100);
        table.row();
        final TextButton logoutButton = new TextButton("Logout", skin);
        table.add(logoutButton);

        profileButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new Profile(game));
                dispose();
            }
        });

        logoutButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
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
    }
}
