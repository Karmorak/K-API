package com.karmorak.api.funktions;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GraphicFunktions extends GameFunktions {
	
	public static Sprite colorize(FileHandle grayscale, Color color, float intensity) {	
		Pixmap pix = new Pixmap(grayscale);
		Pixmap pix2 = new Pixmap((int)pix.getWidth(),(int) pix.getHeight(), Format.RGBA8888);
		pix2.setBlending(Pixmap.Blending.None);
		
		for(int x = 0; x < pix.getWidth(); x++) {
			for(int y = 0; y < pix.getHeight(); y++) {
				int pixel = pix.getPixel(x, y);
				Color c = new Color(pixel);
				if(c.a == 1f) {
					pix2.drawPixel(x, y, Color.rgba8888(color.r * (c.r*intensity), color.g * (c.g*intensity), color.b * (c.b*intensity), color.a));
				}
			}
		}	
		Sprite s = new Sprite(new Texture(pix2));
		if (!s.getTexture().getTextureData().isPrepared()) {
			s.getTexture().getTextureData().prepare();
		}
		pix.dispose();
		pix2.dispose();
		return s;
	}
	public static Texture colorize(TextureRegion grayscale, Color color, float intensity) {		
		
		Texture texture = grayscale.getTexture();
		if (!texture.getTextureData().isPrepared()) {
		    texture.getTextureData().prepare();
		}
		Pixmap pix = texture.getTextureData().consumePixmap();
		Pixmap pix2 = new Pixmap((int)grayscale.getRegionWidth(),(int) grayscale.getRegionHeight(), Format.RGBA8888);
		pix2.setBlending(Pixmap.Blending.None);
		
		for (int x = 0; x < grayscale.getRegionWidth(); x++) {
		    for (int y = 0; y < grayscale.getRegionHeight(); y++) {
		        int pixel = pix.getPixel(grayscale.getRegionX() + x, grayscale.getRegionY() + y);		        
		        Color c = new Color(pixel);
				if(c.a == 1f) {
					pix2.drawPixel(x, y, Color.rgba8888(color.r * (c.r*intensity), color.g * (c.g*intensity), color.b * (c.b*intensity), color.a));
				}
		    }
		}
		Texture t = new Texture(pix2);
		//texture.dispose();
		//pix.dispose();
		pix2.dispose();
		return t;
	}
	
	public static Texture colorize(Texture grayscale, Color color, float intensity) {			
		if (!grayscale.getTextureData().isPrepared()) {
			grayscale.getTextureData().prepare();
		}
		Pixmap pix = grayscale.getTextureData().consumePixmap();
		
		Pixmap pix2 = new Pixmap((int)pix.getWidth(),(int) pix.getHeight(), Format.RGBA8888);
		pix2.setBlending(Pixmap.Blending.None);
		
		for(int x = 0; x < pix.getWidth(); x++) {
			for(int y = 0; y < pix.getHeight(); y++) {
				int pixel = pix.getPixel(x, y);
				Color c = new Color(pixel);
				if(c.a == 1f) {
					pix2.drawPixel(x, y, Color.rgba8888(color.r * (c.r*intensity), color.g * (c.g*intensity), color.b * (c.b*intensity), color.a));
				}
			}
		}	
		Texture s = new Texture(pix2);
		pix.dispose();
		pix2.dispose();
		return s;
	}
	
	public static Texture colorize2(FileHandle grayscale, Color color, float intensity, boolean mipmap) {			
//		if (!grayscale.getTextureData().isPrepared()) {
//			grayscale.getTextureData().prepare();
//		}
		Pixmap pix = new Pixmap(grayscale);	
		
		Pixmap pix2 = new Pixmap((int)pix.getWidth(),(int) pix.getHeight(), Format.RGBA8888);
		pix2.setBlending(Pixmap.Blending.None);
		
		for(int x = 0; x < pix.getWidth(); x++) {
			for(int y = 0; y < pix.getHeight(); y++) {
								
				int pixel = pix.getPixel(x, y);
				Color c = new Color(pixel);
				
				if(c.a == 1f) {
					if(c.r == 0) {
						pix2.drawPixel(x, y, Color.rgba8888(color.r * (1*intensity), color.g * (1*intensity), color.b * (1*intensity), color.a));
					} else {	
						pix2.drawPixel(x, y, Color.rgba8888(color.r * (c.r*intensity), color.g * (c.g*intensity), color.b * (c.b*intensity), color.a));
					}
					
				}
			}
		}	
		Texture s = new Texture(pix2, mipmap);
		pix.dispose();
		pix2.dispose();
		return s;
	}
	public static Texture colorize2(TextureRegion grayscale, Color color, float intensity) {		
		
		if (!grayscale.getTexture().getTextureData().isPrepared()) {
			grayscale.getTexture().getTextureData().prepare();
		}
		Pixmap pix = grayscale.getTexture().getTextureData().consumePixmap();
		Pixmap pix2 = new Pixmap((int)grayscale.getRegionWidth(),(int) grayscale.getRegionHeight(), Format.RGBA8888);
		pix2.setBlending(Pixmap.Blending.None);
		
		for (int x = 0; x < grayscale.getRegionWidth(); x++) {
		    for (int y = 0; y < grayscale.getRegionHeight(); y++) {
		        int pixel = pix.getPixel(grayscale.getRegionX() + x, grayscale.getRegionY() + y);		        
		        Color c = new Color(pixel);
				if(c.a == 1f) {
					if(c.r == 0) {
						pix2.drawPixel(x, y, Color.rgba8888(color.r * (1*intensity), color.g * (1*intensity), color.b * (1*intensity), color.a));
					} else {
						pix2.drawPixel(x, y, Color.rgba8888(color.r * (c.r*intensity), color.g * (c.g*intensity), color.b * (c.b*intensity), color.a));
					}
					
				}
		    }
		}
		Texture t = new Texture(pix2);
		//texture.dispose();
		//pix.dispose();
		pix2.dispose();
		return t;
	}
	
	public static Texture colorize2(Texture grayscale, Color color, float intensity) {			
		if (!grayscale.getTextureData().isPrepared()) {
			grayscale.getTextureData().prepare();
		}
		Pixmap pix = grayscale.getTextureData().consumePixmap();		
		Pixmap pix2 = new Pixmap((int)pix.getWidth(),(int) pix.getHeight(), Format.RGBA8888);
		pix2.setBlending(Pixmap.Blending.None);
		
		for(int x = 0; x < pix.getWidth(); x++) {
			for(int y = 0; y < pix.getHeight(); y++) {
				int pixel = pix.getPixel(x, y);
				Color c = new Color(pixel);
				if(c.a == 1f) {
					if(c.r == 0) {
						pix2.drawPixel(x, y, Color.rgba8888(color.r * (1*intensity), color.g * (1*intensity), color.b * (1*intensity), color.a));
					} else {
						pix2.drawPixel(x, y, Color.rgba8888(color.r * (c.r*intensity), color.g * (c.g*intensity), color.b * (c.b*intensity), color.a));
					}
					
				}
			}
		}	
		Texture t = new Texture(pix2);
		
		pix.dispose();
		pix2.dispose();
		return t;
	}
	
	public static Pixmap colorize2(Pixmap grayscale, Color color, float intensity) {		
		
		Pixmap pix2 = new Pixmap((int)grayscale.getWidth(),(int) grayscale.getHeight(), Format.RGBA8888);
		pix2.setBlending(Pixmap.Blending.None);
		
		for(int x = 0; x < grayscale.getWidth(); x++) {
			for(int y = 0; y < grayscale.getHeight(); y++) {
				int pixel = grayscale.getPixel(x, y);
				Color c = new Color(pixel);
				if(c.a == 1f) {
					if(c.r == 0) {
						pix2.drawPixel(x, y, Color.rgba8888(color.r * (1*intensity), color.g * (1*intensity), color.b * (1*intensity), color.a));
					} else {
						pix2.drawPixel(x, y, Color.rgba8888(color.r * (c.r*intensity), color.g * (c.g*intensity), color.b * (c.b*intensity), color.a));
					}
					
				}
			}
		}	
		grayscale.dispose();
		return pix2;
	}
	
	public static Sprite makeGreyScale(Sprite s, float alpha) {
		if (!s.getTexture().getTextureData().isPrepared()) {
		    s.getTexture().getTextureData().prepare();
		}
		
		Pixmap pix = s.getTexture().getTextureData().consumePixmap();
			
		for(int x = 0; x < s.getWidth(); x++) {
			for(int y = 0; y < s.getHeight(); y++) {
				int c = pix.getPixel(x, y);		
				Color color = new Color(c);		
				if(color.a >= alpha) {
					float c2 = 0.299f * color.r + 0.587f * color.g + 0.114f*color.b;
					pix.drawPixel(x, y, Color.rgba8888(c2, c2, c2, 1f));
				}
			}
		}
		Sprite s2 = new Sprite(new Texture(pix));
		pix.dispose();
		return s2;		
	}
	
	public static Sprite invertColor(Sprite s) {
		if (!s.getTexture().getTextureData().isPrepared()) {
		    s.getTexture().getTextureData().prepare();
		}
		Pixmap pix = s.getTexture().getTextureData().consumePixmap();		
		for(int x = 0; x < s.getWidth(); x++) {
			for(int y = 0; y < s.getHeight(); y++) {
				int c = pix.getPixel(x, y);
				int c2 = createLinear(c, -1, 0);		
				pix.drawPixel(x, y, c2);
			}
		}
		Sprite s2 = new Sprite(new Texture(pix));
		pix.dispose();
		return s2;		
	}
	
	public static int createLinear(int color, int a, int b) {	
		return a*color+b;
	}
	
	public static Sprite createColorBar() {
		Pixmap pix3 = new Pixmap(255*6, 1, Format.RGB888);
		pix3.setBlending(Pixmap.Blending.None);
		for (float i = 0; i < 255; i++) {
			pix3.drawPixel((int)i, 0, Color.rgba8888(1f, i/255f, 0f, 1f));
		}			
		for (float i = 0; i < 255; i++) {
			pix3.drawPixel(255*2 - (int)i-1, 0, Color.rgba8888(i/255f, 1f, 0f, 1f));
		}			
		for (float i = 0; i < 255; i++) {
			pix3.drawPixel(255*2 + (int)i-1, 0, Color.rgba8888(0f, 1f, i/255f, 1f));
		}
		for (float i = 0; i < 255; i++) {
			pix3.drawPixel(255*4 - (int)i-2, 0, Color.rgba8888(0f, i/255f, 1f, 1f));
		}
		for (float i = 0; i < 255; i++) {
			pix3.drawPixel(255*4 + (int)i-2, 0, Color.rgba8888(i/255f, 0f, 1f, 1f));
		}
		
		for (float i = 0; i < 255; i++) {
			pix3.drawPixel(255*6 - (int)i-3, 0, Color.rgba8888(1f, 0f, i/255f, 1f));
		}
		
		Sprite t = new Sprite(new Texture(pix3));
		pix3.dispose();	
		return t;
	}
	
	public static Color getRandomColor() {
		float f = (float) (Math.random() * 6);
		
		Color c = new Color(0,0,0,0);
		
		if(f < 1f) {
			 c =  new Color(1f, f, 0, 1);
			 
		} else if (f >= 1 && f <= 2) {
			 c =  new Color(1-(f-1), 1f, 0, 1);	
		}		
		 else if (f > 2 && f <= 3) {
			 c =  new Color(0f, 1f, (f-2f), 1);	
		}		
		 else if (f > 3 && f <= 4) {
			 c =  new Color(0f,1- (f-3f), 1f, 1);	
		}		
		
		 else if (f > 4 && f <= 5) {
			 c =  new Color((f-4f), 0f, 1f, 1);	
		}		
		 else if (f >= 5 && f <= 6) {
			 c =  new Color(1f, 0f,1- (f-5f), 1);	
			 
		 }
		return c;
	}
	public static boolean isEqualColor(Color a, Color b) {
		if(a.toString().equals(b.toString())) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param color
	 * @return
	 * 
	 * the number shouldnt be greater than 1f but if its greater its always 1f
	 * if its smaller than 0f the value will be 0f
	 */
	public static Color FloattoColor(float color) {
		
		float f = 1f;
		if(color < 1f) {
			if(color > 0f) f  = color * 6;
			else f = 0f;
		}
		
		Color c = new Color(0,0,0,0);
		
		if(f < 1f) {
			c =  new Color(1f, f, 0, 1);
			
		} else if (f >= 1 && f <= 2) {
			c =  new Color(1-(f-1), 1f, 0, 1);
			
		} else if (f > 2 && f <= 3) {
			c =  new Color(0f, 1f, (f-2f), 1);	
			
		} else if (f > 3 && f <= 4) {
			c =  new Color(0f,1- (f-3f), 1f, 1);
			
		} else if (f > 4 && f <= 5) {
			c =  new Color((f-4f), 0f, 1f, 1);	
			
		} else if (f >= 5 && f <= 6) {
			c =  new Color(1f, 0f, 1- (f-5f), 1);
			
		}
		
		return c;		
	}
	
	
	
	/**
	 * @param color
	 * @return
	 * 
	 * one of the rgb values should always be 1f and one 0f. the third is the free one.
	 * like r=1f g=0,65f b = 0f;
	 * so it not supports gray scales 
	 * 
	 */
	
	public static float ColortoFloat(Color color) {
		float r = -1;
		
		if(color.r == 1f) {
			if(color.g == 0f) {
				r = color.b +5;
			} else {
				r = color.g;
			}
		} else if(color.g == 1f) {
			if(color.r == 0f) {
				r = color.b + 2;
			} else {
				r = color.r + 1;
			}
		} else if(color.b == 1f) {
			if(color.a == 0f) {
				r = color.g + 3;
			} else {
				r = color.a + 4;
			}
		}
		
		if(r != -1) r = r / 6;
		
		return r;		
	}

}
