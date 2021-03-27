package com.guxuede;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 *
 *     预设的几个attibute(属性,SpriteBatch 传入的,只能在vertex使用),
 public static final String POSITION_ATTRIBUTE = "a_position";
 public static final String NORMAL_ATTRIBUTE = "a_normal";
 public static final String COLOR_ATTRIBUTE = "a_color";   //batch.setColor(Color.BLUE); 时的值
 public static final String TEXCOORD_ATTRIBUTE = "a_texCoord";
 public static final String TANGENT_ATTRIBUTE = "a_tangent";
 public static final String BINORMAL_ATTRIBUTE = "a_binormal";


 gl_FragCoord 像素所在的位置


 * A port of ShaderLesson2 from lwjgl-basics to LibGDX:
 * https://github.com/mattdesl/lwjgl-basics/wiki/ShaderLesson2
 *
 * @author davedes
 */
public class ShaderLesson2 implements ApplicationListener {

    //Minor differences:
    //LibGDX Position attribute is a vec4
    //u_projView is called u_projTrans
    //we need to set ShaderProgram.pedantic to false
    //LibGDX uses lower-left as origin (0, 0)
    //TexCoord attribute requires "0" appended at end to denote GL_TEXTURE0
    //ShaderProgram.TEXCOORD_ATTRIBUTE+"0"
    //It's wise to use LOWP for color attribute in GL ES

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Shader Lesson 2";
        cfg.width = 640;
        cfg.height = 480;
        cfg.resizable = false;

        new LwjglApplication(new ShaderLesson2(), cfg);
    }

    final String VERT =
            "attribute vec4 "+ ShaderProgram.POSITION_ATTRIBUTE+";\n" +
                    "attribute vec4 "+ShaderProgram.COLOR_ATTRIBUTE+";\n" +
                    "attribute vec2 "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n" +

                    "uniform mat4 u_projTrans;\n" +
                    " \n" +
                    "varying vec4 vColor;\n" +
                    "varying vec2 vTexCoord;\n" +

                    "void main() {\n" +
                    "	vColor = "+ShaderProgram.COLOR_ATTRIBUTE+";\n" +
                    "	vTexCoord = "+ShaderProgram.TEXCOORD_ATTRIBUTE+"0;\n" +
                    "	gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
                    "}";

    final String FRAG =
            //GL ES specific stuff
            "#ifdef GL_ES\n" //
                    + "#define LOWP lowp\n" //
                    + "precision mediump float;\n" //
                    + "#else\n" //
                    + "#define LOWP \n" //
                    + "#endif\n" + //
                    "varying LOWP vec4 vColor;\n" +
                    "varying vec2 vTexCoord;\n" +
                    "uniform sampler2D u_texture;\n" +
                    "void main() {\n" +
                    "	vec4 texColor = texture2D(u_texture, vTexCoord);\n" +
                    "	\n" +
                    "	texColor.rgb = 1.0 - texColor.rgb;\n" +
                    "	\n" +
                    "	gl_FragColor = texColor * vColor;\n" +
                    "}";

    Texture tex;
    SpriteBatch batch;
    OrthographicCamera cam;
    ShaderProgram shader;

    @Override
    public void create() {
        tex = new Texture(Gdx.files.internal("data/items.png"));

        //important since we aren't using some uniforms and attributes that SpriteBatch expects
        ShaderProgram.pedantic = false;

        shader = new ShaderProgram(VERT, FRAG);
        if (!shader.isCompiled()) {
            System.err.println(shader.getLog());
            System.exit(0);
        }
        if (shader.getLog().length()!=0)
            System.out.println(shader.getLog());


        batch = new SpriteBatch(1000, shader);
        batch.setShader(shader);

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.setToOrtho(false);
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
        batch.setProjectionMatrix(cam.combined);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        batch.begin();

        //notice that LibGDX coordinate system origin is lower-left
        batch.draw(tex, 10, 10);
//        batch.draw(tex, 10, 320, 32, 32);

        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        shader.dispose();
        tex.dispose();
    }
}