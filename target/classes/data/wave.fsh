//波纹：
#ifdef GL_ES
precision mediump float;
#endif
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float motion;
uniform float angle;

void main()
{
    vec2 tmp = v_texCoords;
    tmp.x = tmp.x + 0.01 * sin(motion +  tmp.x * angle);
    tmp.y = tmp.y + 0.01 * sin(motion +  tmp.y * angle);
    gl_FragColor = texture2D(u_texture, tmp);
}
