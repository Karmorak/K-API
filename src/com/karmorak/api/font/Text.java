//v1.1
package com.karmorak.api.font;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.karmorak.api.Main;
import com.karmorak.api.Vector2;
import com.karmorak.api.funktions.GraphicFunktions;

public class Text {

	private OwnFont font;
	
	/** this is only important for multi lines
	 * 0 - default all lines will be drawn on the left side
	 * 1 - all lines will be drawn in the middle
	 * 2 - all lines will be drawn from the right side of the biggest line
	 * 
	 * 
	 */	
	public int pos_mode = 0;
	private String[] name;
	private Color color;
	private Vector2 pos;
	private float scale = 1f;
	private float max_width;
	
	private float abs_y = 0;
	
	private boolean cacheadded = false;
	
	private int longestword = 0;
	private float longwordlength = 0;
	
	private boolean thick = false; 
	
	/**
	 * 0 = namechanged
	 * 1 = colorchanged
	 * 2 = formatting changed
	 */
	private boolean[] changed = new boolean[] {false, false, false}; 
	private Sprite[] texturecache;

//	public Text(OwnFont font) {
//		this.font = font;
//		pos = new Vector2(0, 0);
//	}	

	public Text(OwnFont font, String name) {
		this.font = font;
		this.name = new String[] {name};		
		
		longwordlength = font.getWordBounds(name, thick).getWidth();
		
		this.pos = new Vector2(0, 0);
	}
	
	public Text(OwnFont font, String name[]) {
		this.font = font;
		this.name = new String[name.length];
		
		for(int i = 0; i < name.length; i++) {
			this.name[i] = name[i];
			
			
			if(font.getWordBounds(name[i], thick).getWidth() > longwordlength) {				
				longwordlength = font.getWordBounds(name[i], thick).getWidth();
			}
			if(this.name[longestword].length() < name[i].length()) {
				longestword = i;
			}
			
		}	
		
		this.name = name;
		this.pos = new Vector2(0, 0);
	}
	
	
	public void setName(String name) {
		this.name[0] = name;
		changed[0]=false;
		cacheadded = false;
		
		
		longwordlength = font.getWordBounds(name, thick).getWidth();
	}
	
	public void setName(String[] name) {
		this.name = name;
		changed[0]=false;
		cacheadded = false;
		
		for(int i = 0; i < name.length; i++) {			
			if(font.getWordBounds(name[i], thick).getWidth() > longwordlength) {				
				longwordlength = font.getWordBounds(name[i], thick).getWidth();
			}
			if(this.name[longestword].length() < name[i].length()) {
				longestword = i;
			}
		}
	}
	
	public void setName(int i, String name) {
		this.name[i] = name;
		changed[0]=false;
		cacheadded = false;
		
		if(font.getWordBounds(name, thick).getWidth() > longwordlength) {				
			longwordlength = font.getWordBounds(name, thick).getWidth();
		}
		if(this.name[longestword].length() < name.length()) {
			longestword = i;
		}
	}
	
	public String getName() {
		return name[0];
	}
	
	public String[] getNames() {
		return name;
	}
	
	public String getName(int i) {
		return name[i];
	}
	
	public void setThickFont(boolean bool) {
		if(bool != thick) {
			thick = bool;
			changed[2] = false;
			cacheadded = false;
		}	
	}
	
	public void setPosition(float x, float y) {
		if(!(pos.getX() == x && pos.getY() == y)) {
//			cacheadded = false;
		}		
		pos.setPosition(x, y);
	}

	
	
	public float getOffsetX(int i) {
		float ax = 0;
		
		if(i != longestword) {
			if(max_width > 0) {
				ax = (max_width - getWidth(i)) * 0.5f;
			} else {
				ax = (getRealWidth() - getWidth(i)) * 0.5f;	
			}
		} else {
			float realwidth = getRealWidth();
			if(max_width > 0 && max_width > realwidth) {
				ax = (max_width - realwidth) * 0.5f;
			}
		}
		if(ax < 0)
			return 0;
		
		return ax;
	}
	
	public float getOffsetX() {
		float ax = getOffsetX(0);			
		for(int i = 1; i < name.length; i++) {
			float c = getOffsetX(i);
			if(c < ax) {
				ax = c;
			}		
		}
		if(ax < 0) {
			return 0;
		}
		return ax;
	}
	
	
	public float getOffsetY() {
		if(name.length == 1) {
			return pos.getY();
		} else {
			float y = 0;
			for(int i = 1; i < name.length; i++) {
				y -= getHeight(i);
			}
	
			
			return y;
		}
	}
	
