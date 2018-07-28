package com.karmorak.api.button;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karmorak.api.Vector2;

class Writable extends Hang {

	private int max_chars;
	protected int chars = 0;
	
	
	public Writable(Hang h, int max_chars) {
		super();
		setDefconfig(h.getID());
		this.father = h.father;
		this.text = h.text;
		this.font = h.font;
		
		ArrayList<String> names = new ArrayList<>();
		
		String name_c = "";
		for(int i = 0; i < max_chars; i++) {
			name_c = name_c + "_";
			
			if(text.getWordBounds(name_c).getX() >= text.getMaxWidth()) {
				names.add(name_c + " -");
				name_c = "";
			}		
		}	
		
		if(name_c != "") {
			names.add(name_c);
		}
		
		String[] names2 = new String[names.size()];  
		for(int i = 0; i < names.size(); i++) {
			names2[i] = names.get(i);
		}
		
		setName(names2);	
		setoldName(names2);
		
		Vector2 bound = text.getWordBounds("I" + names2[0]);
		
		text.setlineYabs(8);
		text.setMaxWidth(bound.getWidth());
		
		setPosition(h.getPosition().getX(), 0);
		
		this.max_chars = max_chars;
		show = true;
		
	}

	public int getchars() {
		return chars;
	}
	
	public int getmaxchars() {
		return max_chars;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
}
