package com.badlogic.gdx;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.tests.gwt.GwtTestWrapper;
import com.guxuede.ShaderMain;
import com.pixelscientists.gdx.InventoryDemoGame;
//import com.pixelscientists.gdx.LibgdxUtils;

/**
 * Created by guxuede on 2017/4/9 .
 */
public class GdxSampleDemoLunch  {

    public static void main (String[] args) throws Exception {
        new LwjglApplication(new InventoryDemoGame());
    }
}
