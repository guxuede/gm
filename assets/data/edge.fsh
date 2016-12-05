#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform vec2 steps;

const float stride = 2.0;

void main()
{

    vec3 tmpColor = texture2D(u_texture, v_texCoords + steps * stride).rgb;
    tmpColor = abs(texture2D(u_texture, v_texCoords).rgb - tmpColor);
    gl_FragColor = vec4(tmpColor * 2.0, 1.0);
}




/*precision mediump float;
varying vec2 textureCoordinate;
uniform sampler2D inputImageTexture;
uniform vec2 steps;

const float stride = 2.0;

void main()
{

    vec3 tmpColor = texture2D(inputImageTexture, textureCoordinate + steps * stride).rgb;
    tmpColor = abs(texture2D(inputImageTexture, textureCoordinate).rgb - tmpColor);
    gl_FragColor = vec4(tmpColor * 2.0, 1.0);
}
*/
