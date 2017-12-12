uniform mat4 u_Matrix;

attribute vec4 a_Position;
attribute vec4 a_Color;
attribute vec2 a_TextureCoordinates;


varying vec2   v_TextureCoordinates;
varying vec4   v_Color;

void main()
{
v_Color = a_Color; // a_Color;
v_TextureCoordinates = a_TextureCoordinates;
gl_Position = u_Matrix * a_Position;
}