	public Vector2 getTotalPosition() {
		if(name.length == 1) {
			return pos;
		} else {
			float y = pos.getY();
			for(int i = 1; i < name.length; i++) {
				y -= getHeight(i);
			}
			return new Vector2(pos.getX(), y);
		}
	}
	
	public Vector2 getTotalBounds() {
		if(name.length == 1) {
			if(getWidth() < max_width) {
				return new Vector2(max_width, getHeight());
			}			
			return new Vector2(getWidth(), getHeight());
		} else {
			float width = getWidth();
			float height = getHeight();
			
			for(int i = 1; i < name.length; i++) {
				
				if(i == name.length-1) height += getHeight(i);
				else height += getHeight(i) + abs_y;
				
				float w = getWidth(i);
				if(w > width) {
					width = w;
				}
			}
			return new Vector2(width, height);
		}
	}
	
	public void setColor(Color c) {
		if(color != null) {
			if(!GraphicFunktions.isEqualColor(c, color)) {
				color = c;
				changed[1]=false;
			}
		} else {
			color = c;
			changed[1]=false;
		}
	}
	
	public Color getColor() {
		return color;
	}
	
	
	public void setlineYabs(float f) {
		abs_y = f;		
	}
	
	public void setMaxWidth(float width) {
		max_width = width;		
	}
	
	public float getMaxWidth() {
		return max_width;
	}
	
	public float getWidth() {
		return font.getWordBounds(name[0], thick).getWidth()*getScale();		
	}
	
	public float getWidth(int i) {
		return font.getWordBounds(name[i], thick).getWidth()*getScale();		
	}
	
	public float getRealWidth() {
		float width = 0;
				
		for(int i = 0; i < name.length; i++) {
			float a_width = 0;
			for(int a = 0; a < name[i].toCharArray().length;a++) {				
				char ch = name[i].toCharArray()[a];				
				Vector2 bounds = font.getCharBounds(ch, thick);					
					
				if(max_width > 0)	
					if(a_width + bounds.getWidth() * scale > max_width)  {						
						return a_width;
					}			
				a_width+=bounds.getWidth() * scale;
				
			}
			
			if(a_width > width)				
				width = a_width;
		}
		
		
		if(max_width > 0 && width > max_width) {
			return max_width;
		}	
		
		return width;		
	}
	
	public float getHeight() {
		return font.getWordBounds(name[0], thick).getHeight()*getScale();		
	}
	
	public float getHeight(int i) {
		return font.getWordBounds(name[i], thick).getHeight()*getScale();		
	}
	
