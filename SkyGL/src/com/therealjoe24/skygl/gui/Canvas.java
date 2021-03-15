package com.therealjoe24.skygl.gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL30.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.GL_FRAGMENT_SHADER;

import com.therealjoe24.skygl.Display;
import com.therealjoe24.skygl.renderer.BufferLoader;
import com.therealjoe24.skygl.renderer.MeshData;
import com.therealjoe24.skygl.renderer.PrimitiveMesh;
import com.therealjoe24.skygl.renderer.ShaderInstance;
import com.therealjoe24.skygl.renderer.ShaderObject;
import com.therealjoe24.skygl.renderer.ShaderProgram;
import com.therealjoe24.skygl.renderer.texture.Texture;

/**
 * Stores individual elements
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
 *
 */
public class Canvas {
	
	private List<CanvasElement> _elements = new ArrayList<CanvasElement>();
	
	private Texture _textureBuffer;
	private PrimitiveMesh _quad;
	
	private ShaderProgram _program;
	private ShaderInstance _instance;
	
	public Canvas(BufferLoader loader) {
		ShaderObject vs = new ShaderObject(GL_VERTEX_SHADER, ShaderObject.LoadSource("canvas.vert"));
		ShaderObject fs = new ShaderObject(GL_FRAGMENT_SHADER, ShaderObject.LoadSource("canvas.frag"));
		_program = new ShaderProgram(Arrays.asList(new ShaderObject[] {
				vs, fs
		}));
		_instance = new ShaderInstance(_program);
		
		UpdateTexture(loader);
		
		Display.AddResizeCallbackFunc(new Display.SkyGLDisplayResizeFunc() {
			@Override
			public void invoke() {
				UpdateTexture(loader);
			}
		});
	}

	public void AddElement(BufferLoader loader, CanvasElement el) {
		_elements.add(el);
	}
	
	public Texture getTexture() {
		return _textureBuffer;
	}
	
	public PrimitiveMesh getQuad() {
		return _quad;
	}
	
	public void UpdateTexture(BufferLoader loader) {
		BufferedImage image = new BufferedImage(Display.getWidth(), Display.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D)image.getGraphics();
		for (CanvasElement el : _elements) {
			el.RenderToCanvas(g);
		}
		ByteBuffer buf = CreateByteBuffer(image);
		_textureBuffer = new Texture(Display.getWidth(), Display.getHeight(), buf);
		/* TODO: delete or reuse previous buffer */
		_quad = loader.LoadToVAO(new MeshData(_textureBuffer, 1.0f));
		_instance.SetAuxUniform("uTexture", _textureBuffer);
	}
	
	public ShaderInstance getShaderInstance() {
		return _instance;
	}
	
	public ShaderProgram getShaderProgram() {
		return _program;
	}
	
	public void Dispose() {
		_textureBuffer.Dispose();
	}
	
	private static ByteBuffer CreateByteBuffer(BufferedImage image) {
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
	    tx.translate(0, -image.getHeight());
	    AffineTransformOp op = new AffineTransformOp(tx,
	        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    image = op.filter(image, null);
		ByteBuffer buf = ByteBuffer.allocateDirect(image.getWidth()*image.getHeight()*4);
		DataBuffer db = image.getRaster().getDataBuffer();
		byte[] pix = ((DataBufferByte)(db)).getData();
		buf.put(pix);
		buf.flip();
		return buf;
	}
	
}
