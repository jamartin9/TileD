package utils;
import java.io.IOException;
import java.io.InputStream;
import main.TextBoxArea;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {

	/* Texture should be 64x64 for 20x15 map */
	private static int HEIGHT = 960;
	private static int WIDTH = 1280;
	private static int SCALEX = 64;
	private static int SCALEY = 64;
	private static int arWidth = 20;
	private static int arHeight = 15;

	public static void BeginSession() {
		Display.setTitle("TilD");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
			Display.setFullscreen(true);
			// Make keyboard
			Keyboard.create();
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

	public static int getArWidth() {
		return arWidth;
	}

	public static void setArWidth(int arWidth) {
		Artist.arWidth = arWidth;
	}

	public static int getArHeight() {
		return arHeight;
	}

	public static void setArHeight(int arHeight) {
		Artist.arHeight = arHeight;
	}

}
