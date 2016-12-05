package com.guxuede;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;



public class ShaderWave extends ApplicationAdapter {
	ShaderProgram shader;
	Mesh mesh;
	Texture texture;

	@Override
	public void create () {
		String vertexShader = Gdx.files.internal("data/wave.vsh").readString();
		String fragmentShader = Gdx.files.internal("data/wave.fsh").readString();;

		shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) {
			Gdx.app.log("ShaderTest", shader.getLog());
			Gdx.app.exit();
		}

		mesh = new Mesh(true, 4, 6, VertexAttribute.Position(), VertexAttribute.ColorUnpacked(), VertexAttribute.TexCoords(0));
		mesh.setVertices(new float[] {-0.5f, -0.5f, 0, 1, 1, 1, 1, 0, 1, 0.5f, -0.5f, 0, 1, 1, 1, 1, 1, 1, 0.5f, 0.5f, 0, 1, 1, 1,
				1, 1, 0, -0.5f, 0.5f, 0, 1, 1, 1, 1, 0, 0});
		mesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});
		texture = new Texture(Gdx.files.internal("data/items.png"));
	}

	@Override
	public void render () {

		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		texture.bind();
		shader.begin();
		shader.setUniformf("motion", motion += 0.05);
		shader.setUniformf("angle", 15.0f);
		mesh.render(shader, GL20.GL_TRIANGLES);
		shader.end();


		if (motion > 1.0e20) motion = 0.0f;
	}
	
	float motion = 0;
	

	@Override
	public void dispose () {
		mesh.dispose();
		texture.dispose();
		shader.dispose();
	}
	public static void main (String[] args) throws Exception {
		new LwjglApplication(new ShaderWave());
	}
}