	public Vector2 getWordBounds(String word) {
		float width = font.getWordBounds(word, thick).getWidth()*getScale();
		float height = font.getWordBounds(word, thick).getHeight()*getScale();
		
		return new Vector2(width, height);
		
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void draw(SpriteBatch batch) {	
		float x = pos.getX();
		float y = pos.getY();
		float width = 0;
		boolean draw = true;
		
		boolean haschanges = false;
		for(int i = 0; i < changed.length; i++) {
			if(!changed[i]) {
				haschanges = true;
			}
		}
		
		if(haschanges || texturecache == null || texturecache[0] == null) {
			texturecache = new Sprite[name.length * this.name[longestword].length()];
			TextureRegion[] regions = new TextureRegion[this.name[longestword].length()*name.length];
			
			int index = 0;
			Color b = font.getColor(thick);	
			
			if(color != null && !GraphicFunktions.isEqualColor(b, color) || !changed[2] || !changed[1]) {
				if(texturecache != null && changed[0]) {					
					ArrayList<Character> chars = new ArrayList<>();	
					for(int var1 = 0; var1 < name.length; var1++) {
						for(int i = 0; i < name[var1].toCharArray().length; i++) {
							char c = name[var1].toCharArray()[i];
							if(!chars.contains(c)) {						
								regions[index] = font.getRegion(c, thick);
								chars.add(c);
								index++;
							}
						}
					}
					font.setColor(color, regions, thick);					
				} else font.setColor(color, thick);
			}		
			
			//die neuen Texturen in cache laden
			index = 0;
			for(int var1 = 0; var1 < name.length; var1++) {
				for(int i = 0; i < name[var1].toCharArray().length; i++) {					
					char c = name[var1].toCharArray()[i];							
					Sprite s = new Sprite(font.getRegion(c, thick));
					texturecache[index] = s;
					index++;					
				}
			}
			
			//longestword_index			
			index = 0;
			for(int var1 = 0; var1 < name.length; var1++) {					
				for(int i = 0; i < name[var1].toCharArray().length; i++) {
					char c = name[var1].toCharArray()[i];		
					
					if(draw) {						
						float[] data = font.getInfo(c, thick);
									
						float ax = 0;
						if(pos_mode == 0) {
							
						} else if(pos_mode == 1) {
							ax = getOffsetX(var1);
						}
							
						batch.draw(texturecache[index], x + ax + width, y + (data[1]*scale*font.getScale()), font.getCharBounds(c, thick).getWidth()*scale,  font.getCharBounds(c, thick).getHeight()*scale);					
						width+=font.getCharBounds(c, thick).getWidth()*scale;							
					
						if(max_width > 0) {
							if(i == name[var1].toCharArray().length-1) {
								if(width + font.getCharBounds(c, thick).getWidth() * scale + ax > max_width) {
									draw = false;
								}
							} else {							
								if(width + font.getCharBounds(name[var1].toCharArray()[i+1], thick).getWidth() * scale + ax > max_width) {
									draw = false;
								}
							}
						}

					}
					index++;
				}	
				draw = true;
				x = pos.getX();
				width = 0;
				y -= (getHeight(var1) + abs_y);
			}
			
			if(!changed[1] && color != null && !GraphicFunktions.isEqualColor(b, color))
				if(texturecache != null && changed[0]) font.setColor(b, regions, thick);					
				else font.setColor(b, thick);
			
			
			
			
		} else {
			int index = 0;
			for(int var1 = 0; var1 < name.length; var1++) {
				for(int i = 0; i < name[var1].toCharArray().length; i++) {
					char c = name[var1].toCharArray()[i];
					
					if(draw) {
						float[] data = font.getInfo(c, thick);						
						
						float ax = 0;
						if(pos_mode == 0) {
							
						} else if(pos_mode == 1) {
							ax = getOffsetX(var1);
						}
						
						Vector2 bounds = font.getCharBounds(c, thick);
						
						batch.draw(texturecache[index], x + ax + width, y + (data[1]* scale *font.getScale()), bounds.getWidth()*  scale,  bounds.getHeight()* scale);
						width+=bounds.getWidth()* scale;
						
						if(max_width > 0) {
							if(i == name[var1].toCharArray().length-1) {
								if(width + font.getCharBounds(c, thick).getWidth() * scale + ax > max_width) {
									draw = false;
								}
							} else {							
								if(width + font.getCharBounds(name[var1].toCharArray()[i+1], thick).getWidth() * scale + ax > max_width) {
									draw = false;
								}
							}
						}
						
					}
					index++;
				}
				draw = true;
				x = pos.getX();
				width = 0;
				y -= (getHeight(var1) + abs_y);
			}
		}
		
		for(int i = 0; i < changed.length; i++)
			changed[i] = true;
	}

	
	private int last_id = -1; 
	public void draw(SpriteCache cache) {	
		float x = pos.getX();		
		//buffer = new FrameBuffer(Format.RGBA8888, (int)font.getWordBounds(name).getWidth(), (int)font.getWordBounds(name).getHeight(), false);
		
		if(!cacheadded) {
			if(last_id >= 0)
				Main.caches.remove(last_id);
			for(char c : name[0].toCharArray()) {
				cache.beginCache();
				cache.add(font.getRegion(c, thick), x, pos.getY(), font.getBounds(c, thick).getWidth()*font.scale,  font.getBounds(c, thick).getHeight()*font.scale);
	//			batch.draw(font.getRegion(c), x, pos.getY(), font.getBounds(c).getWidth()*font.scale,  font.getBounds(c).getHeight()*font.scale);
				x+=font.getRegion(c, thick).getRegionWidth()*font.scale;
				cacheadded = true;
				
				Main.caches.add(cache.endCache());		
				last_id = Main.caches.size()-1;
			}
		}
	}

	public void dispose() {	
	}





	
}
