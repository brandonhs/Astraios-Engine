////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2021 TheRealJoe24
// 
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
////////////////////////////////////////////////////////////////////////////////
#version 330 core
out vec4 FragColor;

in vec2 TexCoord;

uniform sampler2D uTexture;

void main() {
	vec4 result = texture(uTexture,TexCoord);
	FragColor = result;
}
