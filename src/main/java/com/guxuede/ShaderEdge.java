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



public class ShaderEdge extends ApplicationAdapter {
	ShaderProgram shader;
	Mesh mesh;
	Texture texture;
	Matrix4 matrix = new Matrix4();

	@Override
	public void create () {
		String vertexShader = Gdx.files.internal("data/edge.vsh").readString();
		String fragmentShader = Gdx.files.internal("data/edge.fsh").readString();

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

	Vector3 axis = new Vector3(0, 0, 1);
	float angle = 0;

	@Override
	public void render () {
		angle += Gdx.graphics.getDeltaTime() * 45;
		matrix.setToRotation(axis, angle);

		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		texture.bind();
		shader.begin();
		shader.setUniformf("steps", 1.0f/Gdx.graphics.getWidth(),1.0f/Gdx.graphics.getHeight());
		mesh.render(shader, GL20.GL_TRIANGLES);
		shader.end();

	}
	
	

	@Override
	public void dispose () {
		mesh.dispose();
		texture.dispose();
		shader.dispose();
	}
	public static void main (String[] args) throws Exception {
		new LwjglApplication(new ShaderEdge());
	}
}
