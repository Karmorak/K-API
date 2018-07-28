package com.karmorak.api.button;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.Vector2;
import com.karmorak.api.listeners.GestureWrapper;

public class Expandable extends Button {
	
	private static final String BACKG_PATH = "assets//scrolable//background.png";
	
	
	private ArrayList<Button> options = new ArrayList<>();
	private float options_scale = 0;
	private float options_topabs = 9;
	private float options_abs = 7;
		
	private Scrolable scrolbar;
	
	private Sprite background;
	private final float fade_value = 8f;
	private Vector2 max_bg_bounds;
	private Vector2 cur_bg_bounds;	
	public int expand_state = -1;
	
	private float options_height = 0;
	
	
	public Expandable(Vector2 pos, Vector2 max_expand_size) {
		super(new String[] {":", "."}, pos);
		
		setlineYabs(getheight()*0.5f - getheight(1));
		setInteractable(true);
		show = true;
		
		Texture t1 = new Texture(Gdx.files.classpath(BACKG_PATH));
		background = new Sprite(t1);
		
		exp_list.add(this);
		max_bg_bounds = new Vector2(max_expand_size.getX(), max_expand_size.getY());
		cur_bg_bounds = new Vector2(0, 0);
	}
	

	public void scroll() {
		
	}
	
	@Override
	public void show(boolean bool) {
		show = bool;
		for(Button c : options) {
			c.show(bool);
		}
	}
	
	
	public void addOption(String name) {
		Button b = new Button(name);
		b.show(false);
		if(options_scale > 0) b.setScale(options_scale);
		else b.setScale(this.getScale());
		
		b.setMaxTextWidth(max_bg_bounds.getWidth());
		
		options.add(b);
		float y = options_topabs - options_abs;
		for(Button c : options) {
			y = y + c.getheight() + options_abs; 
		}
		options_height = y;
		
		if(y >= max_bg_bounds.getHeight()) {
			if(scrolbar == null) {
				scrolbar = new Scrolable("1.0", true, max_bg_bounds.getHeight() - getheight(), 20);
				scrolbar.showBorder(true);
				scrolbar.dochangeName(false);
				scrolbar.setName(" ");
			} else {
				scrolbar.setSliderSize(scrolbar.getSliderWidth(), scrolbar.getBGheight() * (max_bg_bounds.getHeight() / y));
				scrolbar.setSlider(1f);
			}
		} 
	}
	
	public boolean isOptionSelected(int i) {
		return options.get(i).isSelected();
	}
	
	public void setOptionsScale(float scale) {
		if(scale <=  0)
			options_scale  = 0;
		else
			options_scale = scale;
		for(Button b : options) b.setScale(options_scale);
	}
	
	public static void trigger() {
		for(Expandable e : exp_list) {
			if(e.isSelected()) {
				e.expand_state = 0;
				Button.selected_Button = -1;
				for(Button b : e.options) b.show(true);
			} else {
				if(e.isStilSelected(GestureWrapper.mouse_pos)) {
					Button.selected_Button = -1;
				} else {
					for(Button b : e.options) b.show(false);
					e.expand_state = -1;
					e.cur_bg_bounds.setX(0);
					e.cur_bg_bounds.setY(0);
				}
			}
		}
	}
	
	public void update() {
		if(show) { 
			if(expand_state == 0) {
				cur_bg_bounds.setX(cur_bg_bounds.getX() + fade_value);
				cur_bg_bounds.setY(cur_bg_bounds.getY() + fade_value);
				for(Button b : options) b.setMaxTextWidth(cur_bg_bounds.getWidth());
				
				if(cur_bg_bounds.getX() >= max_bg_bounds.getX() && cur_bg_bounds.getY() >= max_bg_bounds.getY()) {
					cur_bg_bounds.setX(max_bg_bounds.getX());
					cur_bg_bounds.setY(max_bg_bounds.getY());
					expand_state = 1;
				}
			}
		}			
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		if(show) {
			if(expand_state == 0) {
				batch.draw(background, getPosition().getX() - cur_bg_bounds.getX() + getwidth(), getPosition().getY() - cur_bg_bounds.getY() + getheight(), cur_bg_bounds.getX(), cur_bg_bounds.getY());
			
				float old_scale;
				float scale_c = cur_bg_bounds.getX() / max_bg_bounds.getX();
				
				
				float x = getPosition().getX() +getwidth()-cur_bg_bounds.getWidth();
				float y = getPosition().getY() +getheight() - options_topabs;
				int stopby = options.size();
				
				for(int i = 0; i < options.size(); i++) {
					Button c = options.get(i);		
					float height = c.getheight();				
					y = y - height; 					
					if(y < getPosition().getY() - cur_bg_bounds.getY() + getheight()) {
						stopby=i;
						break;
					}
					y  = y - options_abs;
				}
				y = getPosition().getY() +getheight() - options_topabs;
				
				for(int i = 0; i < stopby; i++) {
					Button c = options.get(i);
					old_scale = c.getScale();
					c.setScale(scale_c);
					y = y - c.getheight();
					
					c.setPosition(x, y);
					y  = y - options_abs;
					c.draw(batch);
					c.setScale(old_scale);
				}	
			} else if (expand_state == 1) {
				batch.draw(background, getPosition().getX() - cur_bg_bounds.getX() + getwidth(), getPosition().getY() - cur_bg_bounds.getY() + getheight(), cur_bg_bounds.getX(), cur_bg_bounds.getY());
			
				float x = getPosition().getX() +getwidth()-max_bg_bounds.getWidth();
				float y = getPosition().getY() +getheight() - options_topabs;
				int stopby = options.size();
				int startby = -1;
				
				float ay = 0;
				if(scrolbar != null)
					 ay = (max_bg_bounds.getHeight() - options_height) * (1-scrolbar.getValue());
				
				y = y - ay;
				
				for(int i = 0; i < options.size(); i++) {
					Button c = options.get(i);		
					float height = c.getheight();
					
					if(y > getPosition().getY() + getheight()+1) {
						startby = i;
					}
					
					y = y - height; 		
					if(y < getPosition().getY() - cur_bg_bounds.getY() + getheight()) {
						stopby=i;
					}
					c.setPosition(x, y);	
					if(i > startby && i < stopby) options.get(i).draw(batch);				
					y  = y - options_abs;
				}
			
				if(scrolbar != null) {
					scrolbar.setPosition(getPosition().getX() + getwidth()-scrolbar.getBGwidth(), getPosition().getY() + getheight() -max_bg_bounds.getHeight());
					scrolbar.draw(batch);
				}
			}
		}
	}
	
	@Override
	public void setInteractable(boolean bool) {}	
	@Override
	public void setSelectable(boolean bool) {}	
	@Override
	public void setHoverable(boolean bool) {}
	@Override
	public Button setName(String name) {
		return this;
	}


	public boolean isStilSelected(Vector2 mousepos) {
		if (expand_state == 0) {
			return isSelected();
		} else if (expand_state == 1) {
			int mouse_pos_y = (int) (Gdx.graphics.getHeight() - mousepos.getY());
			if(mousepos.getX() >  getPosition().getX() - max_bg_bounds.getX() + getwidth() && mousepos.getX() < getPosition().getX() + getwidth()) {
				if(mouse_pos_y > getPosition().getY() - max_bg_bounds.getY() + getheight() && mouse_pos_y < getPosition().getY() + getheight()) {
					return true;
				}
			}

		}			
		return false;
	}




}
