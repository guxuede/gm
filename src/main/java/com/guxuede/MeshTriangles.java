package com.guxuede;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;


/**
 * Created by greggu on 2016/12/7 .
 */
public class MeshTriangles extends ApplicationAdapter
{
	OrthographicCamera camera;
	ShaderProgram shader;
	Mesh mesh;

	@Override
	public void create()
	{
		camera = new OrthographicCamera();
		//update the camera with our Y-up coordiantes
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		shader = SpriteBatch.createDefaultShader();
		/**
		 * 一个mesh可能保存一个对象的位置、颜色、纹理等方面的信息
		 * 这4个参数分别为:是否静态、最大顶点数、最大索引数、Vertex属性
		 */
		mesh  = new Mesh(true, 3, 3,
				new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_position"));

		mesh.setVertices(new float[] { -0.5f, -0.5f, 0,
				0.5f, -0.5f, 0,
				0, 0.5f, 0 });

		mesh.setIndices(new short[] { 0, 1, 2 });
	}

	@Override
	public void render()
	{
		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shader.begin();
		//update the projection matrix so our triangles are rendered in 2D
		shader.setUniformMatrix("u_projTrans", camera.combined);
		mesh.render(shader, GL20.GL_TRIANGLES,0 ,3);
		shader.end();
	}


	@Override
	public void dispose()
	{
		mesh.dispose();
	}

	public static void main(String[] args) throws Exception
	{
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		new LwjglApplication(new MeshTriangles());
	}
}
