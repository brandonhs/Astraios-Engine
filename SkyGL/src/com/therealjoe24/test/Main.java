package com.therealjoe24.test;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.FloatBuffer;
import java.util.Arrays;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import com.therealjoe24.skygl.Display;
import com.therealjoe24.skygl.Input;
import com.therealjoe24.skygl.camera.PerspectiveCamera;
import com.therealjoe24.skygl.objects.Model;
import com.therealjoe24.skygl.renderer.BufferLoader;
import com.therealjoe24.skygl.renderer.PrimitiveMesh;
import com.therealjoe24.skygl.renderer.Renderer;
import com.therealjoe24.skygl.renderer.ShaderInstance;
import com.therealjoe24.skygl.renderer.ShaderObject;
import com.therealjoe24.skygl.renderer.ShaderProgram;
import com.therealjoe24.skygl.texture.Texture;

public class Main {

	public static void main(String[] args) {
		Display.Create(512, 512, "Window", true);
		Input.Init();
		
		float[] positions = {
			-1,  1, 0, 
			-1, -1, 0, 
			 1, -1, 0, 
			 1,  1, 0,
		};
		float[] texturePositions = {
			 0,  1, 
			 0,  0, 
			 1,  0, 
			 1,  1
		};
		int[] indices = {
			0, 1, 2,
			2, 3, 0,
		};
		
		String vsSource = ShaderObject.LoadSource("shader.vert");
		String fsSource = ShaderObject.LoadSource("shader.frag");
		ShaderObject vs = new ShaderObject(GL_VERTEX_SHADER, vsSource);
		ShaderObject fs = new ShaderObject(GL_FRAGMENT_SHADER, fsSource);
		ShaderObject[] shaders = {
				vs, fs
		};
		ShaderProgram program = new ShaderProgram(Arrays.asList(shaders));
		
		BufferLoader loader = new BufferLoader();
		ShaderInstance instance = new ShaderInstance(program);
		Renderer renderer = new Renderer();
		
		PerspectiveCamera camera = new PerspectiveCamera(
				(float)Math.toRadians(45f), 0.0001f, 100000f, 
				new Vector3f(0, 5, 5), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 1, 0));
		
		Texture texture = Texture.LoadTexture("res/wall.png");
		
		PrimitiveMesh mesh = loader.LoadToVAO(indices, positions, texturePositions);
		Model model = new Model(mesh);
		
		instance.SetAuxUniform("uTexture", texture);
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
		
		loader.Terminate();
		Display.Terminate();
	}

}
