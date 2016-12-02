package com.guxuede;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ColorRenderingMain extends ApplicationAdapter {
	static final String TEXT_PLAIN = "AAA BBB CCC DDD EEE";
	static final String TEXT_COLOR = "[RED]AAA [BLUE]BBB [RED]CCC [BLUE]DDD [#fff]EEE";
	static final String TEXT_ESCAPE = "[[ORANGE]Escaped, but is colored";

	SpriteBatch batch;
	BitmapFont font;

	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.BLACK);

		font.getData().markupEnabled = true;
	}

	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		font.draw(batch, TEXT_PLAIN, 5, Gdx.graphics.getHeight() - 5);
		font.draw(batch, TEXT_COLOR, 5, Gdx.graphics.getHeight() - 20);
		font.draw(batch, TEXT_ESCAPE, 5, Gdx.graphics.getHeight() - 35);
		batch.end();
	}

	public static void main (String[] args) throws Exception {
		new LwjglApplication(new ColorRenderingMain());
	}
}
