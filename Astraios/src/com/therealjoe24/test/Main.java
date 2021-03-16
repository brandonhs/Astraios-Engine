/*******************************************************************************
 * Copyright (C) 2021 TheRealJoe24
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
		/* Initialize the display and input manager */
		Display.Create(512, 512, "SkyGL 3D Demo", true);
		Input.Init();
		
		/* Load the shaders */
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
				new Vector3f(0, 0, 5), 
				new Vector3f(0, 0, 0), 
				new Vector3f(0, 1, 0));
		
		Texture texture = Texture.LoadTexture("res/wall.png");
		
		Canvas canvas = new Canvas();
		canvas.AddElement(new TextElement("SkyGL 3D Demo", 0.5f, 0.02f, 0, 1, 0, 1, 48));
		canvas.AddElement(new TextElement("Made by TheRealJoe24", 0.8f, 0.02f, 0.1f, 0.1f, 0.8f, 1, 48));
		TextElement el = new TextElement("", 0.03f, 0.02f, 0.8f, 0.1f, 0.3f, 1, 36);
		canvas.AddElement(el);
		
		PrimitiveMesh mesh = loader.LoadToVAO(new MeshData(texture, 1));
		Model model = new Model(mesh);
		ShaderInstance instance = new ShaderInstance(program);
		
		instance.SetAuxUniform("uTexture", texture);
		instance.SetCamera(camera);
		
		double time, newTime, dt;
		
		time = System.currentTimeMillis();
		
		Display.setClearColor(0, 0, 0);
		Display.ShowWindow();
		
		while (!Display.windowShouldClose()) {
			Display.ClearScreen();
			
			model.RotateY(0.003f);
			model.RotateX(0.02f);
			renderer.RenderModel(instance, model);
			
			newTime = System.currentTimeMillis();
			dt = newTime - time;
			int fps = (int)Math.round(1/(dt/1000));
			time = newTime;
			el.SetText(String.format("fps: %d", fps));
			
			canvas.Render();
			
			Display.SwapBuffers();
			
			Input.Update();
		}
		texture.Dispose();
		loader.Terminate();
		Display.Terminate();
	}

}
