package com.guxuede;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;


/**
 * Created by greggu on 2016/12/7 .
 */
public class MeshTriangles1 extends ApplicationAdapter
{
	//Position attribute - (x, y) 
	public static final int POSITION_COMPONENTS = 2;

	//Color attribute - (r, g, b, a)
	public static final int COLOR_COMPONENTS = 4;

	//Total number of components for all attributes
	public static final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;

	//The "size" (total number of floats) for a single triangle
	public static final int PRIMITIVE_SIZE = 3 * NUM_COMPONENTS;

	//The maximum number of triangles our mesh will hold
	public static final int MAX_TRIS = 1;

	//The maximum number of vertices our mesh will hold
	public static final int MAX_VERTS = MAX_TRIS * 3;

	//The array which holds all the data, interleaved like so:
	//    x, y, r, g, b, a
	//    x, y, r, g, b, a, 
	//    x, y, r, g, b, a, 
	//    ... etc ...
	protected float[] verts = new float[MAX_VERTS * NUM_COMPONENTS];

	//The current index that we are pushing triangles into the array
	protected int idx = 0;

	OrthographicCamera camera;
	ShaderProgram shader;
	Mesh mesh;

	@Override
	public void create()
	{
		camera = new OrthographicCamera();
		String vertexShader = Gdx.files.internal("data/mesh.vsh").readString();
		String fragmentShader = Gdx.files.internal("data/mesh.fsh").readString();;

		shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) {
			Gdx.app.log("ShaderTest", shader.getLog());
			Gdx.app.exit();
		}

		/**
		 * 一个mesh可能保存一个对象的位置、颜色、纹理等方面的信息
		 * 这4个参数分别为:是否静态、最大顶点数、最大索引数、Vertex属性
		 */
		mesh = new Mesh(true, MAX_VERTS, 0,
				new VertexAttribute(VertexAttributes.Usage.Position, POSITION_COMPONENTS, "a_position"),
				new VertexAttribute(VertexAttributes.Usage.ColorUnpacked, COLOR_COMPONENTS, "a_color"));
	}

	@Override
	public void render()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//push a few triangles to the batch
		drawTriangle(10, 10, 40, 40, Color.RED);
		drawTriangle(50, 50, 70, 40, Color.BLUE);
		drawTriangle(50, 50, 70, 40, Color.YELLOW);

		//this will render the above triangles to GL, using Mesh
		flush();
	}


	void drawTriangle(float x, float y, float width, float height, Color color) {
		//we don't want to hit any index out of bounds exception...
		//so we need to flush the batch if we can't store any more verts
		if (idx==verts.length)
			flush();

		//now we push the vertex data into our array
		//we are assuming (0, 0) is lower left, and Y is up

		//bottom left vertex
		verts[idx++] = x;           //Position(x, y) 
		verts[idx++] = y;
		verts[idx++] = color.r;     //Color(r, g, b, a)
		verts[idx++] = color.g;
		verts[idx++] = color.b;
		verts[idx++] = color.a;

		//top left vertex
		verts[idx++] = x;           //Position(x, y) 
		verts[idx++] = y + height;
		verts[idx++] = color.r;     //Color(r, g, b, a)
		verts[idx++] = color.g;
		verts[idx++] = color.b;
		verts[idx++] = color.a;

		//bottom right vertex
		verts[idx++] = x + width;    //Position(x, y) 
		verts[idx++] = y;
		verts[idx++] = color.r;      //Color(r, g, b, a)
		verts[idx++] = color.g;
		verts[idx++] = color.b;
		verts[idx++] = color.a;
	}
	void flush() {
		//if we've already flushed
		if (idx==0)
			return;

		//sends our vertex data to the mesh
		mesh.setVertices(verts);

		//no need for depth...
		Gdx.gl.glDepthMask(false);

		//enable blending, for alpha
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		//number of vertices we need to render
		int vertexCount = (idx/NUM_COMPONENTS);

		//update the camera with our Y-up coordiantes
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//start the shader before setting any uniforms
		shader.begin();

		//update the projection matrix so our triangles are rendered in 2D
		shader.setUniformMatrix("u_projTrans", camera.combined);

		//render the mesh
		mesh.render(shader, GL20.GL_TRIANGLES, 0, vertexCount);

		shader.end();

		//re-enable depth to reset states to their default
		Gdx.gl.glDepthMask(true);

		//reset index to zero
		idx = 0;
	}
	


	@Override
	public void dispose()
	{
		mesh.dispose();
	}

	public static void main(String[] args) throws Exception
	{
		new LwjglApplication(new MeshTriangles1());
	}
}
