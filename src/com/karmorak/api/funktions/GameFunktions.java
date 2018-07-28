//v1.1
package com.karmorak.api.funktions;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import javax.imageio.ImageIO;
import com.badlogic.gdx.Gdx;

public class GameFunktions {
	
	
	/**
	 * width/1920
	 * @return
	 */
	public static float getScaleMultiX() {
		int width = Gdx.graphics.getWidth();		
		if(width == 1920) {
			return 1;
		} else {
			float c = width / 1920;
			return c;			
			
		}	
	}
	
	/**
	 * height/1080
	 * @return
	 */
	public static float getScaleMultiY() {
		float height = Gdx.graphics.getHeight();	
		
		if(height == 1080) {
			return 1;
		} else {
			float c = height / 1080f;
			return c;				
		}	
	}
	
	/**
	 * 1920/width
	 * @return
	 */
	public static float getScaleMultiX2() {
		int width = Gdx.graphics.getWidth();		
		if(width == 1920) {
			return 1;
		} else {
			float c = 1920 /width;
			return c;			
			
		}	
	}
	/**
	 * 1080/height
	 * @return
	 */
	public static float getScaleMultiY2() {
		float height = Gdx.graphics.getHeight();	
		
		if(height == 1080) {
			return 1;
		} else {
			float c = 1080f / height;
			return c;				
		}	
	}
	
	/**
	 * @param type
	 * @return
	 * 
	 * 0 == smallest
	 * 1 == average smallest
	 * 2 == average biggest
	 * >2 == biggest
	 */
	
	public static float getScaleMulti(short type) {
		float x = getScaleMultiX2();
		float y = getScaleMultiY2();	
		
		if(type == 0) {
			if(x < y) {
				return x;
			} else {
				return y;
			}
		} else if (type == 1) {
			if(x < y) {
				float c = x/y;
				return c;
			} else {
				float c = y/x;
				return c;
			}
		} else if (type == 2) {
			if(x < y) {
				float c = y/x;
				return c;
			} else {
				float c = x/y;
				return c;
			}
		} else {
			if(x < y) {
				return y;
			} else {
				return x;
			}
		}
	}
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if(Objects.equals(value, entry.getValue())) {
				return entry.getKey();
			}
		}		
		return null;	
	}
	
	public static BufferedImage makeImage(BufferedImage image, int image_width, int image_height, String path) throws IOException {
		int x = (int) (image_width*getScaleMultiY());
		int y = (int) (image_height*getScaleMultiY());
		BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);		
		img.getGraphics().drawImage(ImageIO.read(GameFunktions.class.getClassLoader().getResourceAsStream(path)), 0, 0, x, y, null);
		return img;
	}
	
	


}
