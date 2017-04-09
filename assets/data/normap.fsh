/*
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
  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}
*/


#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
//attributes from vertex shader
varying LOWP vec4 v_color;
varying vec2 v_texCoords;

//our texture samplers
uniform sampler2D u_texture;   //diffuse map
uniform sampler2D u_normals;   //normal map

//values used for shading algorithm...
uniform vec2 Resolution;         //resolution of screen
uniform vec3 LightPos;           //light position, normalized
uniform vec3 LightPos1;           //light position, normalized
uniform vec3 LightPos2;           //light position, normalized
uniform LOWP vec4 LightColor;    //light RGBA -- alpha is intensity
uniform LOWP vec4 AmbientColor;  //ambient RGBA -- alpha is intensity
uniform vec3 Falloff;            //attenuation coefficients
uniform float motion;

void main() {
	//RGBA of our diffuse color
	vec4 DiffuseColor = texture2D(u_texture, v_texCoords);

	//RGB of our normal map
	vec3 NormalMap = texture2D(u_normals, v_texCoords).rgb;

vec3 Sum = vec3(0.0);

	//The delta position of light
	vec3 LightDir = vec3(LightPos.xy - (gl_FragCoord.xy / Resolution.xy), LightPos.z);
	LightDir.x *= Resolution.x / Resolution.y;
	float D = length(LightDir);
	vec3 N = normalize(NormalMap * 2.0 - 1.0);
	vec3 L = normalize(LightDir);
	vec3 Diffuse = (LightColor.rgb * LightColor.a) * max(dot(N, L), 0);//todo hack this when NormalMap is null
	vec3 Ambient = AmbientColor.rgb * AmbientColor.a;
	float Attenuation = 1.0 / ( Falloff.x + (Falloff.y*D) + (Falloff.z*D*D) );
	vec3 Intensity = Ambient + Diffuse * Attenuation;
	vec3 FinalColor = DiffuseColor.rgb * Intensity;
	Sum += FinalColor;

    vec3 LightDir = vec3(LightPos1.xy - (gl_FragCoord.xy / Resolution.xy), LightPos1.z);
    LightDir.x *= Resolution.x / Resolution.y;
    float D = length(LightDir);
    vec3 N = normalize(NormalMap * 2.0 - 1.0);
    vec3 L = normalize(LightDir);
    vec3 Diffuse = (LightColor.rgb * LightColor.a) * max(dot(N, L), 0);//todo hack this when NormalMap is null
    vec3 Ambient = AmbientColor.rgb * AmbientColor.a;
    float Attenuation = 1.0 / ( Falloff.x + (Falloff.y*D) + (Falloff.z*D*D) );
    vec3 Intensity = Ambient + Diffuse * Attenuation;
    vec3 FinalColor = DiffuseColor.rgb * Intensity;
    Sum += FinalColor;

    vec3 LightDir = vec3(LightPos2.xy - (gl_FragCoord.xy / Resolution.xy), LightPos2.z);
    LightDir.x *= Resolution.x / Resolution.y;
    float D = length(LightDir);
    vec3 N = normalize(NormalMap * 2.0 - 1.0);
    vec3 L = normalize(LightDir);
    vec3 Diffuse = (LightColor.rgb * LightColor.a) * max(dot(N, L), 0);//todo hack this when NormalMap is null
    vec3 Ambient = AmbientColor.rgb * AmbientColor.a;
    float Attenuation = 1.0 / ( Falloff.x + (Falloff.y*D) + (Falloff.z*D*D) );
    vec3 Intensity = Ambient + Diffuse * Attenuation;
    vec3 FinalColor = DiffuseColor.rgb * Intensity;
    Sum += FinalColor;

	gl_FragColor = v_color * vec4(Sum, DiffuseColor.a);
}