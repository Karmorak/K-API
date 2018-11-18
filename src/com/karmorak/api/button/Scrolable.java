package com.karmorak.api.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.Vector2;

public class Scrolable extends Button {	
	
	private static final String BACKG_PATH = "assets//scrolable//background.png";
	private static final String SLIDER_PATH = "assets//scrolable//slider.png";
	
	protected final boolean IS_VERTICAL;
	
	private Vector2 min_bg_size, bg_pos, bg_size;
	private Vector2 slider_pos, slider_size;		

	private Sprite background, slider;	
	private Sprite part_1, part_2, part_3, part_4, part_5, part_6, part_7, part_8;
	
	private float value, border_thicknes, bg_scale = 1f;
	
	protected boolean dontchangeName, showBorder;
	
	
	public Scrolable(Button h, boolean is_vertical, float length) {
		super();
		IS_VERTICAL = is_vertical;		
		
		Texture t1 = new Texture(Gdx.files.classpath(BACKG_PATH));
		background = new Sprite(t1);
		Texture t2 = new Texture(Gdx.files.classpath(SLIDER_PATH));
		slider = new Sprite(t2);
		float scale = length / 300;		
		
		takeConfig(h);		
		text.setName("0.0");
		
		if(is_vertical) {
			bg_size = new Vector2(length/5.5f, length);
			slider_size = new Vector2(getBGwidth(), slider.getHeight()*scale);
			setScale((length/5.5f)/60);
		} else {
			bg_size = new Vector2(length, length / 5.5f);
			slider_size = new Vector2(slider.getWidth()*scale, getBGheight());
			setScale(scale);
		}
		
		
		min_bg_size = new Vector2(bg_size.getWidth()*0.2f, getBGheight());
	
		value = 0;		
		
		bg_pos = new Vector2(pos.getX(), pos.getY());
		slider_pos = new Vector2(bg_pos.getX(), bg_pos.getY());
		
		pos.setPosition(bg_pos.getX() + (getBGwidth() - getwidth()) *0.5f, bg_pos.getY() + (getBGheight() - getheight()) *0.5f);
		text.setPosition(pos.getX(), pos.getY());
		
		list.add(h.getID(), this);
		scrols.add(this);
		
		setInteractable(false);		
	}

	public Scrolable(String name, boolean is_vertical, float length) {
		super();
		IS_VERTICAL = is_vertical;		
		
		Texture t1 = new Texture(Gdx.files.classpath(BACKG_PATH));
		background = new Sprite(t1);
		Texture t2 = new Texture(Gdx.files.classpath(SLIDER_PATH));
		slider = new Sprite(t2);
		float scale = length / 300;		
		
		Button h = new Button(name);
		h.setMiddle();
		takeConfig(h);		
		text.setName("0.0");
		
		if(is_vertical) {
			bg_size = new Vector2(length/5.5f, length);
			slider_size = new Vector2(getBGwidth(), slider.getHeight()*scale);
			setScale((length/5.5f)/60);
		} else {
			bg_size = new Vector2(length, length / 5.5f);
			slider_size = new Vector2(slider.getWidth()*scale, getBGheight());
			setScale(scale);
		}
		
		min_bg_size = new Vector2(bg_size.getWidth()*0.2f, getBGheight());
	
		value = 0;		
		
		bg_pos = new Vector2(pos.getX(), pos.getY());
		slider_pos = new Vector2(bg_pos.getX(), bg_pos.getY());
		
		pos.setPosition(bg_pos.getX() + (getBGwidth() - getwidth()) *0.5f, bg_pos.getY() + (getBGheight() - getheight()) *0.5f);
		text.setPosition(pos.getX(), pos.getY());
		
		list.add(h.getID(), this);
		scrols.add(this);
		
		setInteractable(false);		
	}
	
