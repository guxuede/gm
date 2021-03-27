#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform vec2 resolution;
uniform float time;

// shadertoy globals
#define iTime time
#define u_resolution resolution
#define PI 3.14159265359
#define t time
#define r resolution.xy

// Lightning shader
// rand,noise,fmb functions from https://www.shadertoy.com/view/Xsl3zN
// jerome

float rand(vec2 n) {
    return fract(sin(dot(n, vec2(12.9898, 4.1414))) * 43758.5453);
}

float noise(vec2 n) {
    const vec2 d = vec2(0.0, 1.0);
    vec2 b = floor(n), f = smoothstep(vec2(0.0), vec2(1.0), fract(n));
    return mix(mix(rand(b), rand(b + d.yx), f.x), mix(rand(b + d.xy), rand(b + d.yy), f.x), f.y);
}

float fbm(vec2 n) {
    float total = 0.0, amplitude = 1.0;
    for (int i = 0; i < 7; i++) {
        total += noise(n) * amplitude;
        n += n;
        amplitude *= 0.5;
    }
    return total;
}

void main() {
    vec4 col = vec4(0,0,0,1);
    vec2 uv = v_texCoords;


    // draw a line, left side is fixed
    vec2 t = uv * vec2(2.0,1.0) - iTime*3.0;
    vec2 t2 = (vec2(1,-1) + uv) * vec2(2.0,1.0) - iTime*3.0; // a second strand

    // draw the lines,
//  this make the left side fixed, can be useful
//  float ycenter = mix( 0.5, 0.25 + 0.25*fbm( t ), uv.x*4.0);
//    float ycenter2 = mix( 0.5, 0.25 + 0.25*fbm( t2 ), uv.x*4.0);
    float ycenter = fbm(t)*0.5;
    float ycenter2= fbm(t2)*0.5;

    // falloff
    float diff = abs(uv.y - ycenter);
    float c1 = 1.0 - mix(0.0,1.0,diff*20.0);

    float diff2 = abs(uv.y - ycenter2);
    float c2 = 1.0 - mix(0.0,1.0,diff2*20.0);

    float c = max(c1,c2);
    col = vec4(c*0.6,0.2*c2,c,1.0); // purple color
    gl_FragColor = col;
}