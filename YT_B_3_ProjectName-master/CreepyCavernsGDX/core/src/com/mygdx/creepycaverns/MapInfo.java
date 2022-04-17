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
 * Created by Brennyn on 11/30/2017.
 */

public class MapInfo implements Screen {

    final CreepyCaverns game;
    Stage stage;

    public MapInfo(final CreepyCaverns game) {
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
        table.add(userLabel).spaceBottom(100).fillX().center();
        table.row();

        final Label textEnter = new Label("Enter a map name: ", skin, "labelDefault");
        table.add(textEnter).spaceBottom(50).fillX().center();
        table.row();

        final TextField textMapName = new TextField("", skin, "textFieldDefault");
        table.add(textMapName).spaceBottom(100).center();
        table.row();

        final TextButton submitButton = new TextButton("Submit Map", skin);
        table.add(submitButton).spaceBottom(50).center();
        table.row();

        submitButton.addListener(new ClickListener() {
            public void clicked (InputEvent event, float x, float y) {
                game.inter.submitMap(game.inter.getEditorMap(), game.inter.getEditorMonsters(), textMapName.getText(), game.inter.getUser());
                System.out.println("Check mapURL: " + game.inter.getMapURL());
                game.setScreen(new Profile(game));
                game.inter.setEdit(false);
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
