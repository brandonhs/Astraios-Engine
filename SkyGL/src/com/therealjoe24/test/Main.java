package com.therealjoe24.test;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.util.Arrays;

import org.joml.Math;
import org.joml.Vector3f;

import com.therealjoe24.skygl.Display;
import com.therealjoe24.skygl.Input;
import com.therealjoe24.skygl.gui.Canvas;
import com.therealjoe24.skygl.gui.elements.TextElement;
import com.therealjoe24.skygl.renderer.BufferLoader;
import com.therealjoe24.skygl.renderer.MeshData;
import com.therealjoe24.skygl.renderer.PrimitiveMesh;
import com.therealjoe24.skygl.renderer.Renderer;
import com.therealjoe24.skygl.renderer.ShaderInstance;
import com.therealjoe24.skygl.renderer.ShaderObject;
import com.therealjoe24.skygl.renderer.ShaderProgram;
import com.therealjoe24.skygl.renderer.camera.PerspectiveCamera;
import com.therealjoe24.skygl.renderer.objects.Model;
import com.therealjoe24.skygl.renderer.texture.Texture;

public class Main {
	
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
		
		Texture texture = Texture.LoadTexture("C:/users/brand/Downloads/ollie.png");
		
		Canvas canvas = new Canvas(loader);
		TextElement el = new TextElement("Ollie is cute :)", 50, 50);
		canvas.AddElement(loader, el);
		
		PrimitiveMesh mesh = loader.LoadToVAO(new MeshData(texture, 1));
		Model model = new Model(mesh);
		ShaderInstance instance = new ShaderInstance(program);
		
		instance.SetAuxUniform("uTexture", texture);
		instance.SetCamera(camera);
		
		Display.setClearColor(0, 0, 0);
		Display.ShowWindow();
		
		while (!Display.windowShouldClose()) {
			Display.ClearScreen();
			
			model.RotateY(0.05f);
			renderer.RenderModel(instance, model);
			
			renderer.RenderCanvas(canvas);
			
			Display.SwapBuffers();
			
			Input.Update();
		}
		
		canvas.Dispose();
		texture.Dispose();
		loader.Terminate();
		Display.Terminate();
	}

}
