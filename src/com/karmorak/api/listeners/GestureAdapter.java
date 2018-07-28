package com.karmorak.api.listeners;

import com.badlogic.gdx.math.Vector2;

public interface GestureAdapter{
	
	
	public void keyDown(int keycode);	
	public boolean keyUp(int keycode);
	public boolean keyTyped(char character);
	public boolean touchDown(int screenX, int screenY, int pointer, int button);
	public boolean touchUp(int screenX, int screenY, int pointer, int button);
	public boolean touchDragged(int screenX, int screenY, int pointer);
	public boolean mouseMoved(int screenX, int screenY);
	public boolean scrolled(int amount);
	public boolean tap(float x, float y, int count, int button);
	public boolean longPress(float x, float y);
	public boolean fling(float velocityX, float velocityY, int button);
	public boolean pan(float x, float y, float deltaX, float deltaY);
	public boolean panStop(float x, float y, int pointer, int button);
	public boolean zoom(float initialDistance, float distance);
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2);
	public void keyNativeDown(int keycode);
	
	public void pinchStop();
}
