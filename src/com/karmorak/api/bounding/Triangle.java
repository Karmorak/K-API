package com.karmorak.api.bounding;

import com.karmorak.api.Vector2;

public class Triangle extends Colideable {	
	
	public static final short FACING_NORTH_WEST = 0;
	public static final short FACING_NORTH_EAST = 1;
	public static final short FACING_SOUTH_EAST = 2;
	public static final short FACING_SOUTH_WEST = 3;
	
	public Triangle(Vector2 pos, float width, float height) {
		type = SHAPE_TRIANGLE;
		this.pos = new Vector2(pos.getX(), pos.getY());			
		this.bounds = new Vector2(width, height);
		this.COLbounds_min = new Vector2(0, 0);
		this.COLbounds_max = new Vector2(width, height);	
	}

	public short getRotation() {
		return rotation;
	}

	public void setRotation(short rotation) {
		this.rotation = rotation;
	}
	
	
	
}
