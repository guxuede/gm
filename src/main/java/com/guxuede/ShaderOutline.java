package com.guxuede;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;


public class ShaderOutline extends ApplicationAdapter {

	public static void main(String[] args) throws LWJGLException {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ShaderOutline";
//        cfg.useGL30 = true;
		cfg.width = 640;
		cfg.height = 480;
		cfg.resizable = true;

		new LwjglApplication(new ShaderOutline(), cfg);
	}

	Texture tex;
	SpriteBatch batch;
	OrthographicCamera cam;
	ShaderProgram shader;

	@Override
	public void create() {
		tex = new Texture(Gdx.files.internal("data/dragon.png"));
		//important since we aren't using some uniforms and attributes that SpriteBatch expects
		ShaderProgram.pedantic = false;
		String vertexShader = Gdx.files.internal("data/outline.vsh").readString();
		String fragmentShader = Gdx.files.internal("data/outline.fsh").readString();;

		shader = new ShaderProgram(vertexShader, fragmentShader);
		if (!shader.isCompiled()) {
			System.err.println(shader.getLog());
			System.exit(0);
		}
		if (shader.getLog().length()!=0)
			System.out.println(shader.getLog());


		batch = new SpriteBatch(1000);
		batch.setShader(shader);
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		cam.setToOrtho(false);
	}

	@Override
	public void resize(int width, int height) {
		System.out.printf("resize"+Display.getWidth()+":"+Display.getHeight());
		//cam.setToOrtho(false, width, height);
		//batch.setProjectionMatrix(cam.combined);
	}

	private long startTime = System.currentTimeMillis();
	@Override
	public void render() {
		//time = System.currentTimeMillis() - time;
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		batch.begin();
		shader.setUniformf("resolution", new Vector2( Display.getWidth(), Display.getHeight()));//必须要在render中设定参数??
		shader.setUniformf("time", (System.currentTimeMillis() - startTime)/1000f);
		//notice that LibGDX coordinate system origin is lower-left
		batch.draw(tex, 0, 0,Display.getWidth(), Display.getHeight());
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