	public Scrolable(String name, boolean is_vertical, float length, float width) {
		super();		
		
		Texture t1 = new Texture(Gdx.files.classpath(BACKG_PATH));
		background = new Sprite(t1);
		Texture t2 = new Texture(Gdx.files.classpath(SLIDER_PATH));
		slider = new Sprite(t2);		
		float scale = length / 300;		
		
		IS_VERTICAL = is_vertical;
		Button h = new Button(name);
		h.setMiddle();
		takeConfig(h);		
		text.setName("0.0");
		
		if(is_vertical) {
			bg_size = new Vector2(width, length);
			slider_size = new Vector2(getBGwidth(), slider.getHeight()*scale);
			setScale(width/60);
		} else {
			bg_size = new Vector2(length, width);
			slider_size = new Vector2(slider.getWidth()*scale, getBGheight());
			setScale(scale);	
		}
		min_bg_size = new Vector2(bg_size.getWidth()*0.2f, getBGheight());
		
		value = 0;		
		
		bg_pos = new Vector2(pos.getX(), pos.getY());
		slider_pos = new Vector2(bg_pos.getX(), bg_pos.getY());
		
		pos.setPosition(bg_pos.getX() + (getBGwidth() - getwidth()) *0.5f, bg_pos.getY() + (getBGheight() - getheight()) *0.5f);
		text.setPosition(pos.getX(), pos.getY());
		
		list.add(h.getID(), this);
		scrols.add(this);
		
		setInteractable(false);		
	}
	
	
	@Override
	public void setScale(float scale) {
		bg_scale = scale;
		super.setScale(scale);
	}
	
	public void setTextScale(float scale) {
		super.setScale(scale);
	}
	
	public boolean isMouseOver() {
		Vector2 last_mouse_pos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		
		int mouse_pos_y = (int) (Gdx.graphics.getHeight() - last_mouse_pos.getY());
		int mouse_pos_x = (int) last_mouse_pos.getX();
		
		float button_pos_x = bg_pos.getX();
		float button_pos_y = bg_pos.getY();
		
		if(mouse_pos_x > button_pos_x &&
				mouse_pos_x < button_pos_x +  bg_size.getWidth() &&
					mouse_pos_y > button_pos_y &&
						mouse_pos_y < button_pos_y + getBGheight()) {
			return true;	
		}
		
		return false;
	}
	
