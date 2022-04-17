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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Brennyn on 11/27/2017.
 */

public class BanScreen implements Screen {

    final CreepyCaverns game;
    Stage stage;

    public BanScreen(final CreepyCaverns game) {
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

        final Label userLabel = new Label("Ban User", skin, "labelDefault");
        table.add(userLabel).spaceBottom(150).fillX().center();
        table.row();

        final TextField banUsername = new TextField("", skin, "textFieldDefault");
        table.add(banUsername).spaceBottom(50);
        table.row();

        final TextButton banButton = new TextButton("Ban", skin);
        table.add(banButton).spaceBottom(100);
        table.row();
        final TextButton backButton = new TextButton("Back", skin);
        table.add(backButton);
        table.row();

        banButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                String user = banUsername.getText();
                game.inter.banActor(user, game.inter.getUser());
                game.setScreen(new BanScreen(game));
                dispose();
            }
        });

        backButton.addListener(new ClickListener() {
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
