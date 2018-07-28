//v1
package com.karmorak.api;

public class Vector3 extends Vector2 {

	private float pos_z;
	
	public Vector3(float x, float y, float z) {
		super(x, y);
		setZ(z);
	}
	
	public void setPosition(float x, float y, float z) {
		pos_x = x;
		pos_y = y;
		pos_z = z;
	}

	public float getZ() {
		return pos_z;
	}

	public void setZ(float pos_z) {
		this.pos_z = pos_z;
	}
	
	public float getDepth() {
		return pos_z;
	}

	public void setDepth(float pos_z) {
		this.pos_z = pos_z;
	}
	
	

}
