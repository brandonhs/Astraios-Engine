#version 330 core
out vec4 FragColor;

in vec2 TexCoord;

uniform sampler2D ourTexture;

void main() {
    FragColor = texture(ourTexture, vec2(1.-TexCoord.x,1.-TexCoord.y));
}
