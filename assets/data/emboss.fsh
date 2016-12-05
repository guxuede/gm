#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform vec2 steps;

const float stride = 2.0;

void main()
{
    vec2 texCoord = v_texCoords;
    vec4 color1;

    color1.rgb = vec3(0.5);
    color1 -= texture2D(u_texture, texCoord - steps) * 5.0;
    color1 += texture2D(u_texture, texCoord + steps) * 5.0;

    color1.rgb = vec3((color1.r + color1.g + color1.b) / 3.0);
    gl_FragColor = vec4(color1.rgb, texture2D(u_texture,v_texCoords).a);

}


//other demos
/**
    vec3 tmpColor = texture2D(u_texture, v_texCoords + steps * stride).rgb;
    tmpColor = texture2D(u_texture, v_texCoords).rgb - tmpColor + 0.5;
    float f = (tmpColor.r + tmpColor.g + tmpColor.b) / 3.0;
    gl_FragColor = vec4(f, f, f, texture2D(u_texture,v_texCoords).a);
**/


//come from web
/*
uniform float pixelWidth;
uniform float pixelHeight;

varying vec2 v_vTexcoord;
varying vec4 v_vColour;

void main()
{
    vec2 onePixel = vec2(1.0 / pixelWidth, 1.0 / pixelHeight);
    vec2 texCoord = v_vTexcoord;
    vec4 color1;

    color1.rgb = vec3(0.5);
    color1 -= texture2D(gm_BaseTexture, texCoord - onePixel) * 5.0;
    color1 += texture2D(gm_BaseTexture, texCoord + onePixel) * 5.0;

    color1.rgb = vec3((color1.r + color1.g + color1.b) / 3.0);
    gl_FragColor = vec4(color1.rgb, texture2D(gm_BaseTexture,v_vTexcoord).a);
}
*/

/*
precision mediump float;
varying vec2 textureCoordinate;
uniform sampler2D inputImageTexture;
uniform vec2 steps;
const float stride = 2.0;
void main()
{
    vec3 tmpColor = texture2D(inputImageTexture, textureCoordinate + steps * stride).rgb;
    tmpColor = texture2D(inputImageTexture, textureCoordinate).rgb - tmpColor + 0.5;
    float f = (tmpColor.r + tmpColor.g + tmpColor.b) / 3.0;
    gl_FragColor = vec4(f, f, f, 1.0);
}
*/
