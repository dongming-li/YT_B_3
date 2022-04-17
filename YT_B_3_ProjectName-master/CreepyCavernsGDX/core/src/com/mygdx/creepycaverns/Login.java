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
 * Created by Brennyn on 10/31/2017.
 */

public class Login implements Screen {

    final CreepyCaverns game;
    Stage stage;

    public Login(final CreepyCaverns game){
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

        final Label labelUsername = new Label("Username:", skin, "labelDefault");
        table.add(labelUsername).spaceBottom(50);
        final TextField textUsername = new TextField("", skin, "textFieldDefault");
        table.add(textUsername).spaceBottom(50);
        table.row();

        final Label labelPassword = new Label("Password:", skin, "labelDefault");
        table.add(labelPassword).spaceBottom(50);
        final TextField textPassword = new TextField("", skin, "textFieldDefault");
        table.add(textPassword).spaceBottom(50);
        table.row();

        final TextButton buttonLogin = new TextButton("Login", skin);
        table.add(buttonLogin).fillX().center().spaceBottom(100);
        table.row();

        buttonLogin.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                //Login attempt

                String username = textUsername.getText();
                String password = textPassword.getText();

                game.inter.startLogin(username, password);
                String user = game.inter.getUser();
                //System.out.println("Check Login user: " + user);
                //System.out.println("Check Login username: " + username);

                if (user == null){
                    game.setScreen(new MainMenuScreen(game));
                } else {
                    if (user.equalsIgnoreCase(username)) {
                        game.setScreen(new Profile(game));
                    } else {
                        game.setScreen(new MainMenuScreen(game));
                    }
                }
                dispose();
            }
        });

        final TextButton buttonBack = new TextButton("Back", skin);
        table.add(buttonBack).fillX().center();
        table.row();

        buttonBack.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
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
    }
}
