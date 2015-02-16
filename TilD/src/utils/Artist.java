package utils;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {

	/* Texture should be 64x64 for 20x15 map */
	private static final int HEIGHT = 960;
	private static final int WIDTH = 1280;


	public Artist() {
		// TODO Auto-generated constructor stub
	}

	public static void BeginSession() {
		Display.setTitle("TilD");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
			// Make keyboard
			Keyboard.create();

		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// top left hand corner is 0,0
		GL11.glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		// enable textures
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// enable double buffer
		GL11.glEnable(GL11.GL_DOUBLEBUFFER);

	}

	public static void drawQuad(float x, float y, float sizeX, float sizeY) {
		// draw quad
		GL11.glBegin(GL11.GL_QUADS); // moves clockwise
		GL11.glVertex2f(x, y); // top left
		GL11.glVertex2f(x + sizeX, y); // top right
		GL11.glVertex2f(x + sizeX, y + sizeY); // bottom right
		GL11.glVertex2f(x, y + sizeY); // bottom left
		GL11.glEnd();
	}

	public static void drawQuadTex(
			Texture texture, 
			float x, 
			float y, 
			float width,
			float height) {
		// bind
		texture.bind();
		// translate
		GL11.glTranslatef(x, y, 0);
		// draw
		GL11.glBegin(GL11.GL_QUADS);
		// top left
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(0, 0); 
		// top right
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(width, 0); 
		// bottom right
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(width, height); 
		// bottom left
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(0, height); 	
		
		GL11.glEnd();
		// prevent screen tearing
		GL11.glLoadIdentity();

		
		
	}

	public static Texture loadTexture(String path, String fileType){
		Texture tex =null;
		InputStream input= ResourceLoader.getResourceAsStream(path);
		try {
			tex= TextureLoader.getTexture(fileType, input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tex;
		
	}

	/* Stubs */
	public static void drawTriangle(float x, float y, float width, float height) {

	}

	public static void drawTriangleTex(float x, float y, float width,
			float height) {

	}

}
