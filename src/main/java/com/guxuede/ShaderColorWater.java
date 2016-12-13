package com.guxuede;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;


/**
 * Created by greggu on 2016/12/7 .
 * http://www.jdzhao.com/game/show_804_195.html
 */
public class ShaderColorWater extends ApplicationAdapter
{
	SpriteBatch spriteBatch;
	ShaderProgram shader;
	Texture texture;

	public void create() {
		String vertexShader = Gdx.files.internal("data/mesh3.vsh").readString();
		String fragmentShader = Gdx.files.internal("data/mesh3.fsh").readString();;

		shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) {
			Gdx.app.log("ShaderTest", shader.getLog());
			Gdx.app.exit();
		}


		spriteBatch = new SpriteBatch();
		spriteBatch.setShader(shader);
		texture = new Texture(Gdx.files.internal("data/items.png"));
	}
	public void dispose() {
	}
	public void pause() {
	}
	public void render() {
		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.begin();
		spriteBatch.draw(texture,0,1);
		spriteBatch.end();
	}
	public void resize(int width, int height) {
	}
	public void resume() {
	}

	public static void main(String[] args) throws Exception
	{
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		new LwjglApplication(new ShaderColorWater());
	}
}
