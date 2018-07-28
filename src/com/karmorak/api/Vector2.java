//v2
package com.karmorak.api;

public class Vector2 {
	
	protected float pos_x;
	protected float pos_y;
	
	public Vector2(float x, float y) {
		pos_x = x;
		pos_y = y;
	}
	
	public void setPosition(float x, float y) {
		pos_x = x;
		pos_y = y;
	}
	public void setPosition(Vector2 bounds) {
		pos_x = bounds.getX();
		pos_y = bounds.getY();
	}
	
	public float  getX() {
		return pos_x;
	}
	
	public float  getY() {
		return pos_y;
	}
	
	public float  getWidth() {
		return pos_x;
	}
	
	public float  getHeight() {
		return pos_y;
	}
	
	public float setX(float x) {
		pos_x = x;
		return pos_x;
	}
	
	public float setY(float y) {
		pos_y = y;
		return pos_y;
	}
	
	public float setWidth(float x) {
		pos_x = x;
		return pos_x;
	}
	
	public float setHeight(float y) {
		pos_y = y;
		return pos_y;
	}



}
