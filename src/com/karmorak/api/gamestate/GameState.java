package com.karmorak.api.gamestate;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;

public interface GameState {
		
	public abstract void init();
	public abstract void update();
	public abstract void draw(SpriteBatch batch);

	public abstract void pause();
	public abstract void resume();
	public abstract void resize(int width, int height);
	public abstract void dispose();
	
	public default void draw(SpriteBatch batch, SpriteCache cache) {
		
	}
	public default void keyDown(int c) {
	}
	public default void touchDragged(int screenX, int screenY, int pointer) {
	}
	public default void mouseMoved(int screenX, int screenY) {
	}
	public default void scrolled(int amount) {
	}
	public default void tap(float x, float y, int count, int button) {
	}
	


}
