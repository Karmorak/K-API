package com.karmorak.api.font;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.karmorak.api.Vector2;
import com.karmorak.api.funktions.GraphicFunktions;

public class OwnFont {
		
	
	
	float scale = 1f;
		
	private fontData data;
	private fontCache cache;	
	
	private fontData thick_data;
	private fontCache thick_cache;
	
	
	
	private static final ArrayList<OwnFont> different_fonts = new ArrayList<>();
	
	static HashMap<String, ArrayList<fontCache>> var1 = new HashMap<>();
	
	
	private static class fontCache {
		
		final Texture[] textures; 
		final Color[] colors;	
		
		public fontCache(int textures_length, int colors_length) {
			textures = new Texture[textures_length+1];
			colors = new Color[colors_length+1];
			
		}
		
		public fontCache(fontData b, int textures_length, int colors_length) {
			String path = b.path.toString();
			
			ArrayList<fontCache> list;
			if(var1.get(path) == null || var1.get(path).isEmpty()) {
				list = new ArrayList<>();			
			} else {
				list = var1.get(path);				
			}	
			list.add(this);
			var1.put(b.path.toString(), list);
			
			textures = new Texture[textures_length+1];
			colors = new Color[colors_length+1];
		}
	}
	
	private static class fontData {
				
		final FileHandle path;
		final HashMap<Character, String> data;
		final HashMap<Character, TextureRegion> regions;		
		
		public fontData(FileHandle path, HashMap<Character, String> data, HashMap<Character, TextureRegion> assets) {
			this.path = path;
			this.data = data;
			this.regions = assets;
		}
		
		boolean isSimilar(FileHandle datab) {			
			if(path.toString().equals(datab.toString())) {
				return true;
			}
			return false;
		}
		
		
	}
	
