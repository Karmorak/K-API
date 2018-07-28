package com.karmorak.api.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.Vector2;
import com.karmorak.api.font.OwnFont;

class Hang extends Button{
	
	int father;
	//private boolean isrelative;
	
	public Hang(OwnFont font, String name, int id) {		
		super(font, name);
		setDefconfig(-1);
		
		this.font = font;
		this.setName(name);
		
		old_name = getNames();
		
		this.setPosition(50, Gdx.graphics.getHeight()-50);	
		
		father = id;
		
		//isrelative = true;
	}
	
	public Hang(OwnFont font, String name, Vector2 pos,int id) {
		super(font, name);
		list.add(this);
		setDefconfig(-1);
		this.font = font;
		this.setName(name);
		old_name = getNames();		
		show = true;		
		this.setPosition(pos);
		if(ID == 0) {
			ID = max_id;		
			max_id++;
		}
		father = id;
		//isrelative = true;
	}

	public Hang() {
	}
	
	public Vector2 getRelPosition() {
		Vector2 c = new Vector2(getButton(father).getPosition().getX() + getPosition().getX(),getButton(father).getPosition().getY() + getPosition().getY());
		return c;
	}
		
	@Override
	public Button setPosition(float x, float y) {
		this.pos = new Vector2(x, y);	
		text.setPosition(getButton(father).getPosition().getX() + x, getButton(father).getPosition().getY() + y);
		return this;
	}
	
	Button setRelPosition(float x, float y) {
		this.pos = new Vector2(x, y);	
		text.setPosition(x, y);
		return this;
	}
	
	@Override
	public Button setPosition(Vector2 pos) {
		this.pos = new Vector2(pos.getX(), pos.getY());	
		text.setPosition(getButton(father).getPosition().getX() + pos.getX(), getButton(father).getPosition().getY() + pos.getY());
		return this;
	}
	
	Button setRelPosition(Vector2 pos) {
		this.pos = new Vector2(pos.getX(), pos.getY());	
		text.setPosition(pos.getX(), pos.getY());
		return this;
	}
	
	
	public void updatePosition() {
		text.setPosition(getButton(father).getPosition().getX() + pos.getX(), getButton(father).getPosition().getY() + pos.getY());		
	}	
	
	@Override
	public Button addHang(String name) {
		return this;
	}
	@Override
	public Button addHang(String name, Color color) {
		return this;
	}
	
	@Override
	public void addHang(String name, float x, float y) {
	}
	
	@Override
	public void addHang(String name, float x, float y, Color color) {
	}
	
	@Override
	public void addHang(String name, Vector2 pos) {
	}
	
	@Override
	public void addHang(String name, Vector2 pos, Color color) {
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		text.draw(batch);
	}
	
	public boolean isSelected() {
		Vector2 last_mouse_pos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			
		int mouse_pos_y = (int) (Gdx.graphics.getHeight() - last_mouse_pos.getY());
		int mouse_pos_x = (int) last_mouse_pos.getX();
			
		float button_pos_y = getRelPosition().getY();
		float button_pos_x = getRelPosition().getX();
		
		float buttonminy = (button_pos_y + getheight());
			
		if(mouse_pos_x > button_pos_x &&
				mouse_pos_x < button_pos_x + getwidth() &&
				mouse_pos_y > button_pos_y &&
						mouse_pos_y < buttonminy) {
			return true;	
		} else {
			return false;
		}
	}




}
