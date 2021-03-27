#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif


varying LOWP vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;


uniform vec2 resolution;
uniform float time;

#define width 0.1
#define PI 3.14159265359
#define SAMPLES 4
#define MAG 0.02


// Demonstration how to add a (possibly colored) outline
// around the edges of a texture with an alpha channel,
// made because mog asked for it.
//
// The implementation is rather "brute force",
// using an (2N+1)x(2N+1) box maximum filter,
// but at moderate outline widths, it does the job.
//
// Note that the outline width can only be integer,
// and the outline is only anti-aliased if the original
// object's contours are.
//
// -- KeyJ^trbl

const vec3 outlineColor = vec3(.9, .9, .0);

vec4 get(in vec2 fc) {
    // sample from the input texture
    // the coordinate is in screen pixels,
    // so it has to be converted into texture space first:
    return texture2D(u_texture, fc);
    // the scaling by vec2(2,7) at the end    (here: ^^^^^^^^^^^^^)
    // is only here to make the Nyan Cat image appear at a reasonable size
}


void main()
{

    // use Shadertoy's default animated gradient as a background
    vec3 background = vec3(0,0,0);

    // set the filter size (N); here, we make it dynamic
    // to show the effect, but in practice, it's usually
    // either a constant or a uniform parameter
    int size = 1;
    // determine the maximum alpha value in the (2N-1)x(2N-1) region
    float amax = 0.;
    for (int dy = -size; dy <= size;  ++dy) {
	    for (int dx = -size; dx <= size;  ++dx) {
            amax = max(amax, get(v_texCoords + vec2(dx/resolution.x, dy/resolution.y)).a);
        }
    }
    // combine the final color of the overlay texture
    vec4 img = get(v_texCoords);
    vec4 result = vec4(mix(outlineColor, img.rgb, img.a), amax);

    // finally, compose the overlay with the background
    gl_FragColor = vec4(mix(background, result.rgb, result.a),1.0);






  //gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
//  	vec2 uv = v_texCoords.xy;
//
//      vec3 targetCol = vec3(0,1,0); //The color of the outline
//
//      vec4 finalCol = vec4(0);
//
//      float rads = ((360.0 / float(SAMPLES)) * PI) / 180.0;	//radians based on SAMPLES
//
//      for(int i = 0; i < SAMPLES; i++)
//      {
//          if(finalCol.w < 0.1)
//          {
//          	float r = float(i + 1) * rads;
//      		vec2 offset = vec2(cos(r) * 0.3, -sin(r)) * MAG; //calculate vector based on current radians and multiply by magnitude
//      		finalCol = texture2D(u_texture, uv + offset);	//render the texture to the pixel on an offset UV
//              if(finalCol.w > 0.0)
//              {
//                  finalCol.xyz = targetCol;
//              }
//          }
//      }
//
//      vec4 tex = texture(u_texture, uv);
//      if(tex.w > 0.0)
//      {
//       	finalCol = tex;   //if the centered texture's alpha is greater than 0, set finalcol to tex
//      }
//
//  	gl_FragColor = finalCol;




//#ifdef GL_ES
//precision mediump float;
//#endif
//varying vec4 v_color;
//varying vec2 v_texCoords;
//uniform sampler2D u_texture;
//
//uniform vec2 resolution;
//
//
//#define width 0.1
//#define PI 3.14159265359
//#define SAMPLES 4
//#define MAG 0.02
//
//void main()
//{
//
//	vec2 uv = gl_FragCoord.xy / resolution.xy;
//
//    vec3 targetCol = vec3(0,1,0); //The color of the outline
//
//    vec4 finalCol = vec4(0);
//
//    float rads = ((360.0 / float(SAMPLES)) * PI) / 180.0;	//radians based on SAMPLES
//
//    for(int i = 0; i < SAMPLES; i++)
//    {
//        if(finalCol.w < 0.1)
//        {
//        	float r = float(i + 1) * rads;
//    		vec2 offset = vec2(cos(r) * 0.3, -sin(r)) * MAG; //calculate vector based on current radians and multiply by magnitude
//    		finalCol = texture2D(u_texture, uv + offset);	//render the texture to the pixel on an offset UV
//            if(finalCol.w > 0.0)
//            {
//                finalCol.xyz = targetCol;
//            }
//        }
//    }
//
//    vec4 tex = texture(u_texture, uv);
//    if(tex.w > 0.0)
//    {
//     	finalCol = tex;   //if the centered texture's alpha is greater than 0, set finalcol to tex
//    }
//
//	gl_FragColor = finalCol;



//  vec4 c= texture2D(u_texture, vec2(v_texCoords.x,v_texCoords.y));
// if(c.a == 0){
//   vec4 c1= texture2D(u_texture, vec2(v_texCoords.x - 0.004,v_texCoords.y));
//   vec4 c2= texture2D(u_texture, vec2(v_texCoords.x + 0.004,v_texCoords.y));
//   vec4 c3= texture2D(u_texture, vec2(v_texCoords.x,v_texCoords.y  - 0.004));
//   vec4 c4= texture2D(u_texture, vec2(v_texCoords.x,v_texCoords.y + 0.004));
//   if(c1.a != 0 || c2.a != 0 || c3.a != 0 || c4.a != 0){
//       gl_FragColor = vec4(0.5,0.5,0,1);
//   }else{
//       gl_FragColor = c;
//   }
// }else{
//   gl_FragColor = c;
// }

}
