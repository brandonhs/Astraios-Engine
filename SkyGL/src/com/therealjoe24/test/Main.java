package com.therealjoe24.test;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.util.Arrays;
import org.joml.Math;
import org.joml.Vector3f;

import com.therealjoe24.skygl.Display;
import com.therealjoe24.skygl.Input;
import com.therealjoe24.skygl.renderer.BufferLoader;
import com.therealjoe24.skygl.renderer.PrimitiveMesh;
import com.therealjoe24.skygl.renderer.Renderer;
import com.therealjoe24.skygl.renderer.ShaderInstance;
import com.therealjoe24.skygl.renderer.ShaderObject;
import com.therealjoe24.skygl.renderer.ShaderProgram;
import com.therealjoe24.skygl.renderer.camera.PerspectiveCamera;
import com.therealjoe24.skygl.renderer.objects.Model;
import com.therealjoe24.skygl.renderer.text.TextElement;
import com.therealjoe24.skygl.renderer.texture.Texture;

public class Main {

	static float[] positions = {
			-1,  1, 0, 
			-1, -1, 0, 
			 1, -1, 0, 
			 1,  1, 0,
	};
	
	static float[] texturePositions = {
			 0,  1, 
			 0,  0, 
			 1,  0, 
			 1,  1
	};
	
	static int[] indices = {
			0, 1, 2,
			2, 3, 0,
	};
	
	public static void main(String[] args) {
		Display.Create(512, 512, "Window", true);
		Input.Init();
		
		String vsSource = ShaderObject.LoadSource("shader.vert");
		String fsSource = ShaderObject.LoadSource("shader.frag");
		ShaderObject vs = new ShaderObject(GL_VERTEX_SHADER, vsSource);
		ShaderObject fs = new ShaderObject(GL_FRAGMENT_SHADER, fsSource);
		ShaderObject[] shaders = { vs, fs };
		ShaderProgram program = new ShaderProgram(Arrays.asList(shaders));
		
		BufferLoader loader = new BufferLoader();
		Renderer renderer = new Renderer();
		
		PerspectiveCamera camera = new PerspectiveCamera(
				(float)Math.toRadians(45f), 0.0001f, 100000f, 
				new Vector3f(0, 5, 5), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 1, 0));
		
		Texture texture = Texture.LoadTexture("res/wall.png");
		
		PrimitiveMesh mesh = loader.LoadToVAO(indices, positions, texturePositions);
		Model model = new Model(mesh);
		ShaderInstance instance = new ShaderInstance(program);
		
		TextElement el = new TextElement(loader, "Text Rendering is Fun!", 512);
		
		instance.SetAuxUniform("uTexture", el.getTexture());
		instance.SetCamera(camera);
		
		Display.setClearColor(0, 0, 0);
		Display.ShowWindow();
		
		while (!Display.windowShouldClose()) {
			Display.ClearScreen();
			
			model.RotateY(0.01f);
			renderer.RenderModel(instance, model);
			
			Display.SwapBuffers();
			
			Input.Update();
		}
		
		texture.Dispose();
		loader.Terminate();
		Display.Terminate();
	}

}
