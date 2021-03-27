package com.pixelscientists.gdx;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import com.pixelscientists.gdx.LibgdxUtils;

/**
 * Created by guxuede on 2017/4/9 .
 */
public class InventoryDemoGameLunch {

    public static void main (String[] args) throws Exception {
        new LwjglApplication(new InventoryDemoGame());
    }
}
