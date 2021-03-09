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
		Display.Create(512, 512, "Window", true);
		
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
		
		Texture texture = Texture.LoadTexture("res/wall.png");
		
		instance.SetAuxUniform("uTexture", texture);
		
		Display.setClearColor(0, 0, 0);
		Display.ShowWindow();
		
		while (!Display.windowShouldClose()) {
			Display.PollEvents();
			
			Display.ClearScreen();
			renderer.RenderMesh(instance, mesh);
			
			Display.SwapBuffers();
		}
		
		loader.Terminate();
		Display.Terminate();
	}

}
