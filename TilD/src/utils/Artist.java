package utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {

	/* Texture should be 64x64 for 20x15 map */
	private static int HEIGHT = 960;
	private static int WIDTH = 1280;
	private static int SCALEX = 64;
	private static int SCALEY = 64;

	public static void BeginSession() {
		Display.setTitle("TilD");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
			// Make keyboard
			Keyboard.create();
			// resize Display
			Display.setResizable(true);
			Display.setLocation(0, 0);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// top left hand corner is 0,0
		GL11.glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		// enable textures
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		// fix lines between textures
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		// change filtering
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		// enable blending
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
	
	public static void initLight(Vector4f pos, Vector4f spec,Vector4f amb,Vector4f diff) {
		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(pos.x).put(pos.y).put(pos.z).put(pos.w).flip();

		FloatBuffer specular = BufferUtils.createFloatBuffer(4);
		specular.put(spec.x).put(spec.y).put(spec.z).put(spec.w).flip();

		FloatBuffer ambient = BufferUtils.createFloatBuffer(4);
		ambient.put(amb.x).put(amb.y).put(amb.z).put(amb.w).flip();

		FloatBuffer diffuse = BufferUtils.createFloatBuffer(4);
		diffuse.put(diff.x).put(diff.y).put(diff.z).put(diff.w).flip();

		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, specular);
		GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 20.0f);

		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPosition);
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, specular);
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, diffuse);
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, ambient);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT1);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);
	}
	
	public static int getHeight(){
		return HEIGHT;
	}
	
	
	public static int getWidth(){
		return WIDTH;
	}

	public static int getScaleX(){
		return SCALEX;
	}
	
	public static void setScaleX(int scale){
		SCALEX =scale;
	}
	
	public static int getScaleY(){
		return SCALEY;
	}
	
	public static void setScaleY(int scale){
		SCALEY =scale;
	}
	public static void setHeight(int height){
		HEIGHT=height;
	}
	public static void setWidth(int width){
		WIDTH=width;
	}
	public static void resize(){
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// top left hand corner is 0,0
		GL11.glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	/* Stubs */
	public static void drawTriangle(float x, float y, float width, float height) {

	}

	public static void drawTriangleTex(float x, float y, float width,
			float height) {

	}

}
