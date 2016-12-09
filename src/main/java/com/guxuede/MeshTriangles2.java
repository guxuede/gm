package com.guxuede;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;


/**
 * Created by greggu on 2016/12/7 .
 * http://www.jdzhao.com/game/show_804_195.html
 */
public class MeshTriangles2 extends ApplicationAdapter
{
	ShaderProgram shader;
	Texture texture;
	Texture texture2;
	Mesh mesh;
	public void create() {
		// 以下命令供GPU使用(不支持GLES2.0就不用跑了)
		String vertexShader = Gdx.files.internal("data/mesh2.vsh").readString();
		String fragmentShader = Gdx.files.internal("data/mesh2.fsh").readString();;

		shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) {
			Gdx.app.log("ShaderTest", shader.getLog());
			Gdx.app.exit();
		}
		// 构建网格对象
		mesh = new Mesh( true , 4 , 6 , new VertexAttribute(VertexAttributes.Usage.Position, 2 ,"a_position" ),
				new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2 , "a_texCoord" ));
		float[] vertices = { 
				-0.5f, 0.5f, 0.0f, 0.0f, -0.5f, -0.5f, 0.0f,
				1.0f, 0.5f, -0.5f, 1.0f, 1.0f, 0.5f, 0.5f, 1.0f, 0.0f };
		short[] indices = { 0, 1, 2, 0, 2, 3 };
		// 注入定点坐标
		mesh.setVertices(vertices);
		mesh.setIndices(indices);
		
		// 以Pixmap生成两个指定内容的Texture
		texture = new Texture(Gdx.files.internal("data/items.png"));
		texture2 = new Texture(Gdx.files.internal("data/help.png"));
	}
	public void dispose() {
	}
	public void pause() {
	}
	public void render() {
		// PS:由于使用了ShaderProgram，因此必须配合gl20模式(否则缺少关键opengles接口)
		Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
		texture.bind();
		Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE1);
		texture2.bind();
		// 开始使用ShaderProgram渲染
		shader.begin();
		shader.setUniformi("s_texture", 0);
		shader.setUniformi("s_texture2", 1);
		mesh.render(shader, GL20.GL_TRIANGLES);
		// 结束ShaderProgram渲染
		shader.end();
	}
	public void resize(int width, int height) {
	}
	public void resume() {
	}

/*	float InvSqrt(float x)
	{
		float xhalf = 0.5f*x;
		int i = *(int*)&x; // get bits for floating VALUE
		i = 0x5f375a86- (i>>1); // gives initial guess y0
		x = *(float*)&i; // convert bits BACK to float
		x = x*(1.5f-xhalf*x*x); // Newton step, repeating increases accuracy
		return x;
	}*/
	public static float invSqrt(float x) {
		float xhalf = 0.5f * x;
		int i = Float.floatToIntBits(x);
		i = 0x5f3759df - (i >> 1);
		x = Float.intBitsToFloat(i);
		x *= (1.5f - xhalf * x * x);
		return x;
	}

	public static void main(String[] args) throws Exception
	{
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		new LwjglApplication(new MeshTriangles2());
	}
}