	public boolean isDragged() {
		if(dragg_button == getID()) return true;
		return false;
	}
	/**
	 * only needed when using the hiding system
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		
	}
	
	@Override
	public void draw(SpriteBatch batch) {		
		if(show) {
			batch.draw(background.getTexture(), bg_pos.getX(), bg_pos.getY(), bg_size.getWidth()* bg_scale, bg_size.getHeight() * bg_scale);
			if(showBorder) {
				showBorder(true);
				part_1.draw(batch);
				part_2.draw(batch);
				part_3.draw(batch);
				
				part_4.draw(batch);
				part_5.draw(batch);
				part_6.draw(batch);
				part_7.draw(batch);
				part_8.draw(batch);
			}
			
			batch.draw(slider.getTexture(), slider_pos.getX(), slider_pos.getY(), slider_size.getX()* bg_scale, slider_size.getY()* bg_scale);
		}
		super.draw(batch);
	}
	
	@Override
	public boolean isSelected() {
		if(!isSelectable()) {
			Vector2 last_mouse_pos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			
			int mouse_pos_x = (int) last_mouse_pos.getX();
			int mouse_pos_y = (int) (Gdx.graphics.getHeight() - last_mouse_pos.getY());
			
			float button_pos_x = slider_pos.getX();
			float button_pos_y = slider_pos.getY();
			
			if(mouse_pos_x > button_pos_x &&
					mouse_pos_x < button_pos_x + getSliderWidth() &&
						mouse_pos_y > button_pos_y &&
							mouse_pos_y < button_pos_y + getSliderHeight()) {
				return true;	
			}
		} else {
			if(getID() == selected_Button) return true;
		}
		return false;
	}
	
	public float getValue() {
		return value;
	}
	
	public Scrolable setValue(float value) {
		this.value = value;
		if(!dontchangeName)
			setName("" + value);
		return this;
	}
	
	public Scrolable setSlider(float value) {
		if(value >= 0f && value <=1f) {		
			if(IS_VERTICAL) {
				slider_pos.setX(bg_pos.getX());
				slider_pos.setY(bg_pos.getY() + ((getBGheight() - slider_size.getHeight()) *value));
			} else {
				slider_pos.setX(bg_pos.getX() + (bg_size.getWidth()*value));
				slider_pos.setY(bg_pos.getY());
			}		
		}
		setValue(value);
		return this;
	}
	
	public void setSliderSize(Vector2 bounds) {
		slider_size.setPosition(bounds);
	}
	
	public void setSliderSize(float x_bound, float y_bound) {
		slider_size.setPosition(x_bound, y_bound);
	}
	
	public float getSliderWidth() {
		return slider_size.getWidth() * bg_scale;
	}
	
	public float getSliderHeight() {
		return slider_size.getHeight() * bg_scale;
	}
	
	public float getBGwidth() {
		return bg_size.getWidth() * bg_scale;
	}
	
	public float getBGheight() {
		return bg_size.getHeight() * bg_scale;
	}
	
	public Vector2 getSliderPos() {
		return slider_pos;
	}
	
	public Vector2 getBGPos() {
		return bg_pos;
	}	
	
	public void dochangeName(boolean bool) {
		dontchangeName = !bool;
	}
	
	public void setBackground(Sprite s) {
		background = new Sprite(s.getTexture());
	}
	
	public Button setName(String name) {
		text.setName(name);
		text.setPosition(bg_pos.getX() + (getBGwidth() - getwidth()) *0.5f, bg_pos.getY() + (getBGheight() - getheight()) *0.5f);
		return this;
	}	
	public Button setName(String[] name) {
		text.setName(name);
		text.setPosition(bg_pos.getX() + (getBGwidth() - getwidth()) *0.5f, bg_pos.getY() + (getBGheight() - getheight()) *0.5f);
		return this;
	}	
	
	public Button setName(int index, String name) {
		setName(name);
		return this;
	}	
	
	public Button setPosition(float x, float y) {
		bg_pos = new Vector2(x, y);
		
	
		if(IS_VERTICAL) {
			pos.setPosition(bg_pos.getX() + (getBGwidth() - getwidth()) *0.5f, bg_pos.getY() + (getBGheight() - getheight()) *0.5f);
			text.setPosition(pos.getX(), pos.getY());
		} else {
			pos.setPosition(bg_pos.getX() + (getBGwidth() - getwidth()) *0.5f, bg_pos.getY() + (getBGheight() - getheight()) *0.5f);
			text.setPosition(pos.getX(), pos.getY());
		}
		
		setSlider(getValue());
		return null;		
	}
	
	public void showBorder(boolean bool) {
		if(bool) {
			border_thicknes = 4;
			if(part_1 == null) {
				Texture t = new Texture(Gdx.files.classpath("assets//scrolable//border.png"));	
				
				part_1 = new Sprite(t);
				part_1.setRegion(0, 4, 4, 4);				
				
				part_2 = new Sprite(t);
				part_2.setRegion(0, 0, 4, 4);
			
				part_3 = new Sprite(t);
				part_3.setRegion(0, 4, 4, 4);
				part_3.flip(false, true);
				
				part_4 = new Sprite(t);
				part_4.setRegion(0, 8, 4, 4);
				
				part_4.flip(false, true);
				
				part_5 = new Sprite(t);
				part_5.setRegion(0, 4, 4, 4);
				part_5.flip(true, true);
								
				part_6 = new Sprite(t);
				part_6.setRegion(0, 0, 4, 4);
				part_6.flip(true, false);
							
				part_7 = new Sprite(t);
				part_7.setRegion(0, 4, 4, 4);
				part_7.flip(true, false);
				
				part_8 = new Sprite(t);
				part_8.setRegion(0, 8, 4, 4);
				
			}
			part_1.setPosition(bg_pos.getX(), bg_pos.getY());
			part_1.setSize(border_thicknes, border_thicknes);
			
			part_2.setSize(border_thicknes, getBGheight()-(border_thicknes*2));
			part_2.setPosition(bg_pos.getX(), bg_pos.getY() + border_thicknes);				
		
			part_3.setSize(border_thicknes, border_thicknes);
			part_3.setPosition(bg_pos.getX(), getBGheight() + bg_pos.getY()- border_thicknes);
			
			part_4.setSize(getBGwidth() - (border_thicknes*2), border_thicknes);
			part_4.setPosition(bg_pos.getX() + border_thicknes, getBGheight() + bg_pos.getY()- border_thicknes);
			
			part_5.setSize(border_thicknes, border_thicknes);
			part_5.setPosition(bg_pos.getX() +  getBGwidth() -border_thicknes, getBGheight() + bg_pos.getY()- border_thicknes);
			
			part_6.setSize(border_thicknes, getBGheight()-(border_thicknes*2));
			part_6.setPosition(bg_pos.getX() +  getBGwidth() -border_thicknes, bg_pos.getY() + border_thicknes);
			
			part_7.setSize(border_thicknes, border_thicknes);
			part_7.setPosition(bg_pos.getX() +  getBGwidth() -border_thicknes, bg_pos.getY());
			
			part_8.setSize(getBGwidth()-(border_thicknes*2), border_thicknes);
			part_8.setPosition(bg_pos.getX() + border_thicknes, bg_pos.getY());		
			showBorder = true;
		} else {
			showBorder = false;
		}
	}
	
	public static boolean touchDragged(int screenX, int screenY) {
		float speed_x = Math.abs((lastX - screenX));
		float speed_y = Math.abs((lastY - screenY));		
		if(dragg_button >= 0) {			
			Scrolable c = (Scrolable) getButton(dragg_button);
			if(c.IS_VERTICAL) {				
				if(lastY > screenY) {
					if(screenY < c.getSliderPos().getY() + c.getSliderHeight())
						if(c.getSliderPos().getY() > c.getBGPos().getY())
							c.getSliderPos().setY(c.getSliderPos().getY() - speed_y);	
				} else {
					if(screenY > c.getSliderPos().getY())
						if(c.getSliderPos().getY() +c.getSliderHeight() <= c.getBGPos().getY() + c.getBGheight())
							c.getSliderPos().setY(c.getSliderPos().getY() + speed_y);	
				}	
				
				if(c.getSliderPos().getY() < c.getBGPos().getY()) { 
					c.getSliderPos().setY(c.getBGPos().getY());
				} else if(c.getSliderPos().getY() + c.getSliderHeight() > c.getBGPos().getY() + c.getBGheight()) {
					c.getSliderPos().setY(c.getBGPos().getY() + c.getBGheight() - c.getSliderHeight());
				}
				
				float var1 = (c.getSliderPos().getY() - c.getBGPos().getY()) / (c.getBGheight() - c.getSliderHeight());			
				float var2 = (int) (var1 * 100);
				if(var2 < 0) var2 = 0f;
				
				c.setValue(var2*0.01f);
				
				if(!c.dontchangeName) {
					int var3 = (int) var2;			
					String name = "" + var3;
					
					if(name.length() == 1) {
						name = "0.0" + name;
					}else if(name.length() == 2) {
						name = "0." + name;
					} else if(name.length() == 3){
						String[] name2 = name.split("", 2);
						name = name2[0] + "." + name2[1];
					}
					c.setName(name);
				}	
				lastY = screenY;
			} else {
				if(lastX > screenX) {
					if(screenX < c.getSliderPos().getX() + c.getSliderWidth())
						if(c.getSliderPos().getX() > c.getBGPos().getX())
							c.getSliderPos().setX(c.getSliderPos().getX() - speed_x);	
				} else {
					if(screenX > c.getSliderPos().getX())
						if(c.getSliderPos().getX() + c.getSliderWidth() <= c.getBGPos().getX() + c.getBGwidth())
							c.getSliderPos().setX(c.getSliderPos().getX() + speed_x);	
				}
				
				if(c.getSliderPos().getX() + c.getSliderWidth() < c.getBGPos().getX()) { 
					c.getSliderPos().setX(c.getBGPos().getX());
				} else if(c.getSliderPos().getX() + c.getSliderWidth() > c.getBGPos().getX() + c.getBGwidth()) {
					c.getSliderPos().setX(c.getBGPos().getX() + c.getBGwidth() - c.getSliderWidth());
				}
				
				
				float var1 = (c.getSliderPos().getX() - c.getBGPos().getX() + (c.getSliderWidth() * 0.5f)) / (c.getBGwidth() - (c.getSliderWidth()));			
				float var2 = (int) ((var1 * 100)-(2*(240/c.getBGwidth())));
				if(var2 < 0) var2 = 0f;
				
				c.setValue(var2*0.01f);
				
				if(!c.dontchangeName) {
					int var3 = (int) var2;			
					String name = "" + var3;
					
					if(name.length() == 1) {
						name = "0.0" + name;
					}else if(name.length() == 2) {
						name = "0." + name;
					} else if(name.length() == 3){
						String[] name2 = name.split("", 2);
						name = name2[0] + "." + name2[1];
					}
					c.setName(name);
				}	
				lastX = screenX;
			}
		}
		return false;
	}


	
	

}
