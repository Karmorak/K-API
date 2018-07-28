package com.karmorak.api.bounding;

import com.karmorak.api.Vector2;

public class Circle extends Colideable {		
	public Circle(Vector2 pos, float radius) {
		type = SHAPE_CIRCLE;
		this.pos = new Vector2(pos.getX(), pos.getY());
		this.bounds = new Vector2(radius, radius);
		this.COLbounds_min = new Vector2(0, 0);
		this.COLbounds_min = new Vector2(radius, radius);	
	}
}