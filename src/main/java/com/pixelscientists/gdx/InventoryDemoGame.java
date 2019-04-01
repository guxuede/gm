package com.pixelscientists.gdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Logger;
import com.pixelscientists.gdx.inventory.Inventory;
import com.pixelscientists.gdx.inventory.InventoryActor;

/**
 * Created by guxuede on 2017/5/3 .
 */
public class InventoryDemoGame  implements ApplicationListener {

    public static Stage stage;
    public static AssetManager assetManager;

    @Override
    public void create() {
        assetManager = new AssetManager();
        assetManager.load("textures/splash.png", Texture.class);
        assetManager.load("skins/uiskin.json", Skin.class);
        assetManager.load("icons/icons.atlas", TextureAtlas.class);
        assetManager.finishLoading();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Texture.setAssetManager(assetManager);

        stage = new Stage();
        Skin skin = assetManager.get("skins/uiskin.json", Skin.class);
        stage.addActor(new TextButton("hello",skin));
        Gdx.input.setInputProcessor(stage);


        DragAndDrop dragAndDrop = new DragAndDrop();
        InventoryActor inventoryActor = new InventoryActor(new Inventory(), dragAndDrop, skin);
        inventoryActor.setVisible(true);
        stage.addActor(inventoryActor);
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
}