	public OwnFont(FileType type, String path, Color c) {
		String file_path = path;
		
		FileHandle handle = Gdx.files.getFileHandle(file_path + ".png", type);
		
		boolean b = true;
		for(OwnFont f : different_fonts) {
			if(f.data.isSimilar(handle)) {
				data = f.data;
				thick_data = f.thick_data;
				thick_cache = new fontCache(f.thick_data, 3, 3);
				cache = new fontCache(f.data, 3, 3);
				b = false;
			}
		}
		if(b) {
			data = new fontData(handle, new HashMap<>(), new HashMap<>());		
			cache = new fontCache(3, 3);
			cache.colors[0] = c;
			cache.textures[0] = GraphicFunktions.colorize2(handle, c, 1f,true);
			cache.textures[0].setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
			ArrayList<fontCache> list = new ArrayList<>();		
			list.add(cache);
			var1.put(data.path.toString(), list);
			
			
			FileHandle f = Gdx.files.getFileHandle(file_path + ".txt", type);
			
			try {
				String line;
				BufferedReader br = new BufferedReader(f.reader());
				
				while ((line = br.readLine()) != null) {
					if(!line.startsWith("//")) {
						String[] lines = line.split("_", 2);
						data.data.put((char)Integer.parseInt(lines[0]), lines[1]);
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			different_fonts.add(this);
			
			
			FileHandle thick_font_texture = Gdx.files.getFileHandle(file_path + "_thick.png", type);	
			
			
			
			if(thick_font_texture.exists()) {
				FileHandle thick_font = Gdx.files.getFileHandle(file_path + "_thick.txt", type);	
				thick_data = new fontData(thick_font_texture, new HashMap<>(), new HashMap<>());
				
				try {
					String line;
					BufferedReader br = new BufferedReader(thick_font.reader());
					
					while ((line = br.readLine()) != null) {
						if(!line.startsWith("//")) {
							String[] lines = line.split("_", 2);
							thick_data.data.put((char)Integer.parseInt(lines[0]), lines[1]);
						}
					}
					br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				thick_cache = new fontCache(3, 3);
				thick_cache.colors[0] = c;
				thick_cache.textures[0] = GraphicFunktions.colorize2(thick_font_texture, c, 1f,true);
				thick_cache.textures[0].setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);		
				ArrayList<fontCache> list2 = new ArrayList<>();		
				list2.add(thick_cache);
				var1.put(thick_data.path.toString(), list2);
				
			}
		}
		
	
	}
	
	public int[] isColorAlreadyCached(Color color, boolean thick) {
		if(thick && thick_data != null) {
			try {
				ArrayList<fontCache> list = var1.get(thick_data.path.toString());
				
				for(int i = 0; i < list.size(); i++) {
					fontCache cache = list.get(i);		
					for(int i2 = 0; i2 < cache.colors.length; i2++) {
						Color c = cache.colors[i2];
						
						if(c != null && GraphicFunktions.isEqualColor(c, color))
							return new int[] {i, i2};
						
						
					}
				}		
			} catch (NullPointerException e) {
				for(int i = 0; i < thick_cache.colors.length; i++) {
					Color c = thick_cache.colors[i];
					if(c != null && GraphicFunktions.isEqualColor(c, color))
						return new int[] {0, i};
				}
				return null;
			}
		} else {
			try {
				ArrayList<fontCache> list = var1.get(data.path.toString());
				
				for(int i = 0; i < list.size(); i++) {
					fontCache cache = list.get(i);			
					for(int i2 = 0; i2 < cache.colors.length; i2++) {
						Color c = cache.colors[i2];						
						if(c != null && GraphicFunktions.isEqualColor(c, color))
							return new int[] {i, i2};
					}	
				}		
			} catch (NullPointerException e) {
				for(int i = 0; i < cache.colors.length; i++) {
					Color c = cache.colors[i];
					if(c != null && GraphicFunktions.isEqualColor(c, color))
						return new int[] {0, i};
				}
				return null;
			}
		}
		return null;		
	}
	
	public void addCacheColor(Color color, boolean thick) {
		boolean added = false;
		
		if(thick && thick_data != null) {
			if(isColorAlreadyCached(color, true) != null) {
				return;
			}
			
			for(int i = 0; i < thick_cache.colors.length; i++) {
				Color c = thick_cache.colors[i];
				if(c == null) {
					Texture t = GraphicFunktions.colorize2(thick_data.path, color, 1f, true);
					added = true;
					thick_cache.colors[i] = color;
					thick_cache.textures[i] = t;
					break;
				}			
			}		
			
		} else {
			if(isColorAlreadyCached(color, false) != null) {
				return;
			}
			
			for(int i = 0; i < cache.colors.length; i++) {
				Color c = cache.colors[i];
				if(c == null) {
					Texture t = GraphicFunktions.colorize2(data.path, color, 1f, true);
					added = true;
					cache.colors[i] = color;
					cache.textures[i] = t;
					break;
				}			
			}
		}
		
		if(!added) {
			throw new ArrayIndexOutOfBoundsException("color cache is full configure the cache higher or delete unneeded cachefonts");
		}
	}
	
	
	public void write(SpriteBatch batch, Vector2 pos, String s, boolean thick) {
		float x = 0;
		
		for(char c : s.toCharArray()) {
			Vector2 src_pos = getPosition(c, thick);
			Vector2 bounds = getBounds(c, thick);
			
			
			
			TextureRegion t;
			if(!data.regions.containsKey(c)) {
				t = new TextureRegion(cache.textures[0], (int)src_pos.getX(), (int)src_pos.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());			
				data.regions.put(c, t);
			} else {
				t = data.regions.get(c);
			}
			
			batch.draw(t, pos.getX() + x, pos.getY(), bounds.getWidth()*scale, bounds.getHeight()*scale);
			
		
			x+=(bounds.getWidth()*scale);
		}
	}
	
	public void setColor(Color color, boolean thick) {			
		if(thick && thick_data != null) {
			int[] i = isColorAlreadyCached(color, true);			
			
			if(i != null) {
				int var1_cache = i[0];
				int cache_id = i[1];
				
				fontCache cache = var1.get(thick_data.path.toString()).get(var1_cache);
				
				Texture t = cache.textures[cache_id];
				cache.textures[cache_id] = cache.textures[0];
				cache.colors[cache_id] = cache.colors[0];
				cache.colors[0] = color; 
				cache.textures[0] = t;			
				
			} else {
				if(!GraphicFunktions.isEqualColor(thick_cache.colors[0], color)) {
					thick_cache.textures[0] = GraphicFunktions.colorize2(thick_data.path, color, 1, true);
					thick_cache.textures[0].setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
					thick_cache.colors[0] = color;
				}
			}
		} else {
			int[] i = isColorAlreadyCached(color, false);			
			
			if(i != null) {
				int var1_cache = i[0];
				int cache_id = i[1];
				
				fontCache cache = var1.get(data.path.toString()).get(var1_cache);
				
				Texture t = cache.textures[cache_id];
				cache.textures[cache_id] = cache.textures[0];
				cache.colors[cache_id] = cache.colors[0];
				cache.colors[0] = color; 
				cache.textures[0] = t;			
				
			} else {
				if(!GraphicFunktions.isEqualColor(cache.colors[0], color)) {
					cache.textures[0] = GraphicFunktions.colorize2(data.path, color, 1, true);
					cache.textures[0].setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
					cache.colors[0] = color;
				}
			}
		}
	}
	
	public void setColor(Color color, TextureRegion[] regions, boolean thick) {	
		if(thick && thick_data != null) {
			int[] i = isColorAlreadyCached(color, true);			
			
			if(i != null) {
				int var1_cache = i[0];
				int cache_id = i[1];
				
				fontCache cache = var1.get(thick_data.path.toString()).get(var1_cache);
				
				Texture t = cache.textures[cache_id];
				cache.textures[cache_id] = cache.textures[0];
				cache.colors[cache_id] = cache.colors[0];
				cache.colors[0] = color; 
				cache.textures[0] = t;				
				
			} else {
				if(!GraphicFunktions.isEqualColor(thick_cache.colors[0], color)) {
					thick_cache.textures[0] = colorize2(thick_data.path, color, regions);
					thick_cache.textures[0].setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
					thick_cache.colors[0] = color;
				}
			}
		} else {
			int[] i = isColorAlreadyCached(color, false);			
		
			if(i != null) {
				int var1_cache = i[0];
				int cache_id = i[1];
				
				fontCache cache = var1.get(data.path.toString()).get(var1_cache);
				
				Texture t = cache.textures[cache_id];
				cache.textures[cache_id] = cache.textures[0];
				cache.colors[cache_id] = cache.colors[0];
				cache.colors[0] = color; 
				cache.textures[0] = t;				
				
			} else {
				if(!GraphicFunktions.isEqualColor(cache.colors[0], color)) {
					cache.textures[0] = colorize2(data.path, color, regions);
					cache.textures[0].setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
					cache.colors[0] = color;
				}
			}
		}

	}
	
	private static Texture colorize2(FileHandle grayscale, Color color, TextureRegion[] regions) {			
		Pixmap pix = new Pixmap(grayscale);	
		
		Pixmap pix2 = new Pixmap((int)pix.getWidth(),(int) pix.getHeight(), Format.RGBA8888);
		pix2.setBlending(Pixmap.Blending.None);
		
		for(int r = 0; r < regions.length; r++) {
			TextureRegion reg = regions[r];
			if(reg != null) {			
				for(int x = 0; x < reg.getRegionWidth(); x++) {
					for(int y = 0; y < reg.getRegionHeight(); y++) {						
						int pixel = pix.getPixel(reg.getRegionX() + x,reg.getRegionY() + y);
						Color c = new Color(pixel);
						if(c.a == 1f) {
							if(c.r == 0) {
								pix2.drawPixel(reg.getRegionX()+x,reg.getRegionY() + y, Color.rgba8888(color.r * 1, color.g * 1, color.b * 1, color.a));
							} else {
								pix2.drawPixel(reg.getRegionX()+x,reg.getRegionY() + y, Color.rgba8888(color.r * c.r, color.g * c.g, color.b * c.b, color.a));
							}
							
						}
					}	
				}
			}
		}
		Texture s = new Texture(pix2, true);
		pix.dispose();
		pix2.dispose();
		return s;
	}
	
	public Color getColor(boolean thick) {
		if(thick && thick_data != null) {
			return thick_cache.colors[0];
		} else {
			return cache.colors[0];
		}
	}

	public TextureRegion getRegion(char c, boolean thick) {	
		if(thick && thick_data != null) {
			if(!thick_data.regions.containsKey(c)) {
				Vector2 pos = getPosition(c, true);
				Vector2 bounds = getBounds(c, true);

				int w = (int)bounds.getWidth();
				int h = (int)bounds.getHeight();
				int srcX = (int) pos.getX();
				int srcY = (int) pos.getY();
					
				TextureRegion region = new TextureRegion(thick_cache.textures[0], srcX, srcY, w, h);
					
				return region;
			} else {
				return thick_data.regions.get(c);
			}
		} else {
			if(!data.regions.containsKey(c)) {
				Vector2 pos = getPosition(c, false);
				Vector2 bounds = getBounds(c, false);

				int w = (int)bounds.getWidth();
				int h = (int)bounds.getHeight();
				int srcX = (int) pos.getX();
				int srcY = (int) pos.getY();
				
				TextureRegion region = new TextureRegion(cache.textures[0], srcX, srcY, w, h);
				
				return region;
			} else {
				return data.regions.get(c);
			}
		}
		
		
		
		

	}
	
	
	public Vector2 getPosition(char c, boolean thick) {
		if(thick && thick_data != null) {
			String[] s2 = thick_data.data.get(c).split("_");
			int x = Integer.parseInt(s2[0].replace("x", ""));
			int y = Integer.parseInt(s2[1].replace("y", ""));
			return new Vector2(x, y);
		} else {
			String[] s2 = data.data.get(c).split("_");
			int x = Integer.parseInt(s2[0].replace("x", ""));
			int y = Integer.parseInt(s2[1].replace("y", ""));
			return new Vector2(x, y);
		}
	}
	
	public Vector2 getCharBounds(char c, boolean thick) {		
		float x = 0;
		float y = 0;
		
		float[] info = getInfo(c, thick);
		Vector2 bounds = getBounds(c, thick);
		x = bounds.getWidth()*info[0];
		y = bounds.getHeight()*info[0];
		
		return new Vector2(x*scale, y*scale);
	}
	
	public Vector2 getWordBounds(String s, boolean thick) {
			
		float x = 0;
		float y = 0;
				
		if(s.length() == 1) {
			float[] info = getInfo(s.toCharArray()[0], thick);
			Vector2 bounds = getBounds(s.toCharArray()[0], thick);
			x = bounds.getWidth()*info[0];
			y = bounds.getHeight()*info[0];
			
		} else {
			for(char c : s.toCharArray()) {
				float[] info = getInfo(c, thick);
				
				
				Vector2 bounds = getBounds(c, thick);						
				x+=bounds.getWidth()*info[0];
				if(bounds.getHeight()*info[0] > y) y = bounds.getHeight()*info[0];
			}			
		}
		return new Vector2(x*scale, y*scale);
	}
	
	public Vector2 getBounds(char c, boolean thick) {
		if(thick && thick_data != null) {
			String[] s2 = thick_data.data.get(c).split("_");
			int w = Integer.parseInt(s2[2].replace("w", ""));
			int h = Integer.parseInt(s2[3].replace("h", ""));
			return new Vector2(w, h);
		} else {
			String[] s2 = data.data.get(c).split("_");
			int w = Integer.parseInt(s2[2].replace("w", ""));
			int h = Integer.parseInt(s2[3].replace("h", ""));
			return new Vector2(w, h);
		}
	}
	
	public float[] getInfo(char c, boolean thick) {
		if(thick && thick_data != null) {
			float scale = 1f;
			float a_y = 0f;
			String[] s2 = thick_data.data.get(c).split("_");
			if(s2.length > 4) {
				for(int i = 4; i < s2.length; i++) {
					String s = s2[i];
					if(s.startsWith("s")) {
						scale = Float.parseFloat(s.replace("s", ""));
					} if(s.startsWith("a")) {
						a_y = Float.parseFloat(s.replace("ay", ""));
					}
				}
			}
			
			return new float[] {scale, a_y};
		} else {
			float scale = 1f;
			float a_y = 0f;
			String[] s2 = data.data.get(c).split("_");
			if(s2.length > 4) {
				for(int i = 4; i < s2.length; i++) {
					String s = s2[i];
					if(s.startsWith("s")) {
						scale = Float.parseFloat(s.replace("s", ""));
					} if(s.startsWith("a")) {
						a_y = Float.parseFloat(s.replace("ay", ""));
					}
				}
			}
			
			return new float[] {scale, a_y};
		}
		
		
	}

	public void dispose() {
		cache.textures[0].dispose();
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public float getScale() {
		return scale;
	}

	public static float getHeight(String name, OwnFont font, boolean thick) {
		float biggest = 0;
		for(char c : name.toCharArray()) {
			Vector2 b = font.getBounds(c, thick);
			if(b.getHeight() >= biggest) {
				biggest = b.getHeight();
			}
		}
		return biggest;
	}
	
	public static float getWidth(String name, OwnFont font, boolean thick) {
		float biggest = 0;
		for(char c : name.toCharArray()) {
			Vector2 b = font.getBounds(c, thick);
			if(b.getHeight() >= biggest) {
				biggest = biggest + b.getWidth();
			}
		}
		return biggest;
	}

	public void draw(SpriteBatch batch, String name, Vector2 pos, boolean thick) {
		write(batch, pos, name, thick);
	}


	

}
