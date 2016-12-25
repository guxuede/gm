package com.guxuede;

import com.badlogic.gdx.ApplicationAdapter;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * LibGDX port of ShaderLesson6, i.e. normal mapping in 2D games.
 * @author davedes
 */
public class ShaderLesson6 extends ApplicationAdapter {


    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.width = 640;
        cfg.height = 480;
        cfg.resizable = false;
        new LwjglApplication(new ShaderLesson6(), cfg);
    }
    Texture bg;
    Texture rock, rockNormals;

    SpriteBatch batch;
    OrthographicCamera cam;

    ShaderProgram shader;

    //our constants...
    public static final float DEFAULT_LIGHT_Z = 0.075f;
    public static final float AMBIENT_INTENSITY = 0.2f;
    public static final float LIGHT_INTENSITY = 1f;

    public static final Vector3 LIGHT_POS = new Vector3(0f,0f,DEFAULT_LIGHT_Z);

    //Light RGB and intensity (alpha)
    public static final Vector3 LIGHT_COLOR = new Vector3(1f, 0.8f, 0.6f);

    //Ambient RGB and intensity (alpha)
    public static final Vector3 AMBIENT_COLOR = new Vector3(0.6f, 0.6f, 1f);

    //Attenuation coefficients for light falloff
    public static final Vector3 FALLOFF = new Vector3(.4f, 3f, 20f);



    @Override
    public void create() {
        bg = new Texture(Gdx.files.internal("data/items.png"));

        rock = new Texture(Gdx.files.internal("data/dragon.png"));
        rockNormals = new Texture(Gdx.files.internal("data/dragon_n.png"));

        String VERT = Gdx.files.internal("data/normap.vsh").readString();
        String FRAG = Gdx.files.internal("data/normap.fsh").readString();;

        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(VERT, FRAG);
        //ensure it compiled
        if (!shader.isCompiled())
            throw new GdxRuntimeException("Could not compile shader: "+shader.getLog());
        //print any warnings
        if (shader.getLog().length()!=0)
            System.out.println(shader.getLog());

        //setup default uniforms
        shader.begin();

        //our normal map
        shader.setUniformi("u_normals", 1); //GL_TEXTURE1

        //light/ambient colors
        //LibGDX doesn't have Vector4 class at the moment, so we pass them individually...
        shader.setUniformf("LightColor", LIGHT_COLOR.x, LIGHT_COLOR.y, LIGHT_COLOR.z, LIGHT_INTENSITY);
        shader.setUniformf("AmbientColor", AMBIENT_COLOR.x, AMBIENT_COLOR.y, AMBIENT_COLOR.z, AMBIENT_INTENSITY);
        shader.setUniformf("Falloff", FALLOFF);

        //LibGDX likes us to end the shader program
        shader.end();

        batch = new SpriteBatch(1000);
        batch.setShader(shader);

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.setToOrtho(false);

        //handle mouse wheel
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean scrolled(int delta) {
                //LibGDX mouse wheel is inverted compared to lwjgl-basics
                LIGHT_POS.z = Math.max(0f, LIGHT_POS.z - (delta * 0.005f));
                System.out.println("New light Z: "+LIGHT_POS.z);
                return true;
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
        batch.setProjectionMatrix(cam.combined);

        shader.begin();
        shader.setUniformf("Resolution", width, height);
        shader.end();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //reset light Z
        if (Gdx.input.isTouched()) {
            LIGHT_POS.z = DEFAULT_LIGHT_Z;
            System.out.println("New light Z: "+LIGHT_POS.z);
        }
        batch.begin();
        batch.setShader(null);
        batch.draw(bg,0,0);
        batch.end();

        batch.setShader(shader);
        batch.begin();

        //shader will now be in use...

        //update light position, normalized to screen resolution
        float x = Mouse.getX() / (float)Display.getWidth();
        float y = Mouse.getY() / (float)Display.getHeight();

        LIGHT_POS.x = x;
        LIGHT_POS.y = y;

        //send a Vector4f to GLSL
        shader.setUniformf("LightPos", LIGHT_POS);

        //bind normal map to texture unit 1
        rockNormals.bind(1);

        //bind diffuse color to texture unit 0
        //important that we specify 0 otherwise we'll still be bound to glActiveTexture(GL_TEXTURE1)
        rock.bind(0);

        //draw the texture unit 0 with our shader effect applied
        batch.draw(rock, 0, 0);

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
        rock.dispose();
        rockNormals.dispose();
        shader.dispose();
    }
}