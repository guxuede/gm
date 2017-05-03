package com.badlogic.gdx;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.tests.gwt.GwtTestWrapper;
import com.guxuede.ShaderMain;

/**
 * Created by guxuede on 2017/4/9 .
 */
public class GdxSampleDemoLunch  {

    public static void main (String[] args) throws Exception {
        new LwjglApplication(new GwtTestWrapper());
    }
}
