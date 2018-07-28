//v 1.1
package com.karmorak.api.gamestate;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;

public class GSM {
	
	private static final boolean[] inits = new boolean[64];	
	public static final String[] display_names = new String[64];
	
	//public static final ArrayList<String> display_names = new ArrayList<>();
	protected static final ArrayList<GameState> states = new ArrayList<>();
	protected static short currentstate = 0;

	public static void addState(GameState gs, String displayName, short id) {
		states.add(id, gs);
		display_names[id] = displayName;
	}
	
	public static short init() {
		if(states.size() > 0) {
			if(states.get(currentstate) == null) {
				states.get(currentstate).init();
				inits[currentstate] = true;
			} else {
				System.out.println("Couldnt init " + display_names[currentstate] + ". its null");
			}
		}
		return currentstate;		
	}
	public static short init(int state) {
		if(states.size() >= state) {
			if(states.get(state) == null) {
				states.get(state).init();
				inits[state] = true;
			} else {
				System.out.println("Couldnt init " + display_names[state] + ". its null");
			}
		}
			
		return currentstate;	
			
	}
	
	public static short update() {
		if(states.size() > 0) {
			if(!inits[currentstate]) {
				states.get(currentstate).init();
				inits[currentstate]= true;
			}
			states.get(currentstate).update();
		}
		return currentstate;		
	}
	
	public static short draw(SpriteBatch batch) {
		if(states.size() > 0) {
			if(!inits[currentstate]) {
				states.get(currentstate).init();
				states.get(currentstate).update();
				inits[currentstate]= true;
			}
			states.get(currentstate).draw(batch);
		}
		return currentstate;		
	}
	
	public static short draw(SpriteBatch batch, SpriteCache cache) {
		if(states.size() > 0) {
			if(!inits[currentstate]) {
				states.get(currentstate).init();
				states.get(currentstate).update();
				inits[currentstate]= true;
			}
			states.get(currentstate).draw(batch, cache);
		}
		return currentstate;		
	}
	
	public static short pause() {		
		if(states != null && states.size() > 0) {
			states.get(currentstate).pause();
		}
			return currentstate;		
	}
	
	public static short resume() {		
		if(states.size() > 0)
		states.get(currentstate).resume();
		return currentstate;		
	}
	
	public static short resize(int width, int height) {		
		if(states.size() > 0)
		states.get(currentstate).resize(width, height);
		return currentstate;		
	}
	
	public static int dispose() {		
		if(states.size() > 0) {
			states.get(currentstate).dispose();
			inits[currentstate] = true;		
		}
		return currentstate;		
	}
	
	
	public static void changestate(short state) {
		//init();
		currentstate = state;
	}
	
	public static short getStateInt() {
		return currentstate;
	}
	public static GameState getState() {
		return states.get(currentstate);
	}
	
	public static GameState getState(int i) {
		return states.get(i);
	}
	
	public static short tap(float x, float y, int count, int button) {
		if(states.size() > 0)
			states.get(currentstate).tap(x, y, count, button);
		return currentstate;	
	}
	
	public static short keyDown(int c) {
		if(states.size() > 0)
			states.get(currentstate).keyDown(c);
		return currentstate;	
	}
	
	public static short touchDragged(int screenX, int screenY, int pointer) {
		if(states.size() > 0)
			states.get(currentstate).touchDragged(screenX, screenY, pointer);
		return currentstate;			
	}
	public static short mouseMoved(int screenX, int screenY) {
		if(states.size() > 0)
			states.get(currentstate).mouseMoved(screenX, screenY);
		return currentstate;	
	}
	public static short scrolled(int amount) {
		if(states.size() > 0)
			states.get(currentstate).scrolled(amount);
		return currentstate;	
	}
	
	

}
