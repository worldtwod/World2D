precision mediump float;
uniform vec4 u_Color;
void main()
{
gl_FragColor = u_Color;
gl_FragColor.rgb*=u_Color.a;
}