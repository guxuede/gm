package com.guxuede;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.superjumper.SuperJumper;

/**
 * Created by guxuede on 2017/5/3 .
 */
public class TopTipDemo   implements ApplicationListener {

    public static Stage stage;
    public static AssetManager assetManager;

    @Override
    public void create() {
        assetManager = new AssetManager();
        assetManager.load("skins/uiskin.json", Skin.class);
        assetManager.finishLoading();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Texture.setAssetManager(assetManager);

        stage = new Stage();
        Skin skin = assetManager.get("skins/uiskin.json", Skin.class);
        TextButton textButton = new TextButton("hello",skin);
        stage.addActor(textButton);
        Gdx.input.setInputProcessor(stage);

        TextTooltip textTooltip = new TextTooltip("Hello",skin);
        textButton.addListener(textTooltip);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Super Jumper";
        config.width = 480;
        config.height = 480;
        new LwjglApplication(new TopTipDemo(), config);
    }
}
