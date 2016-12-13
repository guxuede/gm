#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;


void main()
{
  if( texture2D(u_texture, v_texCoords).x > 0.8f)  //Check if Color contains red
      gl_FragColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);  //If yes, set it to white
  else
      gl_FragColor = texture2D(u_texture, v_texCoords); //else sample from original
  //gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}
