#ifdef GL_ES
precision mediump float;
#endif

attribute vec4 a_position;
varying vec2 v_texCoords;
void main()
{
    gl_Position = a_position;
    v_texCoords = vec2((a_position.x+1.0)/2.0, 1.0-(a_position.y+1.0)/2.0);
}
