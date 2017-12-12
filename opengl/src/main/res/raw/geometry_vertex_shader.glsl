uniform mat4 u_Matrix;
uniform vec4 u_Color;
attribute vec4 a_Position;

void main()
{
gl_Position = u_Matrix * a_Position;
}


