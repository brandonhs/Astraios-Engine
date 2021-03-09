package com.therealjoe24.test;

import com.therealjoe24.skygl.Display;
import com.therealjoe24.skygl.renderer.BufferLoader;
import com.therealjoe24.skygl.renderer.PrimitiveMesh;
import com.therealjoe24.skygl.renderer.Renderer;
import com.therealjoe24.skygl.renderer.ShaderInstance;
import com.therealjoe24.skygl.renderer.ShaderObject;
import com.therealjoe24.skygl.renderer.ShaderProgram;
import com.therealjoe24.skygl.texture.Texture;

import static org.lwjgl.opengl.GL45.*;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Display.Create(512, 512, "Window");
		
		float[] positions = {
			-1,  1, 0, 
			-1, -1, 0, 
			 1, -1, 0, 
			 1,  1, 0,
		};
		float[] texturePositions = {
			 1,  1, 
			 1,  0, 
			 0,  0, 
			 0,  1
		};
		int[] indices = {
			0, 1, 2,
			2, 3, 0,
		};
 		
		BufferLoader loader = new BufferLoader();
		PrimitiveMesh mesh = loader.LoadToVAO(indices, positions, texturePositions);
		
		String vsSource = ShaderObject.LoadSource("res/shader.vert");
		String fsSource = ShaderObject.LoadSource("res/shader.frag");
		
		ShaderObject vs = new ShaderObject(GL_VERTEX_SHADER, vsSource);
		ShaderObject fs = new ShaderObject(GL_FRAGMENT_SHADER, fsSource);
		ShaderObject[] shaders = {
				vs, fs
		};
		ShaderProgram program = new ShaderProgram(Arrays.asList(shaders));
		
		ShaderInstance instance = new ShaderInstance(program);
		
		Renderer renderer = new Renderer();
		
		Texture texture = Texture.LoadTexture("res/TheRealJoe24.png");
		Texture texture2 = Texture.LoadTexture("res/wall.png");
		
		instance.SetAuxUniform("ourTexture", 0);
		
		Display.setClearColor(0, 0, 0);
		Display.ShowWindow();
		
		long i = 0;
		
		Texture current = texture;
		
		while (!Display.windowShouldClose()) {
			Display.PollEvents();
			
			Display.ClearScreen();
			
			if (i % 8000 == 0) {
				if (current == texture)
					current = texture2;
				else if (current == texture2)
					current = texture;
			}
			current.bind();
			renderer.RenderMesh(instance, mesh);
			
			Display.SwapBuffers();
			
			i++;
		}
		
		loader.Terminate();
		Display.Terminate();
	}

}
