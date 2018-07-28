package com.karmorak.api;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Background {
	
	private static final float BACKGROUND_MOVESPEED = (float) 1.51;
	private static Texture BackGround;
	private static Texture BackGround2;
	private static int Background_X;
	private static int Background_Y;
	private static float width;
	private static float height;
	private static boolean firstStart;
	private static float scaleY;
	
	public static void initBackGround(float ScreenScale, String file) {
		scaleY = ScreenScale;
		BackGround = new Texture(Gdx.files.internal(file));
		BackGround2 = BackGround;
		
		width = (BackGround.getWidth() / scaleY);
		height = (BackGround.getHeight() / scaleY);
		Background_X = (int) -(width);
		Background_Y = 0;
		firstStart = true;	
	}
	
	private static int c;
	
	public static void updateBackGround() {		
		if(firstStart) {	
			firstStart = false;
		} else {
			if(Background_X <= 0) {
				Background_X+=(BACKGROUND_MOVESPEED/scaleY);
			} else {
				Background_X = (int) -(width);
			}
		}	
		if(c==Background_X) {
			Background_X = (int) -(width);
		}
		c= Background_X;
		
	}
	
	public static void drawBackGround(Batch batch) {
		batch.draw(BackGround, Background_X, Background_Y, width, height);
		batch.draw(BackGround2, width+Background_X, Background_Y , width , height);
	}

}
